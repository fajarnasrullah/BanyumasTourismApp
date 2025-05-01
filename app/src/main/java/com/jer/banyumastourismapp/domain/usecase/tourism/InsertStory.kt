package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Story
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class InsertStory(val repository: TourismRepository) {
    suspend operator fun invoke(story: Story) {
        return repository.insertStory(story)
    }
}