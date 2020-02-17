package com.afterwork.myanimation.model

import com.afterwork.myanimation.model.network.ImageApis
import com.afterwork.myanimation.model.network.MyContents
import io.reactivex.Single

class NetworkImagesModelImpl(private val service: ImageApis): NetworkImagesModel {
    override fun getRecent(): Single<MyContents> {
        return service.getRecent()
    }

    override fun getRecentMore(last_pos: String): Single<MyContents> {
        return service.getRecentMore(last_pos)
    }

    override fun getMonthly(): Single<MyContents> {
        return service.getMonthly()
    }

    override fun getMonthlyMore(last_pos: String): Single<MyContents> {
        return service.getMonthlyMore(last_pos)
    }

    override fun getDaily(): Single<MyContents> {
        return service.getDaily()
    }

    override fun getDailyMore(last_pos: String): Single<MyContents> {
        return service.getDailyMore(last_pos)
    }
}