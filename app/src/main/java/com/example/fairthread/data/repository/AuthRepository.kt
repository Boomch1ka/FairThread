package com.example.fairthread.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun register(email: String, password: String): Result<Unit> {
        return try {
            val repo = FirestoreRepository()
            val exists = repo.isUserRegistered(email)
            if (exists) return Result.failure(Exception("An account with this email already exists."))

            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return Result.failure(Exception("No UID"))
            repo.createUserProfile(uid, email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUserProfile(uid: String, email: String) {
        val userDoc = firestore.collection("users").document(uid)
        val profile = mapOf(
            "email" to email,
            "createdAt" to FieldValue.serverTimestamp()
        )
        userDoc.set(profile).await()
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val isRegistered = FirestoreRepository().isUserRegistered(email)
            if (!isRegistered) return Result.failure(Exception("Account not found. Please register first."))

            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendPasswordReset(email: String): Result<Unit> {
        return try {
            val repo = FirestoreRepository()
            val task = repo.isUserRegistered(email)
            if (!task) return Result.failure(Exception("No account found for this email. Please register first."))

            auth.sendPasswordResetEmail(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun getCurrentUserId(): String? = auth.currentUser?.uid

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

}