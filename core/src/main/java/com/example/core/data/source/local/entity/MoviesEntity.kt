package com.example.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_movies")
data class MoviesEntity (

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id")
    var id: Int,
    @ColumnInfo(name="title")
    var title: String,
    @ColumnInfo(name="description")
    var description: String,
    @ColumnInfo(name="time_release")
    var timeRelease: String,
    @ColumnInfo(name="rating")
    var rating: String,
    @ColumnInfo(name="img_photo")
    var imgPhoto: String,
    @ColumnInfo(name="favorite")
    var favorite: Boolean = false

)