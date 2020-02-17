package com.afterwork.myanimation.model.room.common

import androidx.room.TypeConverter

object ContentTypeConvertor {
    @TypeConverter
    @JvmStatic
    fun toContentType(type: Int): ContentType{
        when(type){
            1 -> return ContentType.MONTHLY
            2 -> return ContentType.DAILY
            else -> return ContentType.RECENT
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromContentType(type: ContentType): Int {
        when(type){
            ContentType.MONTHLY -> return 1
            ContentType.DAILY -> return 2
            ContentType.RECENT -> return 0
        }
    }
}