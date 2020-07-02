package com.luna.searchimage

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.luna.searchimage.view.adapter.PagerAdapter
import com.luna.searchimage.view.ui.BookmarkFragment
import com.luna.searchimage.view.ui.ImageListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BookmarkFragment.OnDataPass {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var searchWord : String
    private lateinit var fragmentAdapter: PagerAdapter
    lateinit var sharedPref: SharedPreferences

    companion object {
        const val LAST_QUERY_KEY = "lastQuery"
        var LAST_QUERY_VALUE = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentAdapter =
            PagerAdapter(supportFragmentManager)
        pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(pager)

        fragmentAdapter.addFragment(ImageListFragment(), "이미지 검색결과")
        fragmentAdapter.addFragment(BookmarkFragment(), "즐겨찾기")
        fragmentAdapter.notifyDataSetChanged()

        sharedPref = this.getSharedPreferences(this.packageName, 0)
        LAST_QUERY_VALUE = sharedPref.getString(
            LAST_QUERY_KEY, "smoothie").toString()
    }

    override fun onDataPass(data: String) {
        Log.d(TAG, ">>> 전달받은 데이터: $data")
        //fragmentAdapter.replaceFragment(0, ImageListFragment.newInstance(data))
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
                    searchWord = query
                    fragmentAdapter.replaceFragment(0, ImageListFragment.newInstance(query))
                    fragmentAdapter.notifyDataSetChanged()

                    val editor = sharedPref.edit()
                    editor.putString(LAST_QUERY_KEY, searchWord)
                    editor.apply()
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }


}