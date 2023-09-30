package br.com.mdr.animeapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mdr.animeapp.util.Constants.HERO_DATABASE_TABLE

@Entity(HERO_DATABASE_TABLE)
data class Hero(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val anime: String,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val month: String,
    val day: String,
    val abilities: List<String>,
    val family: List<String>,
    val natureTypes: List<String>
)