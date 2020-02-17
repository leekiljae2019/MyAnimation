package com.afterwork.myanimation.view.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.afterwork.myanimation.R
import com.afterwork.myanimation.model.room.common.ContentType
import com.afterwork.myanimation.view.common.DepthPageTransformer
import com.afterwork.myanimation.view.common.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity(){
    companion object{
        val TAG = "DetailActivity"
        val CONTENT_TYPE = "content_type"
        val INDEX = "index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        var type : ContentType = intent.getSerializableExtra(CONTENT_TYPE) as ContentType
        var index = intent.getIntExtra(INDEX, 0)
        Log.d(TAG, "onCreate($type, $index)")

        pager.adapter = ScreenSlidePagerAdapter(this, type, index)
        pager.setPageTransformer(DepthPageTransformer())
        pager.setOffscreenPageLimit(3)
    }

    private inner class ScreenSlidePagerAdapter(fa: AppCompatActivity, val type: ContentType, val index: Int) : FragmentStateAdapter(fa) {
        val details = mutableListOf<DetailFragment>()

        override fun getItemCount(): Int{
            Log.d(TAG, "getItemCount() size: ${details.size+1}")
            return details.size+1
        }

        override fun getItem(position: Int): Fragment {
            Log.d(TAG, "getItem($index + $position)")

            if(position >= details.size){
                details.add(DetailFragment(type, index + position))
            }

            return details[position]
        }

    }
}