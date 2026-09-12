package com.anay.fitnesstracker.data.repository

import com.anay.fitnesstracker.data.model.Profile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProfileRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getProfile(): Profile {
        val snapshot = firestore
            .collection("profile")
            .document("does not exist")
            .get()
            .await()

        return snapshot.toObject(Profile::class.java)
            ?: throw Exception("Profile not found")
    }
}