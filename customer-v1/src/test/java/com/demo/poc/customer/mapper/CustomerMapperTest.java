package com.demo.poc.customer.mapper;

import static com.demo.poc.customer.MockConstant.CIPHERED_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.demo.poc.commons.core.serialization.JsonSerializer;
import com.demo.poc.customer.dto.response.CustomerResponseDto;
import com.demo.poc.customer.dto.request.CustomerRequestDto;
import com.demo.poc.customer.repository.customer.entity.CustomerEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CustomerMapperTest {

  private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

  private static final Gson gson = new Gson();
  private static final JsonSerializer jsonSerializer = new JsonSerializer(new ObjectMapper());
  private static CustomerEntity CUSTOMER_ENTITY;
  private static CustomerResponseDto CUSTOMER_RESPONSE_DTO;
  private static CustomerRequestDto CUSTOMER_REQUEST_DTO;

  @BeforeEach
  public void setup() {
    CUSTOMER_ENTITY = jsonSerializer.readElementFromFile("mocks/customer/CustomerEntity.json", CustomerEntity.class);
    CUSTOMER_RESPONSE_DTO = jsonSerializer.readElementFromFile("mocks/customer/CustomerResponseDto.json", CustomerResponseDto.class);
    CUSTOMER_REQUEST_DTO = jsonSerializer.readElementFromFile("mocks/customer/CustomerRequestDto.json", CustomerRequestDto.class);
  }

  @Test
  @DisplayName("Given an entity, when mapping object, then return responseDto")
  void givenEntity_whenMappingObject_ThenReturnResponseDto() {
    //Arrange
    String expectedJson = gson.toJson(CUSTOMER_RESPONSE_DTO);

    //Act
    CustomerResponseDto actual = mapper.toResponseDto(CUSTOMER_ENTITY);
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expectedJson, actualJson);
  }

  @Test
  @DisplayName("Given an entity, when mapping object, then return requestDto")
  void givenEntity_whenMappingObject_ThenReturnRequestDto() {
    //Arrange
    CUSTOMER_ENTITY.setId(null);
    String expectedJson = gson.toJson(CUSTOMER_ENTITY);

    //Act
    CustomerEntity actual = mapper.toEntity(CUSTOMER_REQUEST_DTO, CIPHERED_PASSWORD);
    String actualJson = gson.toJson(actual);

    //Assert
    assertEquals(expectedJson, actualJson);
  }
}
