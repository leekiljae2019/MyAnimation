package com.afterwork.myanimation.view.list

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.afterwork.myanimation.R
import com.afterwork.myanimation.databinding.RecentFragmentBinding
import com.afterwork.myanimation.view.common.BaseFragment
import com.afterwork.myanimation.view.common.MyPagingAdapter
import kotlinx.android.synthetic.main.recent_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class RecentFragment: BaseFragment<RecentFragmentBinding>() {

    companion object {
        val TAG = "RecentFragment"
        fun newInstance() = RecentFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.recent_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.vmRecent = getViewModel()
        binding.lifecycleOwner = this

        val adapter = MyPagingAdapter(main, iv_image, resources.getInteger(android.R.integer.config_shortAnimTime))
        list.adapter = adapter

        binding.vmRecent?.load(0)?.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "CALLED binding.vmRecent.load().observe()")
            adapter.submitList(it)
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}
