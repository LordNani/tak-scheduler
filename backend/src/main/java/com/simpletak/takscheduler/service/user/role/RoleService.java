package com.simpletak.takscheduler.service.user.role;

import com.simpletak.takscheduler.dto.user.role.RoleDTO;
import com.simpletak.takscheduler.dto.user.role.RoleMapper;
import com.simpletak.takscheduler.exception.user.role.RoleNotFoundException;
import com.simpletak.takscheduler.model.user.role.RoleEntity;
import com.simpletak.takscheduler.repository.user.role.RoleEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleEntityRepository roleEntityRepository;
    private final RoleMapper mapper;

    public RoleDTO findRoleById(UUID id) {
        return mapper.fromEntity(
                        roleEntityRepository
                                .findById(id)
                                .orElseThrow(RoleNotFoundException::new)
                );
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        RoleEntity roleEntity = mapper.toEntity(roleDTO);
        return mapper.fromEntity(roleEntityRepository.save(roleEntity));
    }

    public void deleteRole(UUID id) {
        roleEntityRepository.deleteById(id);
    }
}
