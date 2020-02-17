package com.afterwork.myanimation.viewmodel.detail

import android.os.AsyncTask
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afterwork.myanimation.model.room.MyContentsDao
import com.afterwork.myanimation.model.room.common.ContentType
import com.afterwork.myanimation.model.room.common.ContentTypeConvertor

class DetailViewModel(type: ContentType, index: Int, dao: MyContentsDao) : ViewModel(){
    companion object{
        val TAG = "DetailViewModel"
    }

    val imageUrl : MutableLiveData<String> = MutableLiveData("")
    val title : MutableLiveData<String> = MutableLiveData("")
    val desc : MutableLiveData<String> = MutableLiveData("")
    val counts : MutableLiveData<String> = MutableLiveData("")
    val visibility : MutableLiveData<Int> = MutableLiveData(View.GONE)

    init {
        AsyncTask.execute({
            var item = dao.getItem(ContentTypeConvertor.fromContentType(type), index)
            if(item == null){
                visibility.postValue(View.VISIBLE)
                return@execute
            }

            visibility.postValue(View.GONE)
            Log.d(TAG, "Index: $index, imageUrl: ${item.image}")

            imageUrl.postValue(item?.image)
            counts.postValue(
                "Likes: ${item?.likesCount}, Downloads: ${item?.downloadsCount}, Views: ${item?.viewsCount}"
            )
            title.postValue(item?.title)
            desc.postValue(item?.description)
        })
    }
}