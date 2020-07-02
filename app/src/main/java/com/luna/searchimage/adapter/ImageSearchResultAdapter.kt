package com.luna.searchimage.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luna.searchimage.R
import com.luna.searchimage.bookmark.Bookmark
import com.luna.searchimage.bookmark.BookmarkAdapter
import com.luna.searchimage.bookmark.BookmarkFragment
import com.luna.searchimage.data.Image
import com.luna.searchimage.ui.search.ImageListFragment.Companion.finalKeyword
import kotlinx.android.synthetic.main.image_list_item.view.*


class ImageSearchResultAdapter(
    private val bookmarkList: List<Bookmark>,
    private val imageList: PagedList<Image>,
    private val mItemClickListener:ItemClickListener
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

       image?.isBookmarked = checkIfBookmarked(image!!)
        Log.d(TAG, ">>> 북마크 된건지? -> ${image.isBookmarked}")
        val resourceId =  if (image.isBookmarked) {
            R.drawable.ic_star
        } else {
            R.drawable.ic_star_border
        }
        holder.itemView.bookmarkBtn.tag = position
        holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)

        holder.itemView.bookmarkBtn.setOnClickListener{
            if(!image.isBookmarked) { //북마크 안되어있는데 눌렀으면 등록
                Log.d(TAG, ">>> 클릭111:  ${image.isBookmarked}")
                image.isBookmarked = true
                mItemClickListener.onBookmarkClicked(image, position, true)
                //holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)
                changeView(holder, position, R.drawable.ic_star)

            }else { //북마크 되어있는데 눌렀으면 해제
                Log.d(TAG, ">>> 클릭222:  ${image.isBookmarked}")
                image.isBookmarked = false
                Log.d(TAG, ">>> 클릭333 해제해야됨:  ${image.isBookmarked}")
                mItemClickListener.onBookmarkClicked(image, position, false)
                Log.d(TAG, ">>> 클릭444 리소스 변경해야됨:  ${image.isBookmarked}")
                Log.d(TAG, ">>> 포지션 111: $position")
                Log.d(TAG, ">>> 포지션tag 222: ${holder.itemView.bookmarkBtn.tag}")
                changeView(holder, position, R.drawable.ic_star_border)
            }

        }

    }

    //만약 이미지[position]이 북마크리스트에 있으면 북마킹 된것임! 단순함.
    fun checkIfBookmarked(image: Image): Boolean {
        var result:Boolean = false
        var i : Int = 0;
        Log.d(TAG, ">>> 이미지 url: ${image.thumbnailUrl}")
        for(bookmark in bookmarkList) {
            if(image.thumbnailUrl.equals(bookmark.thumbnailUrl)){
                i++
            }
            Log.d(TAG, ">>> 북마크 url: ${bookmark.thumbnailUrl}")
            result = image.thumbnailUrl.equals(bookmark.thumbnailUrl)
            Log.d(TAG, ">>> 북마크 url: ${result}")
        }
        if(i > 0)
            result = true
        return result
    }



    fun changeView(holder: ImageViewHolder, position: Int, resourceId: Int) {
        if(holder.itemView.bookmarkBtn.tag.equals(position)) {
          //  holder.itemView.bookmarkBtn.setBackgroundResource(R.drawable.ic_favorite_selector)
            holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)
        }
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
            Log.d(TAG, ">>image url: ${image.imageUrl}")

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
                oldItem.imageUrl == newItem.imageUrl
            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                newItem.imageUrl == oldItem.imageUrl
        }
    }
}