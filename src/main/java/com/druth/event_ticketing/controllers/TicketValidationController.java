package com.druth.event_ticketing.controllers;

import com.druth.event_ticketing.domain.dtos.TicketValidationRequestDto;
import com.druth.event_ticketing.domain.dtos.TicketValidationResponseDto;
import com.druth.event_ticketing.domain.entities.TicketValidation;
import com.druth.event_ticketing.domain.entities.TicketValidationMethodEnum;
import com.druth.event_ticketing.mappers.TicketValidationMapper;
import com.druth.event_ticketing.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationMapper ticketValidationMapper;
    private final TicketValidationService ticketValidationService;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
    ) {
        TicketValidationMethodEnum method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;

        if (TicketValidationMethodEnum.MANUAL.equals(method)) {
            ticketValidation = ticketValidationService.validateTicketManually(ticketValidationRequestDto.getId());
        } else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(ticketValidationRequestDto.getId());
        }

        return ResponseEntity.ok(ticketValidationMapper.toTicketValidationResponseDto(ticketValidation));
    }

}
