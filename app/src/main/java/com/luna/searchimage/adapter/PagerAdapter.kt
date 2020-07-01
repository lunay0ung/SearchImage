package com.luna.searchimage.adapter


import android.os.Parcelable
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.luna.searchimage.ui.bookmark.BookmarkFragment
import com.luna.searchimage.ui.search.ImageListFragment

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val fragments:MutableList<Fragment> = ArrayList()
    val titles:MutableList<String> = ArrayList()

    fun addFragment(fragment: Fragment, title:String){
        fragments.add(fragment)
        titles.add(title)
    }

    fun replaceFragment(index: Int, fragment: Fragment) {
        fragments.removeAt(0)
        fragments.add(0, fragment)
    }

    override fun getItem(p0: Int): Fragment = fragments[p0]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getItemPosition(`object`: Any): Int {

        return PagerAdapter.POSITION_NONE
    }



}