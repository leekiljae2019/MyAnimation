package com.afterwork.myanimation.model.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApis {
    @GET("/api/v4/backgrounds/recent?format.json")
    fun getRecent(): Single<MyContents>

    @GET("/api/v4/backgrounds/recent?format.json")
    fun getRecentMore(@Query("last_pos") last_pos: String): Single<MyContents>

    @GET("/api/v4/backgrounds/popular/monthly?format.json")
    fun getMonthly(): Single<MyContents>

    @GET("/api/v4/backgrounds/popular/monthly?format.json")
    fun getMonthlyMore(@Query("last_pos") last_pos: String): Single<MyContents>

    @GET("/api/v4/backgrounds/popular/daily?format.json")
    fun getDaily(): Single<MyContents>

    @GET("/api/v4/backgrounds/popular/daily?format.json")
    fun getDailyMore(@Query("last_pos") last_pos: String): Single<MyContents>
}