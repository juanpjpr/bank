package com.example.bankchallenge.data.repository

import com.example.bankchallenge.R
import com.example.bankchallenge.domain.common.Result
import com.example.bankchallenge.domain.model.NewUser
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UsersRepository @Inject constructor() {
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()
    suspend fun registerData(newUser: NewUser): Result<Unit> {

        var error: Int? = null

        val task = database.collection("users").document(newUser.userID).set(newUser)
            .addOnFailureListener { error = R.string.register_firebase_save_user_error }
        Tasks.whenAllComplete(task).await()

        return if (error != null) Result.Error(error!!) else Result.Success(Unit)
    }
}