package com.nahorniak.inventorymanagementservice.mappers;

import com.nahorniak.inventorymanagementservice.domain.Role;
import com.nahorniak.inventorymanagementservice.domain.Shop;
import com.nahorniak.inventorymanagementservice.domain.User;
import com.nahorniak.inventorymanagementservice.dto.response.UserDto;
import com.nahorniak.inventorymanagementservice.persistance.RoleEntity;
import com.nahorniak.inventorymanagementservice.persistance.ShopEntity;
import com.nahorniak.inventorymanagementservice.persistance.UserEntity;
import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.IoC;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

@Mapper(withIoC = IoC.SPRING)
public interface SelmaMapper {

    // Entity to Domain
    Shop toDomain(ShopEntity entity);
    User toDomain(UserEntity entity);
    @Maps(withIgnoreMissing = IgnoreMissing.DESTINATION)
    Role toDomain(RoleEntity entity);
    // Entity to Domain


    // Domain to DTO
    UserDto toDto(User domain);
}
