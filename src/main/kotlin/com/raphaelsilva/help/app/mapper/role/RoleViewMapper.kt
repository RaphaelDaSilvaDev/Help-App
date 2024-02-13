package com.raphaelsilva.help.app.mapper.role

import com.raphaelsilva.help.app.dto.view.RoleView
import com.raphaelsilva.help.app.mapper.Mapper
import com.raphaelsilva.help.app.model.Roles
import org.springframework.stereotype.Component

@Component
class RoleViewMapper: Mapper<Roles, RoleView> {
    override fun map(t: Roles): RoleView {
        return RoleView(
            id = t.id!!,
            name = t.name
        )
    }

}