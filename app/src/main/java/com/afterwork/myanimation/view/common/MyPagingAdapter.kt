package com.afterwork.myanimation.view.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.afterwork.myanimation.R
import com.afterwork.myanimation.databinding.ItemImageBinding
import com.afterwork.myanimation.model.room.MyContentEntity
import com.afterwork.myanimation.view.detail.DetailActivity
import com.facebook.drawee.view.SimpleDraweeView

class MyPagingAdapter(val container: View, val viewImage: SimpleDraweeView, animation: Int): PagedListAdapter<MyContentEntity, MyPagingAdapter.MyViewHolder>(
DIFF_CALLBACK
){

    companion object{
        val TAG = "MyPagingAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MyContentEntity>() {
            // The ID property identifies when items are the same.
            override fun areItemsTheSame(oldItem: MyContentEntity, newItem: MyContentEntity) =
                oldItem.id == newItem.id

            // If you use the "==" operator, make sure that the object implements
            // .equals(). Alternatively, write custom data comparison logic here.
            override fun areContentsTheSame(
                oldItem: MyContentEntity, newItem: MyContentEntity) = oldItem == newItem
        }
    }

    private var currentAnimator: Animator? = null
    private var shortAnimationDuration: Int = 0

    init {
        shortAnimationDuration = animation
    }

    class MyViewHolder(view: View) : BindingViewHolder<ItemImageBinding>(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d(TAG, "onCreateViewHolder(viewType: $viewType)")

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_image,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder(position: $position)")
        holder.binding.item = getItem(position)
        holder.itemView.setOnClickListener{
            Log.d(TAG, "onItemClicked(position: $position)")
            var item = getItem(position)
            if(item != null){
                zoomImageFromThumb(holder.itemView.findViewById(R.id.iv_thumb), viewImage, holder.binding.item!!.thumbnailImage)
                var intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.CONTENT_TYPE, item.type)
                intent.putExtra(DetailActivity.INDEX, item.idx)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    fun zoomImageFromThumb(thumbView: SimpleDraweeView, expandedView: SimpleDraweeView, imageUrl: String) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()

        // Load the high-resolution "zoomed-in" image.
        expandedView.setImageURI(imageUrl)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        container.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expandedView.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedView.pivotX = 0f
        expandedView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(
                ObjectAnimator.ofFloat(
                    expandedView,
                View.X,
                startBounds.left,
                finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(expandedView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expandedView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDuration.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                    thumbView.alpha = 1f
                    thumbView.visibility = View.VISIBLE
                    expandedView.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                    thumbView.alpha = 1f
                    thumbView.visibility = View.VISIBLE
                    expandedView.visibility = View.INVISIBLE
                }
            })
            start()
        }
    }
}

abstract class BindingViewHolder<out T: ViewDataBinding>(_view: View): RecyclerView.ViewHolder(_view){
    val binding: T = DataBindingUtil.bind(_view)!!
}