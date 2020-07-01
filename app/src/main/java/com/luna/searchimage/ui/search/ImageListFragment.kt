package com.luna.searchimage.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luna.searchimage.R
import com.luna.searchimage.adapter.ImageSearchResultAdapter
import com.luna.searchimage.bookmark.Bookmark
import com.luna.searchimage.data.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageListFragment : Fragment(), ImageSearchResultAdapter.ItemClickListener {

    private val TAG = ImageListFragment::class.java.simpleName
    private lateinit var recyclerView: RecyclerView
    private lateinit var editField: EditText
    private lateinit var imageListViewModel: ImageListViewModel
    private lateinit var adapter: ImageSearchResultAdapter
    private lateinit var imageClickListener: OnImageClicked
    private lateinit var bookmarkListener: ImageSearchResultAdapter.OnBookmarkCheckListener

    private lateinit var mCtx: Context

    companion object {
        fun newInstance(): ImageListFragment {
            return ImageListFragment()
        }

        fun newInstance(query: String): ImageListFragment {
            val fragment = ImageListFragment()
            val args = Bundle()
            args.putString("query", query)
            fragment.setArguments(args)
            return fragment
        }
        var SHOW_SEARCH_RESULT_MESSAGE =  false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.image_list_fragment, container, false)

    }

    override fun onItemClick(image: Image, position: Int, isBookmarked: Boolean) {
        Log.d(TAG, "클릭 $position, $isBookmarked")
        val bookmark = Bookmark(0, image.thumbnailUrl.toString(), image.imgUrl.toString(), image.siteName.toString(), true)
        if(isBookmarked) {
            imageListViewModel.insertBookmark(bookmark)
        }
        else {
            GlobalScope.launch(Dispatchers.IO) {
                imageListViewModel.deleteBookmark(bookmark)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)//LinearLayoutManager(context)
        mCtx = context!!

        val args = arguments
        var searchWord = args?.getString("query")

        if(searchWord.isNullOrEmpty())
            searchWord = "스무디"

        imageListViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ImageListViewModel(
                    context!!,
                    searchWord!!
                ) as T
            }
        }).get(ImageListViewModel::class.java)

        imageListViewModel.imagePagedList.observe(viewLifecycleOwner, Observer {
            adapter = ImageSearchResultAdapter(context!!, it, imageClickListener, this)
            adapter.submitList(it)
            recyclerView.adapter = adapter
        })


    }

    interface OnImageClicked {
        fun onImageClicked(image: Image)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnImageClicked) {
            imageClickListener = context
        } else {
            throw ClassCastException(
                context.toString() + " must implement OnImageClicked.")
        }
    }
}