package id.rackspira.seedisaster.ui.detailbencana

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import id.rackspira.seedisaster.data.network.entity.ListBencana
import id.rackspira.seedisaster.ui.detailbencana.info.InfoFragment
import id.rackspira.seedisaster.ui.detailbencana.posko.PoskoFragment

class DetailPagerAdapter(private val list: ListBencana, fragment: FragmentManager) :
    FragmentStatePagerAdapter(fragment) {

    private var tabCount = 0

    internal fun setCount(count: Int) {
        this.tabCount = count
    }

    override fun getItem(p0: Int): Fragment? {
        return when (p0) {
            0 -> {
                newInstance(list)
            }
            1 -> {
                newInstance2(list)
            }
            else -> null
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

    companion object {
        fun newInstance(list: ListBencana) = InfoFragment().apply {
            arguments = Bundle().apply {
                putParcelable("posisi", list)
            }
        }

        fun newInstance2(list: ListBencana) = PoskoFragment().apply {
            arguments = Bundle().apply {
                putParcelable("posisi", list)
            }
        }
    }
}