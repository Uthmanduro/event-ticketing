package com.druth.event_ticketing.services.impl;

import com.druth.event_ticketing.domain.entities.*;
import com.druth.event_ticketing.exceptions.TicketSoldOutException;
import com.druth.event_ticketing.exceptions.TicketTypeNotFoundException;
import com.druth.event_ticketing.exceptions.UserNotFoundException;
import com.druth.event_ticketing.repositories.TicketRepository;
import com.druth.event_ticketing.repositories.TicketTypeRepository;
import com.druth.event_ticketing.repositories.UserRepository;
import com.druth.event_ticketing.services.QrCodeService;
import com.druth.event_ticketing.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(
                () -> new TicketTypeNotFoundException(
                        String.format("Ticket Type with ID %s not found", ticketTypeId)
                )
        );

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailable) {
            throw new TicketSoldOutException("Tickets has been sold out");
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        QrCode qrcode = qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
