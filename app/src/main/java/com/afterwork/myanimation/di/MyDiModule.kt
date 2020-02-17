package com.afterwork.myanimation.di


import com.afterwork.myanimation.model.network.ImageApis
import com.afterwork.myanimation.model.room.MyContentsDao
import com.afterwork.myanimation.model.room.MyContentsDatabase
import com.afterwork.myanimation.model.room.common.ContentType
import com.afterwork.myanimation.viewmodel.detail.DetailViewModel
import com.afterwork.myanimation.viewmodel.list.DailyViewModel
import com.afterwork.myanimation.viewmodel.list.MonthlyViewModel
import com.afterwork.myanimation.viewmodel.list.RecentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val API_BASE_URL = "http://bgh.ogqcorp.com"


var viewModelPart = module {
    viewModel {
        RecentViewModel(get())
    }

    viewModel {
        MonthlyViewModel(get())
    }

    viewModel {
        DailyViewModel(get())
    }

    viewModel {(type: ContentType, index: Int) ->
        DetailViewModel(type, index, get())
    }
}

var daoPart = module {
    single<MyContentsDao>{
        MyContentsDatabase.getInstance(androidContext())!!.myContentDao()
    }
}

var networkPart = module {
    single<ImageApis>{
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApis::class.java)
    }
}

var myDiModule = listOf(
    viewModelPart,
    daoPart,
    networkPart
)
