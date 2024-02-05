package com.ecoheat.Model.Forms
import com.ecoheat.Model.Roles
import jakarta.persistence.Column
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserForm {
    @Column(unique = true)
    var name: String? = null

    var password: String? = null


}