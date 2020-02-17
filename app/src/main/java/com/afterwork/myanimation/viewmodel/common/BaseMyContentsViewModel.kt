package com.afterwork.myanimation.viewmodel.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.afterwork.myanimation.model.room.MyContentEntity
import com.afterwork.myanimation.model.room.MyContentsDao
import com.afterwork.myanimation.model.room.common.ContentType

abstract class BaseMyContentsViewModel(val dao: MyContentsDao) : ViewModel(){

    companion object{
        val TAG = "BaseMyContentsViewModel"
    }
    val _refreshing: NotNullMutableLiveData<Boolean> = NotNullMutableLiveData(false)
    val refreshing: LiveData<Boolean> get() = _refreshing

    var pagedListBuilder: LivePagedListBuilder<Int, MyContentEntity>
    var items: LiveData<PagedList<MyContentEntity>>? = null

    abstract val contentType: ContentType

    init {
        Log.d(TAG, "init ContentType: $contentType")

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(36)
            .setPrefetchDistance(4)
            .setPageSize(36)
            .build()

        pagedListBuilder = LivePagedListBuilder<Int, MyContentEntity>(
            getPagingItems(),
            config
        ).setBoundaryCallback(MyBoundaryCallback(contentType, dao, _refreshing))
    }

    fun getPagingItems(): DataSource.Factory<Int, MyContentEntity> {
        when(contentType){
            ContentType.MONTHLY -> return dao.getMonthlyPagingItems()
            ContentType.DAILY -> return dao.getDailyPagingItems()
            else -> return dao.getRecentPagingItems()
        }
    }

    fun load(key: Int): LiveData<PagedList<MyContentEntity>> {
        items = pagedListBuilder.setInitialLoadKey(key).build()
        _refreshing.value = false
        return items!!
    }

    fun onRefreshing(){
        _refreshing.value = false
    }
}
