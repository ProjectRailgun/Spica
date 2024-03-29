package co.railgun.spica

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import co.railgun.spica.api.SpicaClient
import co.railgun.spica.api.function.user.info
import co.railgun.common.model.Announce
import co.railgun.spica.databinding.ActivityHomeBinding
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader
import kotlinx.coroutines.launch
import retrofit2.HttpException


class HomeActivity : BaseThemeActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private val root by lazy { binding.root }
    private val banner by lazy { root.findViewById<Banner>(R.id.banner) }
    private val fabSearch by lazy { root.findViewById<FloatingActionButton>(R.id.fabSearch) }

    override fun themeWhite(): Int {
        return R.style.AppThemeWhite_NoStateBar
    }

    override fun themeStand(): Int {
        return R.style.AppTheme_NoStateBar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isWhiteTheme) {
            val v = findViewById<View>(android.R.id.content)
            v.systemUiVisibility = v.systemUiVisibility.or(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.bringToFront()
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        (findViewById<FloatingActionButton>(R.id.fabSearch)).setOnClickListener { search() }

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val navHeaderT1 = navigationView.getHeaderView(0).findViewById(R.id.textView1) as TextView
        val navHeaderT2 = navigationView.getHeaderView(0).findViewById(R.id.textView2) as TextView
        navigationView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                runCatching { SpicaClient.User.info() }
                    .onFailure {
                        toastErrors().accept(it)
                        if (it is HttpException && it.code() == 401) logout()
                    }
                    .onSuccess {
                        navHeaderT1.text = it.name
                        navHeaderT2.text = it.email
                    }
            }
        }
    }

    public fun setBanner(list: List<Announce>) {
        banner.visibility = View.VISIBLE
        banner.setImageLoader(GlideImageLoader())
            .setImages(list.map { it.imageUrl })
            .setOnBannerListener {
                val uri = Uri.parse(list[it].content)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }.start()
    }

    public fun addListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    if (fabSearch.visibility == View.VISIBLE) fabSearch.hide()
                } else {
                    if (fabSearch.visibility == View.GONE) fabSearch.show()
                }
            }
        })
    }

    private fun search() {
        startActivity(SearchActivity.intent(this))
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_favorite -> {
                startActivity(MyCollectionActivity.intent(this))
            }
            R.id.nav_bangumi -> {
                startActivity(AllBangumiActivity.intent(this))
            }
            R.id.nav_setting -> {
                themeChanged()
            }
            R.id.nav_logout -> {
                logout()
            }
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return false
    }

    private fun logout() {
        SpicaApplication.logout(this)
        finishAffinity()
    }

    private inner class GlideImageLoader : ImageLoader() {

        override fun displayImage(
            context: Context?,
            path: Any?,
            imageView: ImageView?,
        ) {
            Glide.with(context)
                .load(path)
                // TODO 占位图会带来加载错位问题
                //.placeholder(R.drawable.banner_placeholder)
                .error(R.drawable.banner_placeholder)
                .thumbnail(0.1f)
                .crossFade()
                .into(imageView)
        }
    }

}
