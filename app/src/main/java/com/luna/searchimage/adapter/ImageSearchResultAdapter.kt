package com.luna.searchimage.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luna.searchimage.R
import com.luna.searchimage.data.Image
import com.luna.searchimage.ui.search.ImageListFragment
import kotlinx.android.synthetic.main.image_list_item.view.*


class ImageSearchResultAdapter(
    private val context: Context,
    private val imageList: PagedList<Image>,
    val mItemClickListener:ItemClickListener
) : PagedListAdapter<Image, ImageSearchResultAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

    private val TAG = ImageSearchResultAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list_item, parent, false)

        return ImageViewHolder(view)
    }

    override fun getItemCount() = imageList.size


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.itemView.setOnClickListener {
            mItemClickListener.onImageClicked(image!!)
        }
        image?.let { holder.bind(it) }


        val resourceId = if (imageList[position]!!.isBookmarked) {
            R.drawable.ic_star
        } else {
            R.drawable.ic_star_border
        }
         holder.itemView.bookmarkBtn.tag = position
        holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)

        holder.itemView.bookmarkBtn.setOnClickListener{
            if(!image!!.isBookmarked) { //북마크 안되어있는데 눌렀으면 등록
                image.isBookmarked = true
                mItemClickListener.onBookmarkClicked(image, position, true)
                //holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)
                changeView(holder, position, resourceId)

            }else {
                image.isBookmarked = false
                mItemClickListener.onBookmarkClicked(image, position, false)
                changeView(holder, position, resourceId)

                //holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)
            }
        }

    }

    fun changeView(holder: ImageViewHolder, position: Int, resourceId: Int) {
        if(holder.itemView.bookmarkBtn.tag.equals(position))
            holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)
    }


    interface ItemClickListener{
        fun onBookmarkClicked(image: Image, position: Int, isBookmarked: Boolean)
        fun onImageClicked(image: Image)
    }

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TAG = ImageViewHolder::class.java.simpleName
        private val thumb = view.thumbnail
        private val siteName = view.siteName
        private val bookmarkBtn = view.bookmarkBtn

        var image: Image? = null

        fun bind(image: Image) {
            Log.d(TAG, ">>image url: ${image.imgUrl}")

            this.image = image
            Glide.with(thumb.context)
                .load(image.thumbnailUrl)
                .placeholder(R.drawable.ic_defaultimage)
                .into(thumb)

            siteName.text = image.siteName
        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem.imgUrl == newItem.imgUrl
            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                newItem.imgUrl == oldItem.imgUrl
        }
    }
}