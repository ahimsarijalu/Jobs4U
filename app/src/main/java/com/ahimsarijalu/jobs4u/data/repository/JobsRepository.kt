package com.ahimsarijalu.jobs4u.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ahimsarijalu.jobs4u.data.datasource.local.model.Job
import com.ahimsarijalu.jobs4u.data.datasource.local.model.JobSeeker
import com.algolia.search.client.ClientSearch
import com.algolia.search.configuration.ConfigurationSearch
import com.algolia.search.dsl.query
import com.algolia.search.helper.deserialize
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class JobsRepository {
    private val auth = Firebase.auth
    private val user = auth.currentUser
    private val database = FirebaseFirestore.getInstance()
    private val client = ClientSearch(
        ConfigurationSearch(
            ApplicationID("RK2FC2CX6H"),
            APIKey("b601ca1667a11e3db74113a920ac5ba7")
        )
    )
    private val index = client.initIndex(IndexName("jobs"))


    fun getAllJobs(): LiveData<Result<List<Job>>> = liveData {
        emit(Result.Loading)
        try {
            val listOfJobs = mutableListOf<Job>()
            val response = database.collection("jobs").orderBy("tweetId", Query.Direction.DESCENDING).get().await()
            for (document in response) {
                val data = document.data
                listOfJobs.add(
                    Job(
                        tweetId = data["tweetId"].toString(),
                        avatarUrl = data["avatarUrl"] as String,
                        name = data["name"] as String,
                        username = data["username"] as String,
                        text = data["text"] as String,
                        allImage = if (data["allImage"] != null) data["allImage"] as List<String> else listOf(),
                        saved = if (user != null) isSaved(if (data["listUserSaved"] != null) data["listUserSaved"] as List<String> else listOf()) else false
                    )
                )
            }
            emit(Result.Success(listOfJobs))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getAllSaved(): LiveData<Result<List<Job>>> = liveData {
        emit(Result.Loading)
        try {
            val listOfJobs = mutableListOf<Job>()
            val response =
                database.collection("jobSeeker").document(user?.uid.toString()).get().await()

            val userData = response.toObject(JobSeeker::class.java) as JobSeeker
            if (!userData.listSavedJobs.isNullOrEmpty()) {
                for (job in userData.listSavedJobs) {
                    listOfJobs.add(
                        Job(
                            job.tweetId,
                            job.avatarUrl,
                            job.name,
                            job.username,
                            job.text,
                            true,
                            job.allImage ?: listOf(),
                        )
                    )
                }
            }

            emit(Result.Success(listOfJobs))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    fun saveJob(job: Job): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val jobData = Job(
                tweetId = job.tweetId,
                avatarUrl = job.avatarUrl,
                name = job.name,
                username = job.username,
                text = job.text,
                saved = true,
                allImage = job.allImage
            )
            database.collection("jobSeeker").document(user?.uid.toString())
                .update("listSavedJobs", FieldValue.arrayUnion(jobData)).await()
            database.collection("jobs").document(job.tweetId)
                .update("listUserSaved", FieldValue.arrayUnion(user?.uid))
            emit(Result.Success("Job saved successfully"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun removeSavedJob(job: Job): LiveData<Result<String>> = liveData {
        emit(Result.Loading)
        try {
            val jobData = Job(
                tweetId = job.tweetId,
                avatarUrl = job.avatarUrl,
                name = job.name,
                username = job.username,
                text = job.text,
                saved = true,
                allImage = job.allImage
            )
            database.collection("jobSeeker").document(user?.uid.toString())
                .update("listSavedJobs", FieldValue.arrayRemove(jobData)).await()
            database.collection("jobs").document(job.tweetId)
                .update("listUserSaved", FieldValue.arrayRemove(user?.uid))
            emit(Result.Success("Job removed successfully"))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun search(keyword: String): LiveData<Result<List<Job>>> = liveData {
        emit(Result.Loading)
        try {
            val query = query {
                query = keyword
            }

            val response = index.search(query)
            val data = response.hits.deserialize(Job.serializer())
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun filter(filters: String): LiveData<Result<List<Job>>> = liveData {
        emit(Result.Loading)
        try {
            val query = query {
                query = filters
            }

            val response = index.search(query)
            val data = response.hits.deserialize(Job.serializer())
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    private fun isSaved(listId: List<String>): Boolean {
        val found = listId.find { it == user?.uid }
        return found == user?.uid
    }

}





