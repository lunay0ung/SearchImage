package com.luna.searchimage.bookmark

import android.content.DialogInterface
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
import com.luna.searchimage.ui.detail.ImageDetailActivity
import com.luna.searchimage.ui.search.ImageListFragment
import com.luna.searchimage.ui.search.ImageListViewModel
import kotlinx.android.synthetic.main.book_mark_fragment.*

class BookmarkFragment : Fragment(), BookmarkAdapter.ItemClickListener {

    private val TAG = BookmarkFragment::class.java.simpleName
    private lateinit var bookmarkListViewModel: BookmarkListViewModel
    private lateinit var adapter: BookmarkAdapter


    companion object {
        lateinit var  SHARED_BOOKMARK: List<Bookmark>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.book_mark_fragment, container, false)
    }

    override fun onBookmarkClicked(bookmark: Bookmark, position: Int, isBookmarked: Boolean) {
        if(!isBookmarked) { //해제함
            bookmarkListViewModel.deleteBookmark(bookmark)
        }
    }

    override fun onImageClicked(bookmark: Bookmark) {
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
            SHARED_BOOKMARK = bookmarkList
        })
    }

}