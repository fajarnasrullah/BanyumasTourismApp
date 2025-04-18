package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetDestinationByTitle(private val repository: TourismRepository) {

    suspend operator fun invoke(title: String): List<Destination> {
        return repository.getDestinationByTitle(title)
    }

}
