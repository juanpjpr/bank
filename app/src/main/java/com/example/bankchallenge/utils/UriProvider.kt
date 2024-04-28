package com.example.bankchallenge.utils


import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Objects
import javax.inject.Inject

class UriProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun newUri(): Uri {
        val file = context.createImageFile()
        return try {
            FileProvider.getUriForFile(
                Objects.requireNonNull(context),
                "com.example.bankchallenge.provider", file
            )
        } catch (_: Exception) {
            Uri.EMPTY
        }
    }

    private fun Context.createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return File.createTempFile(
            "BANKAPPCHALLENGE_$timeStamp",
            ".jpg",
            cacheDir
        )
    }
}
