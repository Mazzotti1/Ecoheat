package com.ecoheat.Service

import com.ecoheat.Model.Forms.UserForm
import com.ecoheat.Model.Users
import javax.naming.AuthenticationException

interface IUsersService {
    fun getUserById(id: Long): Users?

    fun create(form: UserForm?): Users?
    @Throws(AuthenticationException::class)
    fun login(name: String?, password: String?): Users?
}