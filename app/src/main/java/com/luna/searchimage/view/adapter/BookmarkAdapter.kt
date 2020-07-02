package com.luna.searchimage.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luna.searchimage.R
import com.luna.searchimage.service.source.local.Bookmark
import kotlinx.android.synthetic.main.image_list_item.view.*



class BookmarkAdapter(
    private val bookmarkList: MutableList<Bookmark>,
    private val mItemClickListener: ItemClickListener
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private var listener: ((Bookmark) -> Unit)? = null
    lateinit var sharedBookmarkList: List<Bookmark>

    companion object {
       // fun getBookmarkListData() : List<Bookmark> {
      //      return sharedBookmarkList
       // }
    }

    fun swapData(bookmarkList: List<Bookmark>) {
       //this.sharedBookmarkList = bookmarkList
        this.bookmarkList.clear()
        this.bookmarkList.addAll(bookmarkList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)
        return BookmarkViewHolder(
            view
        )
    }

    override fun getItemCount() = bookmarkList.size

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarkList[position]

        //sharedBookmarkList[position].imageUrl

        holder.itemView.setOnClickListener {
            mItemClickListener.onBookmarkImageClicked(bookmark!!)
        }

        bookmark?.let { holder.bind(it) }


        val resourceId = if (bookmarkList[position]!!.isBookmarked) {
            R.drawable.ic_star
        } else {
            R.drawable.ic_star_border
        }
        holder.itemView.bookmarkBtn.tag = position
        holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)

        holder.itemView.bookmarkBtn.setOnClickListener {
            if (!bookmark!!.isBookmarked) { //북마크 안되어있는데 눌렀으면 등록
                bookmark.isBookmarked = true
                mItemClickListener.onBookmarkDeleted(bookmark, position, true)
                changeViewState(holder, position, resourceId, true)
            } else {
                bookmark.isBookmarked = false
                mItemClickListener.onBookmarkDeleted(bookmark, position, false)
                changeViewState(holder, position, resourceId, false)
            }
        }
    }

    interface ItemClickListener{
        fun onBookmarkDeleted(bookmark: Bookmark, position: Int, isBookmarked: Boolean)
        fun onBookmarkImageClicked(bookmark: Bookmark)
    }


    fun changeViewState(holder: BookmarkViewHolder, position: Int, resourceId: Int, pressedState: Boolean) {
        if(holder.itemView.bookmarkBtn.tag.equals(position)) {
            holder.itemView.bookmarkBtn.isChecked = pressedState
            holder.itemView.bookmarkBtn.isPressed = pressedState

            holder.itemView.bookmarkBtn.setBackgroundResource(resourceId)
        }
    }


    class BookmarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TAG = BookmarkViewHolder::class.java.simpleName
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