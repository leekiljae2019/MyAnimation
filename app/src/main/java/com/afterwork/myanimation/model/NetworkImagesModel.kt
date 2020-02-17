package com.afterwork.myanimation.model

import com.afterwork.myanimation.model.network.MyContents
import io.reactivex.Single

interface NetworkImagesModel {
    fun getRecent(): Single<MyContents>
    fun getRecentMore(last_pos: String): Single<MyContents>
    fun getMonthly(): Single<MyContents>
    fun getMonthlyMore(last_pos: String): Single<MyContents>
    fun getDaily(): Single<MyContents>
    fun getDailyMore(last_pos: String): Single<MyContents>
}