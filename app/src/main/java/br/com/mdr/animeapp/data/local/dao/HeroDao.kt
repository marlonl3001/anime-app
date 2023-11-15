package br.com.mdr.animeapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.mdr.animeapp.domain.model.Hero

@Dao
interface HeroDao {
    //Using PagingSource, allowing to paginate our db queries
    //Room supports Paging3 by default, so you don't need to create a custom PagingSource
    @Query("select * from hero_table ORDER BY id ASC")
    fun getHeroes(): PagingSource<Int, Hero>

    @Query("select * from hero_table WHERE id=:heroId")
    fun getSelectedHero(heroId: Int): Hero

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroes(heroes: List<Hero>)

    @Query("DELETE FROM hero_table")
    suspend fun deleteAllHeroes()
}