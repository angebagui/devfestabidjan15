package org.gdgmiagegi.devfestabidjan15.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem

import org.gdgmiagegi.devfestabidjan15.R
import org.gdgmiagegi.devfestabidjan15.fragment.ScheduleFragment
import org.jetbrains.anko.find

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val toolbar by lazy {   find<Toolbar>(R.id.toolbar) }
    val drawer by lazy {  find<DrawerLayout>(R.id.drawer_layout)}
    val navigationView by lazy {  find<NavigationView>(R.id.nav_view)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)





        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()


        navigationView.setNavigationItemSelectedListener(this)
        if(savedInstanceState ==null){
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, ScheduleFragment()).commit()
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_agenda) {
            // Handle the camera action
            supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ScheduleFragment()).commit()
        } else if (id == R.id.nav_speaker) {
            //supportFragmentManager.beginTransaction()
              //      .replace(R.id.fragment_container, SpeakerFragment()).commit()
        } else if (id == R.id.nav_partner) {
            //supportFragmentManager.beginTransaction()
                //    .replace(R.id.fragment_container, PartnersFragment()).commit()

        } else if (id == R.id.nav_share) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
