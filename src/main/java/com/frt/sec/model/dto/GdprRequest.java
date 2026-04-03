package com.frt.sec.model.dto;

public record GdprRequest (String country,
                           String firstName,
                           String lastName,
                           String documentNumber,
                           String ticketId) {
}
