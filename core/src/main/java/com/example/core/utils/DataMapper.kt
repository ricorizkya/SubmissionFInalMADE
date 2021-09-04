package com.example.core.utils

import com.example.core.data.source.local.entity.MoviesEntity
import com.example.core.data.source.remote.response.MoviesDataResponse
import com.example.core.domain.model.Movies

object DataMapper {

    fun mapResponseToEntities(input: List<MoviesDataResponse>): List<MoviesEntity> {
        val movieList = ArrayList<MoviesEntity>()
        input.map {
            val movie = MoviesEntity(
                it.id,
                it.title,
                it.description,
                it.timeRelease,
                it.rating,
                it.imgPhoto,
                false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MoviesEntity>): List<Movies> =
        input.map {
            Movies(
                    it.id,
                    it.title,
                    it.description,
                    it.timeRelease,
                    it.rating,
                    it.imgPhoto,
                    it.favorite
            )
        }

    fun mapDomainToEntity(input: Movies) = MoviesEntity(
        input.id,
        input.title,
        input.description,
        input.timeRelease,
        input.rating,
        input.imgPhoto,
        input.isFavorite
    )
}