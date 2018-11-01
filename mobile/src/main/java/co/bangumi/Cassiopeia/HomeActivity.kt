package co.bangumi.Cassiopeia

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.bangumi.common.model.Announce
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.app_bar_home.*
import retrofit2.HttpException


class HomeActivity : co.bangumi.common.activity.BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.bringToFront()
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        (findViewById(R.id.fabSearch) as FloatingActionButton).setOnClickListener { search() }

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val navHeaderT1 = navigationView.getHeaderView(0).findViewById(R.id.textView1) as TextView
        val navHeaderT2 = navigationView.getHeaderView(0).findViewById(R.id.textView2) as TextView
        navigationView.setNavigationItemSelectedListener(this)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
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

    public fun setBanner(list: List<Announce>) {
        banner.visibility = View.VISIBLE
        banner.setImageLoader(GlideImageLoader())
        .setImages(list.map { it.imageUrl })
        .setOnBannerListener{
            val uri = Uri.parse(list.get(it).content)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }.start()
    }

    public fun addListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    if (fabSearch.visibility == View.VISIBLE) fabSearch.visibility = View.GONE
                } else{
                    if (fabSearch.visibility == View.GONE) fabSearch.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun search() {
        startActivity(SearchActivity.intent(this))
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
//        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_favorite -> {
                startActivity(FavoriteActivity.intent(this))
            }
            R.id.nav_bangmuni -> {
                startActivity(AllBangumiActivity.intent(this))
            }
//            R.id.nav_setting -> {
//                startActivity(Intent(this, SettingsActivity::class.java))
//            }
            R.id.nav_logout -> {
                logout()
            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return false
    }

    private fun logout() {
        co.bangumi.common.CygnusApplication.logout(this)
        finishAffinity()
    }

    private inner class GlideImageLoader : ImageLoader() {

        override fun displayImage(
            context: Context?,
            path: Any?,
            imageView: ImageView?
        ) {
            Glide.with(context)
                .load(path)
                .placeholder(R.drawable.banner_placeholder)
                .error(R.drawable.banner_placeholder)
                .thumbnail(0.1f)
                .into(imageView);
        }
    }

}
