package com.druth.event_ticketing.domain.dtos;

import com.druth.event_ticketing.domain.entities.TicketValidationMethodEnum;
import com.druth.event_ticketing.domain.entities.TicketValidationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationResponseDto {
    private UUID ticketId;
    private TicketValidationStatusEnum status;
}
