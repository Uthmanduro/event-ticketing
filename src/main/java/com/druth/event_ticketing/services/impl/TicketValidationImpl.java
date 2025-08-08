package com.druth.event_ticketing.services.impl;

import com.druth.event_ticketing.domain.entities.*;
import com.druth.event_ticketing.exceptions.QrCodeNotFoundException;
import com.druth.event_ticketing.exceptions.TicketNotFoundException;
import com.druth.event_ticketing.repositories.QrCodeRepository;
import com.druth.event_ticketing.repositories.TicketRepository;
import com.druth.event_ticketing.repositories.TicketValidationRepository;
import com.druth.event_ticketing.services.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findById(qrCodeId).orElseThrow(
                () -> new QrCodeNotFoundException(
                        String.format(
                                "QrCode with Id %s not found", qrCodeId
                        )
                )
        );

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket);

    }

    private TicketValidation validateTicket(Ticket ticket) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(TicketValidationMethodEnum.QR_SCAN);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                TicketNotFoundException::new
        );

        return validateTicket(ticket);
    }
}
