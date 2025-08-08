package com.druth.event_ticketing.repositories;

import com.druth.event_ticketing.config.QrCodeConfig;
import com.druth.event_ticketing.domain.entities.QrCode;
import com.druth.event_ticketing.domain.entities.QrCodeStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {
    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaserId);
    Optional<QrCode> findByIdAndStatus(UUID qrCodeId, QrCodeStatusEnum status);
}
