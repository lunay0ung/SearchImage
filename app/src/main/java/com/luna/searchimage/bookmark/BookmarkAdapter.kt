package com.luna.searchimage.bookmark

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luna.searchimage.R
import com.luna.searchimage.adapter.ImageSearchResultAdapter
import com.luna.searchimage.data.Image
import kotlinx.android.synthetic.main.image_list_item.view.*



class BookmarkAdapter(
    private val bookmarList: MutableList<Bookmark>,
    val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private var listener: ((Bookmark) -> Unit)? = null

    fun swapData(bookmarList: List<Bookmark>) {
        this.bookmarList.clear()
        this.bookmarList.addAll(bookmarList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun getItemCount() = bookmarList.size

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarList[position]

        holder.itemView.setOnClickListener {
            mItemClickListener.onImageClicked(bookmark!!)
        }

        bookmark?.let { holder.bind(it) }


        val resourceId = if (bookmarList[position]!!.isBookmarked) {
            R.drawable.ic_star
        } else {
            R.drawable.ic_star_border
        }
        holder.itemView.bookmarkBtn.tag = position
        holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)

        holder.itemView.bookmarkBtn.setOnClickListener {
            if (!bookmark!!.isBookmarked) { //북마크 안되어있는데 눌렀으면 등록
                bookmark.isBookmarked = true
                mItemClickListener.onBookmarkClicked(bookmark, position, true)
                changeView(holder, position, resourceId)

            } else {
                bookmark.isBookmarked = false
                mItemClickListener.onBookmarkClicked(bookmark, position, false)
                changeView(holder, position, resourceId)
            }
        }
    }

    interface ItemClickListener{
        fun onBookmarkClicked(bookmark: Bookmark, position: Int, isBookmarked: Boolean)
        fun onImageClicked(bookmark: Bookmark)
    }

    fun changeView(holder: BookmarkViewHolder, position: Int, resourceId: Int) {
        if(holder.itemView.bookmarkBtn.tag.equals(position))
            holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)
    }


    class BookmarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TAG = ImageSearchResultAdapter.ImageViewHolder::class.java.simpleName
        private val thumb = view.thumbnail
        private val siteName = view.siteName
        private val bookmarkBtn = view.bookmarkBtn

        var bookmark: Bookmark? = null
        fun bind(bookmark: Bookmark) {
            this.bookmark = bookmark
            Glide.with(thumb.context)
                .load(bookmark.thumbnailUrl)
                .placeholder(R.drawable.ic_defaultimage)
                .into(thumb)

            Log.d(TAG, ">>> 북마크 url: ${bookmark.thumbnailUrl}")
            siteName.text = bookmark.siteName
        }
    }
}