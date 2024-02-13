package com.raphaelsilva.help.app.controller

import com.raphaelsilva.help.app.dto.form.RoleForm
import com.raphaelsilva.help.app.dto.view.RoleView
import com.raphaelsilva.help.app.model.Roles
import com.raphaelsilva.help.app.service.RoleService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriBuilder
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/api/role")
class RoleController(
    private val roleService: RoleService
) {
    
    @PostMapping
    fun createRole(@RequestBody roleForm: RoleForm, uriBuilder: UriComponentsBuilder): ResponseEntity<Roles>{
        val role = roleService.create(roleForm)
        val uri = uriBuilder.path("/api/role/${role.id}").build().toUri()
        return ResponseEntity.created(uri).body(role)
    }

    @GetMapping("/{id}")
    fun getRoleById(@PathVariable id: Long): ResponseEntity<RoleView>{
        val role = roleService.getRoleById(id)
        return ResponseEntity.ok().body(role)
    }
}