package br.com.mdr.animeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.mdr.animeapp.domain.model.HeroRemoteKey

@Dao
interface HeroRemoteKeyDao {
    @Query("SELECT * from hero_remote_key_table WHERE id=:id")
    suspend fun getRemoteKey(id: Int): HeroRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<HeroRemoteKey>)

    @Delete
    suspend fun deleteAll()
}