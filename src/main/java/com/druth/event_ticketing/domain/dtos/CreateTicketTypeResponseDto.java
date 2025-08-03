package com.druth.event_ticketing.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTicketTypeResponseDto {
    private UUID id;
    private String name;
    private double price;
    private Integer totalAvailable;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
