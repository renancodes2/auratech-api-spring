package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.OrderResponseDTO;
import com.example.auratechApi.models.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface OrderMapper {

    OrderResponseDTO toDto(OrderEntity order);

}
