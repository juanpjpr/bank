package com.example.bankchallenge.data.repository

import com.example.bankchallenge.R
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import com.example.bankchallenge.domain.common.Result
import javax.inject.Inject

class AuthRepository @Inject constructor() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    suspend fun login(email: String, password: String): Result<Unit> {
        var error: Int? = null

        val task = firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener {
                error = when (it) {

                    is FirebaseNetworkException -> R.string.error_login_network

                    else -> R.string.error_login_generic
                }
            }
        Tasks.whenAllComplete(task).await()
        return if (error == null) Result.Success(Unit) else Result.Error(error!!)
    }

}