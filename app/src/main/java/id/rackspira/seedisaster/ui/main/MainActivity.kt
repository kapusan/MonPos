package id.rackspira.seedisaster.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.data.network.entity.DataPosko
import id.rackspira.seedisaster.ui.main.home.HomeFragment
import id.rackspira.seedisaster.ui.main.infoglobal.GlobalFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val homeFragment = HomeFragment()
    private val globalFragment = GlobalFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
    }

    private fun setUp() {
        setSupportActionBar(toolbarMain)
        val actionbarDrawer = ActionBarDrawerToggle(this, drawerLayout, toolbarMain, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(actionbarDrawer)
        actionbarDrawer.syncState()
        navigationMain.setNavigationItemSelectedListener(this)
        setFragment(homeFragment)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.beranda -> {
                setFragment(homeFragment)
                drawerLayout.closeDrawer(Gravity.START)
            }
            R.id.cuaca -> {

            }
            R.id.global -> {
                setFragment(globalFragment)
                drawerLayout.closeDrawer(Gravity.START)
            }
            R.id.tentang -> {

            }
        }
        return true
    }

    private fun setFragment(fragment: Fragment){
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.containerFragment, fragment)
        trans.commit()
    }

}
