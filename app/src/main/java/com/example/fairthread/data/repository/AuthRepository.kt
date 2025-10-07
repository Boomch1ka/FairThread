package com.example.fairthread.data.repository

import com.example.fairthread.data.remote.AbstractApiService
import com.example.fairthread.model.EmailValidationResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val abstractApi: AbstractApiService
) {

    suspend fun validateEmail(email: String): Result<EmailValidationResponse> {
        return try {
            val result = abstractApi.validateEmail(API_KEY, email)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(email: String, password: String): Result<Unit> {
        val validation = validateEmail(email)
        if (validation.isFailure || validation.getOrNull()?.is_deliverable != true) {
            return Result.failure(Exception("Email is not deliverable"))
        }

        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return Result.failure(Exception("No UID"))
            createUserProfile(uid, email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUserProfile(uid: String, email: String) {
        val profile = mapOf(
            "email" to email,
            "createdAt" to FieldValue.serverTimestamp()
        )
        firestore.collection("users").document(uid).set(profile).await()
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun sendPasswordReset(email: String): Result<Unit> {
        return try {
            auth.sendPasswordResetEmail(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun logout() {
        auth.signOut()
    }

    companion object {
        const val API_KEY = "your_abstract_api_key_here"
    }
}
