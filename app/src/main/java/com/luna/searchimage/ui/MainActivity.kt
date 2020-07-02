package com.luna.searchimage.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.luna.searchimage.R
import com.luna.searchimage.adapter.PagerAdapter
import com.luna.searchimage.bookmark.BookmarkFragment
import com.luna.searchimage.ui.search.ImageListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var searchWord : String
    private lateinit var fragmentAdapter: PagerAdapter
    lateinit var sharedPref: SharedPreferences

    companion object {
        val PRIVATE_MODE = 0
        val PREF_NAME = "com.dev.luna"
        const val LAST_QUERY_KEY = "lastQuery"
        var LAST_QUERY_VALUE = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentAdapter = PagerAdapter(supportFragmentManager)
        pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(pager)

        fragmentAdapter.addFragment(ImageListFragment(), "이미지 검색결과")
        fragmentAdapter.addFragment(BookmarkFragment(), "즐겨찾기")
        fragmentAdapter.notifyDataSetChanged()

        sharedPref = this.getSharedPreferences(this.packageName, PRIVATE_MODE)
        //if(!sharedPref.getString(ImageListFragment.LAST_QUERY, "smoothie").isNullOrBlank())
        LAST_QUERY_VALUE = sharedPref.getString(LAST_QUERY_KEY, "smoothie").toString()
        Log.d(TAG, ">>> 1111 저장한 검색 키워드: ${LAST_QUERY_VALUE}")


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