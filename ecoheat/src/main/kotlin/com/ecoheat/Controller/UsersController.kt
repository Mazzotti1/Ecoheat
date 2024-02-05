package com.ecoheat.Controller

import com.ecoheat.Exception.RegistroIncorretoException
import com.ecoheat.Model.Forms.UserForm
import com.ecoheat.Model.Users
import com.ecoheat.Service.Impl.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.naming.AuthenticationException

@RestController
@RequestMapping("/users")
class UsersController {
    @Autowired
    private val service: UserServiceImpl? = null

    @PostMapping("/register")
    fun create(@RequestBody form: UserForm?): ResponseEntity<out Any> {
        try {
            val user = service!!.create(form!!)
            return ResponseEntity(user, HttpStatus.CREATED)
        } catch (ex: RegistroIncorretoException) {
            val errorMessage = ex.message ?: "Ocorreu um erro durante a criação do usuário."
            return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/login")
    @Throws(AuthenticationException::class)
    fun login(@RequestBody form: UserForm): ResponseEntity<out Any> {
        try {
            val users: Users? = service?.login(form.name, form.password)
            return ResponseEntity.ok().body(users?.token)
        } catch (ex: Exception) {
            val errorMessage = ex.message ?: "Ocorreu um erro durante a criação do usuário."
            return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        }
    }

}