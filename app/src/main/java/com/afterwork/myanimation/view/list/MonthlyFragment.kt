package com.afterwork.myanimation.view.list

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.afterwork.myanimation.R
import com.afterwork.myanimation.databinding.MonthlyFragmentBinding
import com.afterwork.myanimation.view.common.BaseFragment
import com.afterwork.myanimation.view.common.MyPagingAdapter
import kotlinx.android.synthetic.main.monthly_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MonthlyFragment: BaseFragment<MonthlyFragmentBinding>() {

    companion object {
        val TAG = "MonthlyFragment"
        fun newInstance() = MonthlyFragment()
    }

    override val layoutResourceId: Int
        get() = R.layout.monthly_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.vmMonthly = getViewModel()
        binding.lifecycleOwner = this

        val adapter = MyPagingAdapter(main, iv_image, resources.getInteger(android.R.integer.config_shortAnimTime))
        list.adapter = adapter

        binding.vmMonthly?.load(0)?.observe(viewLifecycleOwner, Observer{
            Log.d(TAG, "CALLED binding.vmMonthly.load().observe()")
            adapter.submitList(it)
        })
    }

}