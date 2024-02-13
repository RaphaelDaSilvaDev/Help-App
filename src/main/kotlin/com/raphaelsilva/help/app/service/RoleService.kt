package com.raphaelsilva.help.app.service

import com.raphaelsilva.help.app.dto.form.RoleForm
import com.raphaelsilva.help.app.model.Roles
import com.raphaelsilva.help.app.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {
    fun create(roleForm: RoleForm): Roles{
        val role = Roles(name = roleForm.name)
        roleRepository.save(role)
        return role
    }
}
