package com.afterwork.myanimation.model.network

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class MyImage(private var url: String,
                   private var type: String): BaseObservable() {
    @Bindable
    fun getUrl() = url

    @Bindable
    fun getType() = type

    fun setUrl(url: String){
        this.url = url
    }

    fun setType(type: String){
        this.type = type
    }
}