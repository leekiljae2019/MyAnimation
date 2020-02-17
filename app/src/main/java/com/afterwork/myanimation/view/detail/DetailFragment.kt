package com.afterwork.myanimation.view.detail

import android.os.Bundle
import com.afterwork.myanimation.R
import com.afterwork.myanimation.databinding.DetailFragmentBinding
import com.afterwork.myanimation.model.room.common.ContentType
import com.afterwork.myanimation.view.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class DetailFragment(val type: ContentType, val index: Int) : BaseFragment<DetailFragmentBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.detail_fragment


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.vmDetail = getViewModel{ parametersOf(type, index) }
        binding.lifecycleOwner = this

    }

}