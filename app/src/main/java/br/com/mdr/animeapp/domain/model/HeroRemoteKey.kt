package br.com.mdr.animeapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mdr.animeapp.util.Constants.HERO_DATABASE_REMOTE_KEY_TABLE

@Entity(tableName = HERO_DATABASE_REMOTE_KEY_TABLE)
data class HeroRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)