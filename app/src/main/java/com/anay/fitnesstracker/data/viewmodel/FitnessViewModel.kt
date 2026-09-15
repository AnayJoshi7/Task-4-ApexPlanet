package com.anay.fitnesstracker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anay.fitnesstracker.data.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FitnessViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _tempOnboarding = MutableStateFlow(UserProfile())
    val tempOnboarding: StateFlow<UserProfile> = _tempOnboarding.asStateFlow()

    private val _highlightedSplit = MutableStateFlow<String?>(null)
    val highlightedSplit: StateFlow<String?> = _highlightedSplit.asStateFlow()

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

    fun confirmAndSaveProfile(onComplete: () -> Unit) {
        val profile = _tempOnboarding.value
        viewModelScope.launch {
            try {
                db.collection("users").document(profile.username).set(profile).await()
                _currentUser.value = profile
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
                // Fallback to local session on network error
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
                    val profile = snapshot.toObject(UserProfile::class.java)
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

    fun addMeal(name: String, calories: Int, protein: Int) {
        val user = _currentUser.value ?: return
        val newMeal = MealItem(
            id = UUID.randomUUID().toString(),
            name = name,
            calories = calories,
            protein = protein
        )
        val updatedMeals = listOf(newMeal) + user.meals
        val updatedUser = user.copy(meals = updatedMeals)
        _currentUser.value = updatedUser

        viewModelScope.launch {
            try {
                db.collection("users").document(user.username)
                    .update("meals", updatedMeals)
                    .await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
