
package com.katepatty.kstaxi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import androidx.appcompat.app.ActionBarDrawerToggle

import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tool_bar.*

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController

// be replaced by kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.fragment_root.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    // 類別中的共用變數
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        floatCallOut.setOnClickListener { v -> snaker(v) } // v: View



        //This Activity already has an action bar supplied by the window decor.
        // Do not request Window.FEATURE_SUPPORT_ACTION_BAR
        // and set windowActionBar to false in your theme to use a Toolbar instead.

        // toolbar沒有呈現紫色？？？
        //setSupportActionBar(toolbar)

        // 根基碎片
        //val navController = findNavController(R.id.fragment_root)  // fragment_root

        // see com.google.android.material.navigation.NavigationView

        // Tool Bar has 1 fragment_root & 4 fragments for dynamic aims & showIn Drawerlayout

        //setupNavigationDrawer()
        setSupportActionBar(toolbar)

        val navController: NavController = findNavController(R.id.fragment_root)
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.myname,
            R.id.forcab,
            R.id.payment,
            R.id.data
        ).setDrawerLayout(drawer_layout).build()

        //val appBarConfiguration = AppBarConfiguration(navController.graph)

        //toolbar.setupWithNavController(navController, appBarConfiguration)

        setupActionBarWithNavController(navController, appBarConfiguration)

        nav_view.setupWithNavController(navController)


        //nav_view.setNavigationItemSelectedListener{ onNavigationItemSelected()}

        //nav_view.setNavigationItemSelectedListener(this)

        toggler()

        //drawer_layout.openDrawer(GravityCompat.START)
        //drawer_layout.closeDrawer(GravityCompat.START)

        //drawer_layout.addDrawerListener()
        nav_view.setNavigationItemSelectedListener(this)


    }

    private fun snaker(view: View){

        Snackbar.make(view, "Call Out for a new Cab now.", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }

    // Menu Inflater for Tool Bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }



    // Menu Clicked for Navigate
    // User click on the menu of nav_view on the DrawerLayout
    /*override fun onSupportNavigateUp(): Boolean {

        return findNavController(R.id.fragment_root).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }*/

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return findNavController(R.id.fragment_root).navigateUp(drawer_layout)
    }

    private fun toggler(){

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.


        when (item.itemId) {
            R.id.myname -> {
                val c = CustomerFragment()
                show(c).apply { intentToFrag(c) }
            }
            R.id.forcab -> {
                val t = TaxiFragment()
                show(t).apply { intentToFrag(t) }
            }
            R.id.payment -> {
                val p = PayFragment()
                show(p).apply { intentToFrag(p) }
            }
            R.id.data -> {
                val h = HistoryFragment()
                show(h).apply { intentToFrag(h)  }
            }
        }


        return true
    }

    private fun show(fragment: Fragment) {

        val fragmentManager = supportFragmentManager

        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_root, fragment)
            .commit()


        //supportFragmentManager.beginTransaction().add(R.id.fragment_container_view,
            //myFragmentInstance).commit()

        drawer_layout.closeDrawer(GravityCompat.START)
    }


    private fun intentToFrag(fragment: Fragment){

        fun navigateToProductDetails(productId: String) {
            val intent = Intent(this, fragment::class.java)
           // intent.putExtra(KEY_ID, productId)
            startActivity(intent)
        }

    }

}


