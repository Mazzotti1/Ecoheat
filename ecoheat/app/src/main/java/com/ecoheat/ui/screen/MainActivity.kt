package com.ecoheat.ui.screen

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ecoheat.R
import com.ecoheat.ui.ViewModel.LoginScreenViewModel
import com.ecoheat.ui.ViewModel.MainActivityViewModel
import com.ecoheat.ui.ViewModel.RegisterScreenViewModel
import com.ecoheat.ui.screen.ui.theme.EcoHeatTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainActivityViewModel = viewModel()

            val registerViewModel: RegisterScreenViewModel = viewModel()
            val loginViewModel: LoginScreenViewModel = viewModel()
            val navController = rememberNavController()

            viewModel.loadLanguageState(this)
            viewModel.loadThemeState(this)
            val themeMode = viewModel.themeMode.value


            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = viewModel,
                        navController = navController,
                    )
                }
                composable("register") {
                    RegisterScreen(registerViewModel, navController, themeMode)
                }
                composable("login") {
                    LoginScreen(loginViewModel, navController, themeMode)
                }
            }
        }
    }

    @Composable
    fun MainScreen(
        viewModel: MainActivityViewModel,
        navController: NavController,
    ) {

        val language = viewModel.language.value
        val themeMode = viewModel.themeMode.value
        EcoHeatTheme(darkTheme = themeMode) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainContent(
                    viewModel = viewModel,
                    navController = navController,
                    themeMode = themeMode,
                    language = language,
                    context = this
                )
            }
        }
    }


    @Composable
    fun MainContent(
        viewModel: MainActivityViewModel,
        navController: NavController,
        themeMode: Boolean,
        language: String,
        context: Context
    ) {
        Column(
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
                Button(
                    onClick = { navController.navigate("register") },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = stringResource(id = R.string.start), fontSize = 24.sp)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { viewModel.toggleThemeMode(context) },
                    modifier = Modifier.padding(16.dp)
                ) {
                    val icon = if (themeMode) Icons.Filled.WbSunny else Icons.Filled.Nightlight
                    Icon(
                        icon,
                        contentDescription = "Modo ${if (themeMode) "Claro" else "Escuro"}",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Button(
                    onClick = { viewModel.toggleLanguage(context) },
                    modifier = Modifier.padding(16.dp)
                ) {
                    val icon = when (language) {
                        "en" -> R.drawable.brazil_flag
                        else -> R.drawable.usa_flag
                    }

                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "Linguagem ${if (language == "pt") "Português" else "Inglês"}",
                        modifier = Modifier.size(28.dp),
                        tint = Color.Unspecified
                    )
                }

            }
        }
    }
}

