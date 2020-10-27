package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role readById(long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Role with id=" + id));
    }

    @Override
    public Role update(Role role) {
        Role roleToUpdate = readById(role.getId());

        Optional.ofNullable(role.getUsers()).ifPresent(roleToUpdate::setUsers);
        Optional.ofNullable(role.getName()).ifPresent(roleToUpdate::setName);

        return roleRepository.save(roleToUpdate);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Role roleToDelete = readById(id);

        if (!roleToDelete.getUsers().isEmpty()) {
            throw new RuntimeException("Can not delete role id=" + id);
        }

        roleRepository.delete(roleToDelete);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
