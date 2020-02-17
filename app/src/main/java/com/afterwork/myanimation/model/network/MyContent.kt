package com.afterwork.myanimation.model.network

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable


data class MyContent(private var id: Int,
                     private var title: String,
                     private var description: String,
                     private var images: List<MyImage>,
                     private var views_count: Int,
                     private var likes_count: Int,
                     private var downloads_count: Int): BaseObservable() {
    @Bindable
    fun getId() = id

    @Bindable
    fun getTitle() = title

    @Bindable
    fun getDescription() = description

    @Bindable
    fun getImages() = images

    @Bindable
    fun getViews_count() = views_count

    @Bindable
    fun getLikes_count() = likes_count

    @Bindable
    fun getDownloads_count() = downloads_count

    fun setId(id: Int){
        this.id = id
    }

    fun setTitle(title: String){
        this.title = title
    }

    fun setDescription(description: String){
        this.description = description
    }

    fun setImages(images: List<MyImage>){
        this.images = images
    }

    fun setLikes_counts(likes_count: Int){
        this.likes_count = likes_count
    }

    fun setViews_count(views_count: Int){
        this.views_count = views_count
    }

    fun setDownloads_count(downloads_count: Int){
        this.downloads_count = downloads_count
    }
}