package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Ticket
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class DeleteTicket(private val repository: TourismRepository) {
    suspend operator fun invoke(ticket: Ticket) {
        return repository.deleteTicket(ticket)
    }
}