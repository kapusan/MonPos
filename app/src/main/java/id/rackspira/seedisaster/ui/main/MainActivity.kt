package id.rackspira.seedisaster.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.MenuItem
import id.rackspira.seedisaster.R
import id.rackspira.seedisaster.ui.main.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val homeFragment = HomeFragment()

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
            R.id.navigationHome -> {
                setFragment(homeFragment)
                drawerLayout.closeDrawer(Gravity.START)
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
