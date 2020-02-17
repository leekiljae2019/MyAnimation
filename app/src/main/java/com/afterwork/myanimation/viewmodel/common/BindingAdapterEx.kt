package com.afterwork.myanimation.viewmodel.common

import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

@BindingAdapter("setImage")
fun setImage(view: SimpleDraweeView, image: String){
    view.setImageURI(image)
}
