package com.simpletak.takscheduler.api.dto;

public interface Mapper<E, D> {
    E toEntity(D dto);
    D fromEntity(E entity);
}
