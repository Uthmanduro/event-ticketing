package com.druth.event_ticketing.mappers;

import com.druth.event_ticketing.domain.CreateEventRequest;
import com.druth.event_ticketing.domain.CreateTicketTypeRequest;
import com.druth.event_ticketing.domain.UpdateEventRequest;
import com.druth.event_ticketing.domain.UpdateTicketTypeRequest;
import com.druth.event_ticketing.domain.dtos.*;
import com.druth.event_ticketing.domain.entities.Event;
import com.druth.event_ticketing.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventDetailsTicketTypeResponseDto toGetEventDetailsTicketTypeDto(TicketType ticketType);
    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);
    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);
    UpdateEventResponseDto toUpdateEventResponseDto(Event event);

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event publishedEvents);

    GetPublishedEventDetailsTicketTypeResponseDto toListGetPublishedEventTDetailsTicketTypeResponse(TicketType ticketType);
    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);

}