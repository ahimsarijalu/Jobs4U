package com.ahimsarijalu.jobs4u.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ahimsarijalu.jobs4u.data.datasource.local.model.JobSeeker
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Job
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val auth = Firebase.auth
    private val user = auth.currentUser
    private val database = FirebaseFirestore.getInstance()

    fun register(
        email: String,
        password: String,
        name: String,
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser
            val userData = mapOf(
                "userId" to user?.uid,
                "name" to name,
                "email" to email,
                "listSavedJobs" to listOf<Job>()
            )
            database.collection("jobSeeker")
                .document(user?.uid.toString())
                .set(userData)
                .await()
            emit(Result.Success("Registered successfully"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(
        email: String,
        password: String,
    ): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            emit(Result.Success("Logged in successfully"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun logout(): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            auth.signOut()
            emit(Result.Success("Logged out successfully"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUser(): LiveData<Result<JobSeeker>> = liveData {
        emit(Result.Loading)
        try {
            var userData = JobSeeker()
            database.collection("jobSeeker").document(user?.uid.toString()).get()
                .addOnSuccessListener { user ->
                    userData = user.toObject(JobSeeker::class.java) as JobSeeker
                }.await()
            emit(Result.Success(userData))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun changePassword(oldPassword: String, password: String): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val oldData = EmailAuthProvider.getCredential(
                user?.email.toString(),
                oldPassword
            )
            user!!.reauthenticate(oldData).addOnSuccessListener {
                user.updatePassword(password)
            }.await()
            emit(Result.Success("Password has been changed successfully"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun resetPassword(email: String): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            auth.sendPasswordResetEmail(email).await()
            emit(Result.Success("Password reset confirmation link has been sent to your email"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}