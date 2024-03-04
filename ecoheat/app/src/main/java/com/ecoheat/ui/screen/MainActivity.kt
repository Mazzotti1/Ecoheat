package com.ecoheat.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecoheat.ui.ViewModel.MainActivityViewModel
import com.ecoheat.ui.theme.EcoHeatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainActivityViewModel = viewModel()

            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    var isDarkMode by remember { mutableStateOf(true) }

    EcoHeatTheme(darkTheme = isDarkMode) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MainContent(
                viewModel = viewModel,
                isDarkMode = isDarkMode,
                onToggleDarkMode = { isDarkMode = !isDarkMode }
            )
        }
    }
}

@Composable
fun MainContent(
    viewModel: MainActivityViewModel,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { viewModel.onInit() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Iniciar")
        }
        Button(
            onClick = onToggleDarkMode,
            modifier = Modifier.padding(16.dp)
        ) {
            val icon = if (isDarkMode) Icons.Filled.WbSunny else Icons.Filled.Nightlight
            Icon(icon, contentDescription = "Modo ${if (isDarkMode) "Claro" else "Escuro"}")
        }
    }
}