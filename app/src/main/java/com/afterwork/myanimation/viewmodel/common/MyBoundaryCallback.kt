package com.afterwork.myanimation.viewmodel.common

import android.util.Log
import androidx.paging.PagedList
import com.afterwork.myanimation.model.room.MyContentsDao
import com.afterwork.myanimation.model.room.MyContentEntity
import com.afterwork.myanimation.model.room.common.ContentType

class MyBoundaryCallback(val type: ContentType, val dao: MyContentsDao, val refreshing: NotNullMutableLiveData<Boolean>): PagedList.BoundaryCallback<MyContentEntity>(){
    companion object{
        val TAG = "MyBoundaryCallback"
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.d(TAG, "onZeroItemsLoaded() type: $type")
        MyContentsTaskBulider.bulidNetworkTask(type, dao, refreshing).execute()
    }

    override fun onItemAtEndLoaded(itemAtEnd: MyContentEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        Log.d(TAG, "onItemAtEndLoaded() type: $type")
        MyContentsTaskBulider.bulidNetworkTask(type, dao, refreshing).execute()
    }
}