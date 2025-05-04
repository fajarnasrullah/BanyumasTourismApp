package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Ticket
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetTicketHistory(private val repository: TourismRepository) {
    suspend operator fun invoke(uid: String): List<Ticket> {
        return repository.getTicketHistory(uid)
    }
}