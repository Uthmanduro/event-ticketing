package com.druth.event_ticketing.services.impl;

import com.druth.event_ticketing.domain.CreateEventRequest;
import com.druth.event_ticketing.domain.UpdateEventRequest;
import com.druth.event_ticketing.domain.UpdateTicketTypeRequest;
import com.druth.event_ticketing.domain.entities.Event;
import com.druth.event_ticketing.domain.entities.EventStatusEnum;
import com.druth.event_ticketing.domain.entities.TicketType;
import com.druth.event_ticketing.domain.entities.User;
import com.druth.event_ticketing.exceptions.EventNotFoundException;
import com.druth.event_ticketing.exceptions.EventUpdateException;
import com.druth.event_ticketing.exceptions.TicketTypeNotFoundException;
import com.druth.event_ticketing.exceptions.UserNotFoundException;
import com.druth.event_ticketing.repositories.EventRepository;
import com.druth.event_ticketing.repositories.UserRepository;
import com.druth.event_ticketing.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with ID '%s' not found", organizerId)
                ));

        Event eventToCreate = new Event();


        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setEvent(eventToCreate);
                    return ticketTypeToCreate;
                }).toList();

        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSaleStart(event.getSalesStart());
        eventToCreate.setSaleEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID eventId) {
        return eventRepository.findByIdAndOrganizerId(eventId, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID eventId, UpdateEventRequest event) {
        if (event.getId() == null) {
            throw new EventUpdateException("Event ID cannot be null");
        }

        if (!eventId.equals(event.getId())) {
            throw new EventUpdateException("Cannot update the ID f an event");
        }

        Event existingEvent = eventRepository.findByIdAndOrganizerId(eventId, organizerId).orElseThrow( () -> new EventNotFoundException(
                String.format("Event with ID '%s' does not exist", eventId)
        ));

        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSaleStart(event.getSalesStart());
        existingEvent.setSaleEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());


        Set<UUID> requestTicketTypesId = event.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf( existingTicketType ->
                !requestTicketTypesId.contains(existingTicketType.getId())
        );

        Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes()
                .stream()
                .collect(
                    Collectors.toMap(TicketType::getId, Function.identity())
        );

        for (UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
            if (ticketType.getId() == null) {
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);
            } else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {
                TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());
                existingTicketType.setDescription(ticketType.getDescription());
            } else {
                throw new TicketTypeNotFoundException(
                        String.format("Ticket Type with ID '%s' not found", ticketType.getId())
                );
            }
        }

        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEventForOrganizer(UUID organizerId, UUID eventId) {
        getEventForOrganizer(organizerId, eventId).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query, pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID id) {
        return eventRepository.findByIdAndStatus(id, EventStatusEnum.PUBLISHED);
    }


}
