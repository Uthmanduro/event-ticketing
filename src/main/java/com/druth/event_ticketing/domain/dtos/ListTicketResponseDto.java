package com.druth.event_ticketing.domain.dtos;

import com.druth.event_ticketing.domain.entities.TicketStatusEnum;
import com.druth.event_ticketing.domain.entities.TicketType;
import com.druth.event_ticketing.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDto {
    private UUID id;
    private TicketStatusEnum status;
        private ListTicketTicketTypeResponseDto ticketType;
    private User purchaser;

}
