package com.druth.event_ticketing.services;

import com.druth.event_ticketing.domain.entities.QrCode;
import com.druth.event_ticketing.domain.entities.Ticket;
import org.springframework.stereotype.Service;

@Service
public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
}
