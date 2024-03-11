package com.ecoheat.ui.ViewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterScreenViewModel : ViewModel() {
    val themeMode = mutableStateOf(true)

    fun loadThemeState(context: Context) {
        val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        themeMode.value = sharedPreferences.getBoolean("mode", true)
    }
}
