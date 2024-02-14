package com.raphaelsilva.help.app.mapper.user

import com.raphaelsilva.help.app.dto.form.UserForm
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.model.User
import com.raphaelsilva.help.app.service.RoleService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserFormMapper(private val roleService: RoleService): Mapper<UserForm, User> {
    override fun map(t: UserForm): User {
        return User(
                name = t.name,
                email = t.email,
                password = BCrypt.hashpw(t.password, BCrypt.gensalt()),
                role = arrayListOf(roleService.getRoleByNamePure("USER"))
        )
    }

}