package com.luna.searchimage.bookmark

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.luna.searchimage.R
import com.luna.searchimage.adapter.BookmarkAdapter
import com.luna.searchimage.ui.detail.ImageDetailActivity
import kotlinx.android.synthetic.main.book_mark_fragment.*

class BookmarkFragment : Fragment(), BookmarkAdapter.ItemClickListener {

    private val TAG = BookmarkFragment::class.java.simpleName
    private lateinit var bookmarkListViewModel: BookmarkListViewModel
    private lateinit var adapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.book_mark_fragment, container, false)
    }

    override fun onBookmarkDeleted(bookmark: Bookmark, position: Int, isBookmarked: Boolean) {
        if(!isBookmarked) { //해제함
            Log.d(TAG, "북마크 해제 데이터보네: ${bookmark.thumbnailUrl}")
            bookmarkListViewModel.deleteBookmark(bookmark)
            dataPasser.onDataPass(bookmark.keyword)

        }
    }

    interface OnDataPass {
        fun onDataPass(data: String)
    }

    lateinit var dataPasser: OnDataPass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnDataPass
    }

    override fun onBookmarkImageClicked(bookmark: Bookmark) {
        val intent = Intent(context, ImageDetailActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("siteName", bookmark.siteName)
        intent.putExtra("imageUrl", bookmark.imageUrl)
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = BookmarkAdapter(mutableListOf(), this)

        recyclerView.adapter = adapter
        bookmarkListViewModel = ViewModelProvider(this).get(BookmarkListViewModel::class.java)
        bookmarkListViewModel.getAllBookmark().observe(viewLifecycleOwner, Observer<List<Bookmark>>   { bookmarkList ->
            adapter.swapData(bookmarkList)
        })
    }

}