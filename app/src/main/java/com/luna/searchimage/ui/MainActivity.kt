package com.luna.searchimage.ui

import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewGroupCompat
import androidx.viewpager.widget.ViewPager
import com.luna.searchimage.R
import com.luna.searchimage.adapter.PagerAdapter
import com.luna.searchimage.data.Image
import com.luna.searchimage.ui.bookmark.BookmarkFragment
import com.luna.searchimage.ui.detail.ImageDetailActivity
import com.luna.searchimage.ui.search.ImageListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ImageListFragment.OnImageClicked {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var searchWord : String
    private lateinit var fragmentAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentAdapter = PagerAdapter(supportFragmentManager)
        pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(pager)

        fragmentAdapter.addFragment(ImageListFragment(), "이미지 검색결과")
        fragmentAdapter.addFragment(BookmarkFragment(), "즐겨찾기")
        fragmentAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "키워드 입력"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrBlank()) {
                    Log.d(TAG, ">>> 입력한 검색 키워드: $query")
                    //BookListFragment.SHOW_SEARCH_RESULT_MESSAGE = true
                    searchWord = query
                    fragmentAdapter.replaceFragment(0, ImageListFragment.newInstance(query))
                    fragmentAdapter.notifyDataSetChanged()
                }

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }

    override fun onImageClicked(image: Image) {
       // Toast.makeText(this, "${image.collection}.", Toast.LENGTH_LONG).show()
        val intent = Intent(this@MainActivity, ImageDetailActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("siteName", image.siteName)
        intent.putExtra("imageUrl", image.imgUrl)
        startActivity(intent)
    }
}