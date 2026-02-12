package com.example.auratechApi.mappers;

import com.example.auratechApi.dtos.OrderResponseDTO;
import com.example.auratechApi.model.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface OrderMapper {

    OrderResponseDTO toDto(OrderEntity order);

}
