package com.ecoheat.ui.ViewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecoheat.ui.screen.MainActivity
import java.util.Locale

class MainActivityViewModel : ViewModel() {
    val themeMode = mutableStateOf(true)
    val language = mutableStateOf("pt")
    private val languages = mutableListOf("en", "pt")

    fun toggleThemeMode(context: Context) {
        themeMode.value = !themeMode.value
        saveThemeState(context)
    }

    private fun saveThemeState(context: Context) {
        val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("mode", themeMode.value).apply()
    }

    fun loadThemeState(context: Context) {
        val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        themeMode.value = sharedPreferences.getBoolean("mode", true)
    }

    fun toggleLanguage(context: Context) {
        val currentIndex = languages.indexOf(language.value)
        val nextIndex = (currentIndex + 1) % languages.size
        language.value = languages[nextIndex]

        saveLanguageState(context)

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    private fun saveLanguageState(context: Context){
        val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", language.value).apply()
    }

    fun loadLanguageState(context: Context) {
        val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("language", "en") ?: "en"
        language.value = savedLanguage

        val locale = Locale(savedLanguage)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }

}
