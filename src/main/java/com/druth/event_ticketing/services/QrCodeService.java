package com.druth.event_ticketing.services;

import com.druth.event_ticketing.domain.entities.QrCode;
import com.druth.event_ticketing.domain.entities.Ticket;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
