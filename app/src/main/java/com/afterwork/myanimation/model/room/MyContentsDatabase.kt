package com.afterwork.myanimation.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(MyContentEntity::class), version = 1)
abstract class MyContentsDatabase: RoomDatabase(){
    companion object{
        private var INSTANCE: MyContentsDatabase? = null

        fun getInstance(appContext: Context): MyContentsDatabase?{
            if(INSTANCE == null){
                synchronized(MyContentsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        appContext,
                        MyContentsDatabase::class.java, "mycontents.db"
                    ).build()
                }
            }

            return INSTANCE
        }

        fun destoryInstance(){
            if(INSTANCE != null) {
                synchronized(MyContentsDatabase::class) {
                    INSTANCE?.close()
                    INSTANCE = null
                }
            }
        }
    }

    abstract fun myContentDao(): MyContentsDao
}