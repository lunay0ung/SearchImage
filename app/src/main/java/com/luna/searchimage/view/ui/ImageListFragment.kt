package com.luna.searchimage.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luna.searchimage.R
import com.luna.searchimage.view.adapter.ImageSearchResultAdapter
import com.luna.searchimage.service.source.local.Bookmark
import com.luna.searchimage.model.Image
import com.luna.searchimage.MainActivity.Companion.LAST_QUERY_VALUE
import com.luna.searchimage.viewmodel.ImageListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImageListFragment : Fragment(), ImageSearchResultAdapter.ItemClickListener {

    private val TAG = ImageListFragment::class.java.simpleName
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageListViewModel: ImageListViewModel
    private lateinit var adapter: ImageSearchResultAdapter
    private lateinit var mCtx: Context
    //private lateinit var finalKeyword: String
    companion object {
        fun newInstance(query: String): ImageListFragment {
            val fragment = ImageListFragment()
            val args = Bundle()
            args.putString("query", query)
            fragment.setArguments(args)
            return fragment
        }
        var finalKeyword: String = ""

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.image_list_fragment, container, false)
    }

    override fun onImageClicked(image: Image) {
        val intent = Intent(context, ImageDetailActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("siteName", image.siteName)
        intent.putExtra("imageUrl", image.imageUrl)
        startActivity(intent)
    }

    override fun onBookmarkClicked(image: Image, position: Int, isBookmarked: Boolean) {
        Log.d(TAG, "클릭 $position, $isBookmarked")
        val bookmark = Bookmark(0,
            finalKeyword, image.collection!! ,image.thumbnailUrl!!, image.imageUrl!!, image.width!!, image.height!!, image.siteName!!, image.docUrl!!, image.datetime!!, true)
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

        finalKeyword = if(args?.getString("query") != null) {
            args?.getString("query").toString()
        } else {
            LAST_QUERY_VALUE
        }

        imageListViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ImageListViewModel(
                    context!!,
                    finalKeyword
                ) as T
            }
        }).get(ImageListViewModel::class.java)

        val bookmarkList = imageListViewModel.getBookmarkByKeyword(finalKeyword)
        imageListViewModel.imagePagedList.observe(viewLifecycleOwner, Observer {
            adapter = ImageSearchResultAdapter(
                bookmarkList,
                it,
                this
            )
            adapter.submitList(it)
            recyclerView.adapter = adapter
        })
    }

}