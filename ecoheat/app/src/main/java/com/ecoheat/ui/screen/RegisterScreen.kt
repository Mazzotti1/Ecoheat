package com.ecoheat.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ecoheat.R
import com.ecoheat.ui.ViewModel.RegisterScreenViewModel
import com.ecoheat.ui.screen.ui.theme.EcoHeatTheme

class RegisterScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: RegisterScreenViewModel = viewModel()
            val navController = rememberNavController()
            viewModel.loadThemeState(this)
            val themeMode = viewModel.themeMode.value

            RegisterScreen(viewModel,navController, themeMode)
        }
    }
}

@Composable
fun RegisterScreen(
        viewModel: RegisterScreenViewModel,
        navController: NavController,
        themeMode : Boolean) {
    EcoHeatTheme(darkTheme = themeMode) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            RegisterContent(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}

@Composable
fun RegisterContent(
    viewModel: RegisterScreenViewModel,
    navController: NavController,
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 44.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
        }
    }
}