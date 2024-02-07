package com.raphaelsilva.help.app.mapper.user

import com.raphaelsilva.help.app.dto.view.UserView
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.model.User
import org.springframework.stereotype.Component

@Component
class UserViewMapper: Mapper<User, UserView> {
    override fun map(t: User): UserView {
        return UserView(
                id = t.id,
                name = t.name,
                email = t.email,
        )
    }

}