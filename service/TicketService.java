package com.example.trainbooking.service;

import com.example.trainbooking.dto.TicketResponse;

public interface TicketService {

    TicketResponse getTicketByPnr(String pnr);
}