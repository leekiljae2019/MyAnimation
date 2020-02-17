package com.afterwork.myanimation

import android.app.Application
import com.afterwork.myanimation.di.myDiModule
import com.afterwork.myanimation.model.room.MyContentsDatabase
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()

        MyContentsDatabase.getInstance(applicationContext)

        Fresco.initialize(applicationContext)
        startKoin {
            androidContext(applicationContext)
            modules(myDiModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()

        stopKoin()

        MyContentsDatabase.destoryInstance()
    }
}