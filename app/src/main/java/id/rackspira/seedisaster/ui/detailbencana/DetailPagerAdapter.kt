package id.rackspira.seedisaster.ui.detailbencana

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.rackspira.seedisaster.data.network.entity.ListBencana
import id.rackspira.seedisaster.ui.detailbencana.info.InfoFragment
import id.rackspira.seedisaster.ui.detailbencana.posko.PoskoFragment

class DetailPagerAdapter(fragment: FragmentManager): FragmentStatePagerAdapter(fragment) {

    private var tabCount = 0

    internal fun setCount(count: Int) {
        this.tabCount = count
    }

    override fun getItem(p0: Int): Fragment? {
        return when (p0) {
            0 -> InfoFragment()
            1 -> PoskoFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}