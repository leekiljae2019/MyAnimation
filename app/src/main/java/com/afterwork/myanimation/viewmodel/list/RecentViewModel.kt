package com.afterwork.myanimation.viewmodel.list


import android.content.Intent
import android.util.Log
import com.afterwork.myanimation.model.room.MyContentsDao
import com.afterwork.myanimation.model.room.common.ContentType
import com.afterwork.myanimation.view.detail.DetailActivity
import com.afterwork.myanimation.view.list.RecentFragment
import com.afterwork.myanimation.viewmodel.common.BaseMyContentsViewModel


class RecentViewModel(dao: MyContentsDao) : BaseMyContentsViewModel(dao) {

    override val contentType: ContentType
        get() = ContentType.RECENT

}
