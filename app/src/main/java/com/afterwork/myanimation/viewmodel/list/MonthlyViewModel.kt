package com.afterwork.myanimation.viewmodel.list

import com.afterwork.myanimation.model.room.MyContentsDao
import com.afterwork.myanimation.model.room.common.ContentType
import com.afterwork.myanimation.viewmodel.common.BaseMyContentsViewModel

class MonthlyViewModel(dao: MyContentsDao): BaseMyContentsViewModel(dao) {

    override val contentType: ContentType
        get() = ContentType.MONTHLY
}