package co.bangumi.Cygnus

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import retrofit2.HttpException

class HomeActivity : co.bangumi.common.activity.BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(co.bangumi.Cygnus.R.layout.activity_home)
        val toolbar = findViewById(co.bangumi.Cygnus.R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById(co.bangumi.Cygnus.R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, co.bangumi.Cygnus.R.string.navigation_drawer_open, co.bangumi.Cygnus.R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        findViewById(co.bangumi.Cygnus.R.id.fab_search).setOnClickListener { search() }

        val navigationView = findViewById(co.bangumi.Cygnus.R.id.nav_view) as NavigationView
        val navHeaderT1 = navigationView.getHeaderView(0).findViewById(co.bangumi.Cygnus.R.id.textView1) as TextView
        val navHeaderT2 = navigationView.getHeaderView(0).findViewById(co.bangumi.Cygnus.R.id.textView2) as TextView
        navigationView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction()
                .replace(co.bangumi.Cygnus.R.id.fragment_container, HomeFragment())
                .commit()

        co.bangumi.common.api.ApiClient.getInstance().getUserInfo()
                .withLifecycle()
                .subscribe({
                    navHeaderT1.text = it.getData().name
                    navHeaderT2.text = it.getData().email
                }, {
                    toastErrors().accept(it)

                    if (it is HttpException && it.code() == 401) {
                        logout()
                    }
                })
    }

    private fun search() {
        startActivity(SearchActivity.intent(this))
    }

    override fun onBackPressed() {
        val drawer = findViewById(co.bangumi.Cygnus.R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            co.bangumi.Cygnus.R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            co.bangumi.Cygnus.R.id.nav_favorite -> {
                startActivity(FavoriteActivity.intent(this))
            }
            co.bangumi.Cygnus.R.id.nav_bangmuni -> {
                startActivity(AllBangumiActivity.intent(this))
            }
//            R.id.nav_setting -> {
//                startActivity(Intent(this, SettingsActivity::class.java))
//            }
            co.bangumi.Cygnus.R.id.nav_logout -> {
                logout()
            }
        }

        val drawer = findViewById(co.bangumi.Cygnus.R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return false
    }

    private fun logout() {
        co.bangumi.common.CygnusApplication.logout(this)
        finishAffinity()
    }

}
