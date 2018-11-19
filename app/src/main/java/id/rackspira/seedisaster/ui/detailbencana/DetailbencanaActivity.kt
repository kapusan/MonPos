package id.rackspira.seedisaster.ui.detailbencana

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import id.rackspira.seedisaster.R
import kotlinx.android.synthetic.main.activity_detail_bencana.*

@Suppress("SpellCheckingInspection")
class DetailbencanaActivity : AppCompatActivity() {

    internal lateinit var pagerAdapter: DetailPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bencana)
        setUp()
    }

    private fun setUp() {
        setSupportActionBar(toolbarDet)
        pagerAdapter = DetailPagerAdapter(supportFragmentManager)
        pagerAdapter.count = 3
        viewPagerDetail.adapter = pagerAdapter
        tabLayoutDet.addTab(tabLayoutDet.newTab().setText(R.string.overview))
        tabLayoutDet.addTab(tabLayoutDet.newTab().setText(R.string.posko))
        tabLayoutDet.addTab(tabLayoutDet.newTab().setText(R.string.relawan))
        viewPagerDetail.offscreenPageLimit = tabLayoutDet.tabCount
        viewPagerDetail.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutDet))
        tabLayoutDet.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPagerDetail.currentItem = p0!!.position
            }
        })
    }

}
