package com.ecoheat.ui.screen

import com.ecoheat.ui.ViewModel.LoginScreenViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ecoheat.R
import com.ecoheat.ui.screen.ui.theme.EcoHeatTheme


class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: LoginScreenViewModel = viewModel()
            val navController = rememberNavController()
            viewModel.loadThemeState(this)
            val themeMode = viewModel.themeMode.value
            LoginScreen(viewModel,navController, themeMode)

        }
    }
}

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    navController: NavController,
    themeMode: Boolean,
) {
    EcoHeatTheme(darkTheme = themeMode) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LoginContent(
                viewModel = viewModel,
                navController = navController,
            )
        }
    }
}

@Composable
fun LoginContent(
    viewModel: LoginScreenViewModel,
    navController: NavController,
) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.name_title),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = viewModel.name,
                onValueChange = { viewModel.onChangeName(it) },
                label = {Text(text =stringResource(id = R.string.name_placeholder), fontSize = 16.sp)},
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .then(Modifier.padding(bottom= 14.dp))
            )
        }
        Text(
            text = stringResource(id = R.string.password_title),
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = viewModel.password,
                onValueChange = { viewModel.onChangePassword(it) },
                label = {Text(text =stringResource(id = R.string.password_placeholder), fontSize = 16.sp)},
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .then(Modifier.padding(bottom=8.dp)),
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { viewModel.login() },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.green_btn)),
                modifier = Modifier
                    .padding(16.dp)
                    .then(Modifier.padding(bottom = 8.dp))
            ) {
                Text(
                    text = stringResource(id = R.string.btn_login),
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.off_white)
                )
            }
        }
        Text(
            text = stringResource(id = R.string.has_account),
            fontSize = 16.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { navController.navigate("register") },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.black)),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_register),
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
}