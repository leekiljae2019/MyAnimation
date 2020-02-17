package com.afterwork.myanimation.view.list

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.afterwork.myanimation.R
import com.afterwork.myanimation.databinding.DailyFragmentBinding
import com.afterwork.myanimation.view.common.BaseFragment
import com.afterwork.myanimation.view.common.MyPagingAdapter
import kotlinx.android.synthetic.main.daily_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DailyFragment: BaseFragment<DailyFragmentBinding>() {

    companion object {
        val TAG = "DailyFragment"
        fun newInstance() = DailyFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.daily_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.vmDaily = getViewModel()
        binding.lifecycleOwner = this

        val adapter = MyPagingAdapter(main, iv_image, resources.getInteger(android.R.integer.config_shortAnimTime))
        list.adapter = adapter

        binding.vmDaily?.load(0)?.observe(viewLifecycleOwner, Observer{
            Log.d(TAG, "CALLED binding.vmMonthly.load().observe()")
            adapter.submitList(it)
        })
    }

}
