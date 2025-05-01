package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Story
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetStory(val repository: TourismRepository) {
    suspend operator fun invoke(): List<Story> {
        return repository.getStory()
    }
}