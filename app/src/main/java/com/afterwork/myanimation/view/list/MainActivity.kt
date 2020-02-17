package com.afterwork.myanimation.view.list

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.afterwork.myanimation.R
import com.afterwork.myanimation.model.room.MyContentsDatabase
import com.afterwork.myanimation.view.common.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    companion object{
        val TAG = "MainActivity"
        val NUM_PAGES = 3
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if(savedInstanceState == null) {
            AsyncTask.execute({
                MyContentsDatabase.getInstance(applicationContext)?.myContentDao()?.deleteAll()
            })
        }

        pager.adapter = ScreenSlidePagerAdapter(this)
        pager.setPageTransformer(ZoomOutPageTransformer())
        pager.setOffscreenPageLimit(3)
    }

    private inner class ScreenSlidePagerAdapter(fa: AppCompatActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int =
            NUM_PAGES

        override fun getItem(position: Int): Fragment{
            Log.d(TAG, "getItem($position)")
            when(position){
                0-> return RecentFragment.newInstance()
                1-> return MonthlyFragment.newInstance()
                else -> return DailyFragment.newInstance()
            }
        }

    }
}
