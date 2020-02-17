package com.afterwork.myanimation.viewmodel.common

import android.os.AsyncTask
import android.util.Log
import com.afterwork.myanimation.di.API_BASE_URL
import com.afterwork.myanimation.model.network.ImageApis
import com.afterwork.myanimation.model.network.MyContent
import com.afterwork.myanimation.model.network.MyContents
import com.afterwork.myanimation.model.network.MyImage
import com.afterwork.myanimation.model.room.MyContentEntity
import com.afterwork.myanimation.model.room.MyContentsDao
import com.afterwork.myanimation.model.room.common.ContentType
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MyContentsTaskBulider{
    fun bulidNetworkTask(type: ContentType, dao: MyContentsDao, refreshing: NotNullMutableLiveData<Boolean>) = NetworkTask(type, dao, refreshing)
}

class NetworkTask(val type: ContentType, val dao: MyContentsDao, val refreshing: NotNullMutableLiveData<Boolean>): AsyncTask<Void, Void, Boolean>(){

    companion object {
        val TAG = "NetworkTask"
        val moreMap: MutableMap<ContentType, String?> = mutableMapOf(
            ContentType.RECENT to null,
            ContentType.MONTHLY to null,
            ContentType.DAILY to null
        )
    }

    override fun doInBackground(vararg p0: Void?): Boolean {
        Log.d(TAG, "doInBackground type: $type")
        getApi(type, moreMap[type])
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    Log.d("NetworkTask", "Successed")
                    saveData(dao, it.getData(), type)
                    moreMap[type] = convertMore(it.getNext())
                }
            }, {
                Log.d("NetworkTask", "Failed: ${it.localizedMessage}")
                refreshing.postValue(false)
            })
        return true
    }

    override fun onPreExecute() {
        super.onPreExecute()

        refreshing.value = true
    }

//    override fun onPostExecute(result: Boolean?) {
//        super.onPostExecute(result)
//
//        refreshing.value = false
//    }

    fun getApi(type: ContentType, more: String?): Single<MyContents>{

        val apis = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApis::class.java)

        if(more.isNullOrEmpty() == true) {
            when (type) {
                ContentType.MONTHLY -> return apis.getMonthly()
                ContentType.DAILY -> return apis.getDaily()
                else -> return apis.getRecent()
            }
        }else {
            when(type){
                ContentType.MONTHLY -> return apis.getMonthlyMore(more)
                ContentType.DAILY -> return apis.getDailyMore(more)
                else -> return apis.getRecentMore(more)
            }
        }
    }

    fun saveData(dao: MyContentsDao, datas: List<MyContent>, type: ContentType){
        DatabaseTask(type, dao, datas, refreshing).execute()
    }

    fun convertMore(url: String?): String{
        if(url.isNullOrEmpty())
            return ""
        return url.substring(url.indexOf("last_pos=") + "last_pos=".length)
    }

}

class DatabaseTask(val type: ContentType, val dao: MyContentsDao, val datas: List<MyContent>, val refreshing: NotNullMutableLiveData<Boolean>): AsyncTask<Void, Void, Boolean>(){
    companion object{
        val TAG = "DatabaseTask"
    }

    override fun doInBackground(vararg p0: Void?): Boolean {
        val items = ArrayList<MyContentEntity>()
        for(data in datas){
            items.add(MyContentEntity(
                data.getId(),
                type,
                data.getTitle(),
                data.getDescription(),
                data.getViews_count(),
                data.getLikes_count(),
                data.getDownloads_count(),
                getThumbnailImage(data.getImages()),
                getPreviewImage(data.getImages()),
                getImage(data.getImages())
            ))
        }

        dao.bulkInsert(items)

        return true
    }

    override fun onPostExecute(result: Boolean?) {
        super.onPostExecute(result)

        refreshing.value = false
    }

    fun getThumbnailImage(imgs: List<MyImage>): String{
        for(img in imgs){
            if("thumbnail".equals(img.getType())) {
                return img.getUrl()
            }
        }

        return ""
    }

    fun getPreviewImage(imgs: List<MyImage>): String{
        for(img in imgs){
            if("preview".equals(img.getType())) {
                return img.getUrl()
            }
        }

        return ""
    }

    fun getImage(imgs: List<MyImage>): String{
        for(img in imgs){
            if("image".equals(img.getType())) {
                return img.getUrl()
            }
        }

        return ""
    }
}