package fr.isen.scamacca.androiderestaurant

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList: ArrayList<Fragment> = ArrayList()

    override fun getCount(): Int {
        return  fragmentList.size
    }

    override fun getItem(position: Int): Fragment{
        return fragmentList[position]
    }


    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }
}