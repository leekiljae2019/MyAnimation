package com.afterwork.myanimation.model.room

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.afterwork.myanimation.model.room.common.ContentType
import com.afterwork.myanimation.model.room.common.ContentTypeConvertor

@Entity(tableName = "tb_contents", indices = arrayOf(Index(value = ["idx"])))
@TypeConverters(ContentTypeConvertor::class)
data class MyContentEntity(
    @ColumnInfo(name= "id") val id: Int,
    @ColumnInfo(name= "type") val type: ContentType,
    @ColumnInfo(name= "title") val title: String?,
    @ColumnInfo(name= "description") val description: String?,
    @ColumnInfo(name= "views_count") val viewsCount: Int,
    @ColumnInfo(name= "likes_count") val likesCount: Int,
    @ColumnInfo(name= "downloads_count") val downloadsCount: Int,
    @ColumnInfo(name= "thumbnail_image") val thumbnailImage: String,
    @ColumnInfo(name= "preview_image") val previewImage: String,
    @ColumnInfo(name= "image") val image: String
){
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="idx") var idx: Int = 0
}

//@Entity(foreignKeys = arrayOf(ForeignKey(
//    entity = MyContentEntity::class,
//    parentColumns = arrayOf("id"),
//    childColumns = arrayOf("contentId"),
//    onDelete = CASCADE,
//    onUpdate = CASCADE
//)), tableName = "tb_images", indices = arrayOf(Index(value = ["contentId"])))
//data class MyImageEntity(
//    @ColumnInfo(name= "contentId") val id: Int,
//    @ColumnInfo(name= "type") val type: String,
//    @PrimaryKey @ColumnInfo(name= "url") val url: String
//)

