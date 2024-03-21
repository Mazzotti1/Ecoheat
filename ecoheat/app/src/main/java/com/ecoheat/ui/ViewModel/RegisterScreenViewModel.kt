package com.ecoheat.ui.ViewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoheat.Model.Register
import com.ecoheat.data.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.IOException

class RegisterScreenViewModel : ViewModel() {
    private val repository = RegisterRepository()
    val registerStatus = MutableLiveData<String>()

    val themeMode = mutableStateOf(true)
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var rePassword by mutableStateOf("")
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

    fun onChangeRePassword(newRePassword: String){
        rePassword = newRePassword
    }

    fun register() {
        try {
            if (name == "" || password == "" || rePassword == "") {
                viewModelScope.launch(Dispatchers.Main) {
                    _events.emit(RegisterEvent.ShowRequisitionToast("Preencha todos os valores"))
                }
                return
            }

            if (password != rePassword) {
                viewModelScope.launch(Dispatchers.Main) {
                    _events.emit(RegisterEvent.ShowRequisitionToast("Senhas não são iguais"))
                }
                return
            }

            viewModelScope.launch(Dispatchers.IO) {
                val request = Register(name, password)
                val status = repository.registerUser(request)
                if (status.isNotEmpty()) {
                    viewModelScope.launch(Dispatchers.Main) {
                        registerStatus.value = status
                        _events.emit(RegisterEvent.ShowRequisitionToast(status))
                    }
                }
            }
        } catch (e: IOException) {
            viewModelScope.launch(Dispatchers.Main) {
                _events.emit(RegisterEvent.ShowRequisitionToast("Erro de conexão ao registrar"))
            }
        }
    }

}
sealed class RegisterEvent {
    data class ShowRequisitionToast(val message: String) : RegisterEvent()
}