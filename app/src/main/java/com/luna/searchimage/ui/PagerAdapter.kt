package com.luna.searchimage.ui


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.luna.searchimage.ui.bookmark.BookmarkFragment
import com.luna.searchimage.ui.search.ImageListFragment

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ImageListFragment()
            1 -> BookmarkFragment()
            else -> ImageListFragment()
        }
    }


    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "이미지 검색결과"
            1 -> "즐겨찾기"
            else -> "";
        }
    }
}