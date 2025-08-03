package com.druth.event_ticketing.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPublishedEventDetailsTicketTypeResponseDto {
    private UUID id;
    private String name;
    private double price;
    private String description;
}
