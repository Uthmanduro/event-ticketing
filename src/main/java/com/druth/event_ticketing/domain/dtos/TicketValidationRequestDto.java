package com.druth.event_ticketing.domain.dtos;

import com.druth.event_ticketing.domain.entities.TicketValidation;
import com.druth.event_ticketing.domain.entities.TicketValidationMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDto {
    private UUID id;
    private TicketValidationMethodEnum method;
}
