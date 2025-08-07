package com.druth.event_ticketing.repositories;

import com.druth.event_ticketing.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    int countByTicketTypeId(UUID TicketTypeId);
    Page<Ticket> findByPurchaserId(UUID purchaserId, Pageable pageable);
    Optional<Ticket> findByIdAndPurchaserId(UUID ticketId, UUID purchaserId);
}
