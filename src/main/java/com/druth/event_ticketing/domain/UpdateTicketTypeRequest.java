package com.druth.event_ticketing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketTypeRequest {
    private UUID id;
    private String name;
    private double price;
    private Integer totalAvailable;
    private String description;

}
