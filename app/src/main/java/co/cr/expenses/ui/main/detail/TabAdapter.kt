package co.cr.expenses.ui.main.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import co.cr.expenses.model.Detail
import java.lang.IndexOutOfBoundsException

class TabAdapter(fragmentManager: FragmentManager, behavior: Int): FragmentPagerAdapter(fragmentManager, behavior){

    private val fragments = mutableListOf<RecordFragment>()
    private val titles = mutableListOf<String>()

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    fun updateFragment(position: Int, list: MutableList<Detail>){
        if(position >= fragments.size || position < 0){
            throw IndexOutOfBoundsException("Max allowed position was ${fragments.size} and the position received is ${position}")
        }
        fragments[position].updateList(list)
    }

    fun addFragment(fragment: RecordFragment, title: String){
        fragments.add(fragment)
        titles.add(title)
    }

}