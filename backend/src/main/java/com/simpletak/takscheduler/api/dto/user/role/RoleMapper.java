package com.simpletak.takscheduler.api.dto.user.role;

import com.simpletak.takscheduler.api.dto.Mapper;
import com.simpletak.takscheduler.api.model.user.role.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleMapper implements Mapper<RoleEntity, RoleDTO> {

    @Override
    public RoleEntity toEntity(RoleDTO roleDTO) {

        return RoleEntity.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getRole())
                .build();
    }

    @Override
    public RoleDTO fromEntity(RoleEntity entity) {
        return new RoleDTO(entity.getId(), entity.getName());
    }
}
