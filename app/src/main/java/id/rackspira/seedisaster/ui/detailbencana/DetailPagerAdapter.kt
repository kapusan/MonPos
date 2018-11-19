package id.rackspira.seedisaster.ui.detailbencana

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.rackspira.seedisaster.ui.detailbencana.overview.OverviewFragment
import id.rackspira.seedisaster.ui.detailbencana.posko.PoskoFragment
import id.rackspira.seedisaster.ui.detailbencana.relawan.RelawanFragment

class DetailPagerAdapter(fragment: FragmentManager): FragmentStatePagerAdapter(fragment) {

    private var tabCount = 0

    internal fun setCount(count: Int) {
        this.tabCount = count
    }

    override fun getItem(p0: Int): Fragment? {
        return when (p0) {
            0 -> OverviewFragment()
            1 -> PoskoFragment()
            2 -> RelawanFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }
}