package com.anay.fitnesstracker.data.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.anay.fitnesstracker.data.*
import com.anay.fitnesstracker.util.ImageUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FitnessViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val sessionManager = SessionManager(application)

    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _tempOnboarding = MutableStateFlow(UserProfile())
    val tempOnboarding: StateFlow<UserProfile> = _tempOnboarding.asStateFlow()

    private val _highlightedSplit = MutableStateFlow<String?>(null)
    val highlightedSplit: StateFlow<String?> = _highlightedSplit.asStateFlow()

    private val _isSessionChecking = MutableStateFlow(true)
    val isSessionChecking: StateFlow<Boolean> = _isSessionChecking.asStateFlow()

    init {
        checkSavedSession()
    }

    private fun checkSavedSession() {
        val savedUsername = sessionManager.getUsername()
        if (!savedUsername.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    val snapshot = db.collection("users").document(savedUsername).get().await()
                    if (snapshot.exists()) {
                        _currentUser.value = snapshot.toObject(UserProfile::class.java)
                    } else {
                        sessionManager.clearSession()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _isSessionChecking.value = false
                }
            }
        } else {
            _isSessionChecking.value = false
        }
    }

    fun updateInitialDetails(
        name: String,
        gender: String,
        dobYear: Int,
        contact: String,
        heightCm: Double,
        weightKg: Double
    ) {
        val username = FitnessCalculator.generateUsername(name, dobYear.toString(), contact)
        val (bmi, cat) = FitnessCalculator.calculateBmi(heightCm, weightKg)
        val maintCal = FitnessCalculator.calculateMaintenanceCalories(gender, dobYear, heightCm, weightKg)

        _tempOnboarding.value = _tempOnboarding.value.copy(
            username = username,
            name = name,
            gender = gender,
            birthYear = dobYear,
            contactNo = contact,
            heightCm = heightCm,
            weightKg = weightKg,
            bmi = bmi,
            bmiCategory = cat,
            maintenanceCalories = maintCal
        )
    }

    fun updateGoal(goal: String) {
        val current = _tempOnboarding.value
        val (cals, protein) = FitnessCalculator.calculateTargets(
            current.maintenanceCalories,
            current.weightKg,
            goal
        )
        _tempOnboarding.value = current.copy(
            fitnessGoal = goal,
            dailyCalorieGoal = cals,
            dailyProteinGoal = protein
        )
    }

    fun updateFrequency(freq: String) {
        val split = FitnessCalculator.getSplitForFrequency(freq)
        _tempOnboarding.value = _tempOnboarding.value.copy(
            workoutFrequency = freq,
            workoutSplit = split
        )
        _highlightedSplit.value = split
    }

    fun selectExplicitSplit(split: String) {
        _tempOnboarding.value = _tempOnboarding.value.copy(workoutSplit = split)
        _highlightedSplit.value = split
    }
    fun logExercise(exerciseName: String, sets: List<WorkoutSet>) {
        val user = _currentUser.value ?: return
        val newEntry = LoggedExercise(
            id = UUID.randomUUID().toString(),
            exerciseName = exerciseName,
            sets = sets,
            timestamp = System.currentTimeMillis()
        )

        val updatedWorkouts = listOf(newEntry) + user.loggedWorkouts
        val newNotification = NotificationItem(
            id = UUID.randomUUID().toString(),
            message = "Logged: $exerciseName (${sets.size} sets)",
            timestamp = System.currentTimeMillis()
        )
        val updatedNotifs = listOf(newNotification) + user.notifications

        val updatedUser = user.copy(
            loggedWorkouts = updatedWorkouts,
            notifications = updatedNotifs
        )
        _currentUser.value = updatedUser

        viewModelScope.launch {
            try {
                db.collection("users").document(user.username)
                    .update(
                        mapOf(
                            "loggedWorkouts" to updatedWorkouts,
                            "notifications" to updatedNotifs
                        )
                    )
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun confirmAndSaveProfile(onComplete: () -> Unit) {
        val initialNotification = NotificationItem(
            id = UUID.randomUUID().toString(),
            message = "Account created successfully! Welcome to Fitness Tracker.",
            timestamp = System.currentTimeMillis()
        )
        val profile = _tempOnboarding.value.copy(notifications = listOf(initialNotification))

        viewModelScope.launch {
            try {
                db.collection("users").document(profile.username).set(profile).await()
                sessionManager.saveUsername(profile.username)
                _currentUser.value = profile
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                sessionManager.saveUsername(profile.username)
                _currentUser.value = profile
                onComplete()
            }
        }
    }

    fun login(username: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val snapshot = db.collection("users").document(username.trim()).get().await()
                if (snapshot.exists()) {
                    var profile = snapshot.toObject(UserProfile::class.java)!!

                    // Add dynamic login notification
                    val loginNotif = NotificationItem(
                        id = UUID.randomUUID().toString(),
                        message = "Recent Login to your Account",
                        timestamp = System.currentTimeMillis()
                    )
                    val updatedNotifs = listOf(loginNotif) + profile.notifications
                    profile = profile.copy(notifications = updatedNotifs)

                    db.collection("users").document(profile.username)
                        .update("notifications", updatedNotifs)
                        .await()

                    sessionManager.saveUsername(profile.username)
                    _currentUser.value = profile
                    onSuccess()
                } else {
                    onError("Username not found!")
                }
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error during login")
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        sessionManager.clearSession()
        _currentUser.value = null
        onLoggedOut()
    }

    fun updateProfile(
        name: String,
        birthYear: Int,
        contactNo: String,
        avatarUri: Uri? = null
    ) {
        val current = _currentUser.value ?: return
        val base64Str = if (avatarUri != null) {
            ImageUtils.uriToBase64(getApplication(), avatarUri) ?: current.avatarBase64
        } else {
            current.avatarBase64
        }

        val updated = current.copy(
            name = name,
            birthYear = birthYear,
            contactNo = contactNo,
            avatarBase64 = base64Str
        )
        _currentUser.value = updated

        viewModelScope.launch {
            try {
                db.collection("users").document(current.username).set(updated).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addMeal(name: String, calories: Int, protein: Int) {
        val user = _currentUser.value ?: return
        val newMeal = MealItem(
            id = UUID.randomUUID().toString(),
            name = name,
            calories = calories,
            protein = protein
        )
        val updatedMeals = listOf(newMeal) + user.meals

        // Add dynamic meal notification
        val mealNotif = NotificationItem(
            id = UUID.randomUUID().toString(),
            message = "You logged a meal: $name ($calories kcal)",
            timestamp = System.currentTimeMillis()
        )
        val updatedNotifs = listOf(mealNotif) + user.notifications

        val updatedUser = user.copy(meals = updatedMeals, notifications = updatedNotifs)
        _currentUser.value = updatedUser

        viewModelScope.launch {
            try {
                db.collection("users").document(user.username)
                    .update(
                        mapOf(
                            "meals" to updatedMeals,
                            "notifications" to updatedNotifs
                        )
                    )
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}