package com.nahorniak.inventorymanagementservice.mappers;

import com.nahorniak.inventorymanagementservice.dto.response.*;
import com.nahorniak.inventorymanagementservice.persistance.*;
import fr.xebia.extras.selma.*;

@Mapper(withIoC = IoC.SPRING)
public interface SelmaMapper {

    // Entity to Domain
    @Maps(withIgnoreMissing = IgnoreMissing.DESTINATION)
    ShopDto toDto(ShopEntity entity);

    @Maps(withIgnoreMissing = IgnoreMissing.DESTINATION,
            withCustomFields = {@Field({"role.name", "role"})})
    UserDto toDto(UserEntity entity);

    @Maps(withIgnoreMissing = IgnoreMissing.DESTINATION)
    Role toDto(RoleEntity entity);

    @Maps(withIgnoreMissing = IgnoreMissing.DESTINATION)
    OrderDto toDto(OrderEntity entity);

    @Maps(withIgnoreMissing = IgnoreMissing.DESTINATION,
            withCustomFields = {
                    @Field({"order.id", "orderId"}),
                    @Field({"product.id", "productId"}),
                    @Field({"product.name", "productName"})
            })
    OrderProducts toDto(OrderProductsEntity entity);

    @Maps(withIgnoreMissing = IgnoreMissing.DESTINATION,
            withCustomFields = {
                    @Field({"stock.quantity", "quantity"}),
                    @Field({"shop.id", "shopId"})
            })
    ProductDto toDto(ProductEntity entity);
}
