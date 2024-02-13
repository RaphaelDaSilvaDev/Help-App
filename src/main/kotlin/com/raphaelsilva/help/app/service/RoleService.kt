package com.raphaelsilva.help.app.service

import com.raphaelsilva.help.app.dto.form.RoleForm
import com.raphaelsilva.help.app.dto.view.RoleView
import com.raphaelsilva.help.app.exception.NotFoundException
import com.raphaelsilva.help.app.mapper.role.RoleViewMapper
import com.raphaelsilva.help.app.model.Roles
import com.raphaelsilva.help.app.repository.RoleRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.management.relation.Role

@Service
class RoleService(
    private val roleRepository: RoleRepository,
    private val roleViewMapper: RoleViewMapper,
    private val notFoundMessage: String = "Role not found!"
) {
    fun create(roleForm: RoleForm): Roles{
        val role = Roles(name = roleForm.name)
        roleRepository.save(role)
        return role
    }

    fun getRoleById(id: Long) : RoleView{
        val role = roleRepository.findById(id).orElseThrow { NotFoundException(notFoundMessage) }
        return roleViewMapper.map(role)
    }

    fun getAll(pageable: Pageable): Page<RoleView> {
        val roles = roleRepository.findAll(pageable).map { role -> roleViewMapper.map(role) }
        return roles
    }

    fun deleteRole(id: Long) {
        roleRepository.deleteById(id)
    }
}
