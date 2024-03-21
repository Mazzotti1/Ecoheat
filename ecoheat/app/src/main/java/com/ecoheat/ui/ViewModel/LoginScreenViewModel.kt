package com.ecoheat.ui.ViewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoheat.Model.Login
import com.ecoheat.Model.Register
import com.ecoheat.data.repository.LoginRepository
import com.ecoheat.data.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.IOException

class LoginScreenViewModel : ViewModel() {
    private val repository = LoginRepository()
    val loginStatus = MutableLiveData<String>()

    val themeMode = mutableStateOf(true)
    var name by mutableStateOf("")
    var password by mutableStateOf("")

    private val _events = MutableSharedFlow<RegisterEvent>()
    val events: SharedFlow<RegisterEvent> = _events

    fun loadThemeState(context: Context) {
        val sharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        themeMode.value = sharedPreferences.getBoolean("mode", true)
    }

    fun onChangeName(newName: String){
        name = newName
    }

    fun onChangePassword(newPassword: String){
        password = newPassword
    }

    fun login(){
        try {
            if(name == "" || password == ""){
                viewModelScope.launch(Dispatchers.Main){
                    //event
                }
                return
            }

            viewModelScope.launch(Dispatchers.IO) {
                val request = Login(name, password)
                val status = repository.loginUser(request)
                if (status.isNotEmpty()) {
                    viewModelScope.launch(Dispatchers.Main) {
                        loginStatus.value = status
                        _events.emit(RegisterEvent.ShowRequisitionToast(status))
                    }
                }
            }
        }catch (e: IOException){

        }
    }
}


