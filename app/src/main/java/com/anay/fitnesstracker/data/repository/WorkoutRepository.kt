package com.anay.fitnesstracker.data.repository

import com.anay.fitnesstracker.data.model.Workout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class WorkoutRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getWorkouts(): List<Workout> {
        val snapshot = firestore
            .collection("workouts")
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->

            android.util.Log.d(
                "FirestoreDebug",
                "${document.id} -> ${document.data}"
            )

            Workout(
                title = document.getString("title") ?: "",
                muscles = document.getString("muscles") ?: "",
                exercises = document.getLong("exercises")?.toInt() ?: 0,
                isToday = document.getBoolean("isToday") ?: false
            )
        }
    }
}