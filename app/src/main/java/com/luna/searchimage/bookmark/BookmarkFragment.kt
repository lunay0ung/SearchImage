package com.luna.searchimage.bookmark

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.luna.searchimage.R
import kotlinx.android.synthetic.main.book_mark_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment(), BookmarkAdapter.ItemClickListener {

    private lateinit var bookmarkListViewModel: BookmarkListViewModel
    private lateinit var adapter: BookmarkAdapter
    lateinit var clickListener: View.OnClickListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.book_mark_fragment, container, false)

    }

    override fun onItemClick(bookmark: Bookmark, position: Int, isBookmarked: Boolean) {
        if(!isBookmarked) { //해제함
            bookmarkListViewModel.deleteBookmark(bookmark)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = BookmarkAdapter(mutableListOf(), this)

        recyclerView.adapter = adapter
        adapter.setOnBookmarkTapListener { bookmark ->
          //  val fragment = DetailsFragment.newInstance(player)

          //  fragment.show(supportFragmentManager, "DetailsFragment")
        }

        bookmarkListViewModel = ViewModelProvider(this).get(BookmarkListViewModel::class.java)
        bookmarkListViewModel.getAllBookmark().observe(viewLifecycleOwner, Observer<List<Bookmark>> { bookmarkList ->
            adapter.swapData(bookmarkList)
        })
    }

}