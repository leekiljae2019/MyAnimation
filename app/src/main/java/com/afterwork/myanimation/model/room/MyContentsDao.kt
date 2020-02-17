package com.afterwork.myanimation.model.room

import androidx.paging.DataSource
import androidx.room.*
import com.afterwork.myanimation.model.room.common.ContentType

@Dao
interface MyContentsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: MyContentEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun bulkInsert(items: List<MyContentEntity>)

    @Delete
    fun delete(item: MyContentEntity)

    @Query("DELETE FROM tb_contents")
    fun deleteAll()

    @Query("SELECT * FROM tb_contents WHERE type = 0 ORDER BY idx ASC")
    fun getRecentAll(): List<MyContentEntity>

    @Query("SELECT * FROM tb_contents WHERE type = 1 ORDER BY idx ASC")
    fun getMonthlyAll(): List<MyContentEntity>

    @Query("SELECT * FROM tb_contents WHERE type = 2 ORDER BY idx ASC")
    fun getDailyAll(): List<MyContentEntity>

    @Query("SELECT COUNT(id) FROM tb_contents WHERE type = 0")
    fun getRecentCount(): Int

    @Query("SELECT COUNT(id) FROM tb_contents WHERE type = 1")
    fun getMonthlyCount(): Int

    @Query("SELECT COUNT(id) FROM tb_contents WHERE type = 2")
    fun getDailyCount(): Int

    @Query("SELECT * FROM tb_contents WHERE type = 0 ORDER BY idx ASC")
    fun getRecentPagingItems(): DataSource.Factory<Int, MyContentEntity>

    @Query("SELECT * FROM tb_contents WHERE type = 1 ORDER BY idx ASC")
    fun getMonthlyPagingItems(): DataSource.Factory<Int, MyContentEntity>

    @Query("SELECT * FROM tb_contents WHERE type = 2 ORDER BY idx ASC")
    fun getDailyPagingItems(): DataSource.Factory<Int, MyContentEntity>

    @Query("SELECT idx, id, type, thumbnail_image, preview_image, image, title, description, likes_count, downloads_count, views_count FROM tb_contents WHERE type = :type AND idx = :index")
    fun getItem(type: Int, index: Int): MyContentEntity
}