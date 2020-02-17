package com.afterwork.myanimation.model.network

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class MyContents(private var next: String,
                      private var data: List<MyContent>): BaseObservable(){
    @Bindable
    fun getNext() = next

    @Bindable
    fun getData() = data

    fun setNext(next: String){
        this.next = next
    }

    fun setData(data: List<MyContent>){
        this.data = data
    }
}