package com.example.bankchallenge.data.repository

import com.example.bankchallenge.R
import com.example.bankchallenge.domain.common.Result
import com.example.bankchallenge.domain.model.NewUser
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await
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

    suspend fun registerEmailAndPass(newUser: NewUser): Result<Unit> {
        var error: Int? = null

        val task = firebaseAuth.createUserWithEmailAndPassword(newUser.email, newUser.password)
            .addOnFailureListener {
                error = when (it) {
                    is FirebaseAuthUserCollisionException -> R.string.register_firebase_email_collision

                    is FirebaseAuthWeakPasswordException -> R.string.register_firebase_weak_pass

                    is FirebaseAuthInvalidCredentialsException -> R.string.register_firebase_error_invalid_credentials

                    is FirebaseNetworkException -> R.string.register_firebase_network

                    else -> R.string.register_firebase_unknown
                }
            }
        Tasks.whenAllComplete(task).await()

        return if (error == null) Result.Success(Unit) else Result.Error(error!!)
    }

    suspend fun registerSecondaryData(newUser: NewUser){

    }

}