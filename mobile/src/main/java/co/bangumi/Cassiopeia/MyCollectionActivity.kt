package co.bangumi.Cassiopeia

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.bangumi.common.DisplayUtil
import co.bangumi.common.model.Bangumi
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

class MyCollectionActivity : co.bangumi.common.activity.BaseActivity() {

    private val bangumiList = arrayListOf<Bangumi>()
    private val adapter = HomeAdapter()

    private val loadingHud by lazy { DisplayUtil.createHud(this, resources.getString(R.string.loading)) }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MyCollectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.title_favorite)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val mLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(PaddingItemDecoration())

        val mScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = mLayoutManager.childCount
                val totalItemCount = mLayoutManager.itemCount
                val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    loadData()
                }
            }
        }
        recyclerView.addOnScrollListener(mScrollListener)
    }

    override fun onResume() {
        super.onResume()
        bangumiList.clear()
        adapter.notifyDataSetChanged()
        loaded = false
        loadData()
    }

    fun loadData() {
        onLoadData()?.let {
            it.withLifecycle()
                    .subscribe(Consumer {
                        addToList(it)
                    }, toastErrors(), Action { loadingHud.dismiss() })
        }
    }

    var loaded = false

    fun onLoadData(): Observable<List<Bangumi>>? {
        return if (!loaded) {
            loadingHud.show()
            loaded = true
            co.bangumi.common.api.ApiClient.getInstance().getMyBangumi(3)
                    .concatWith(co.bangumi.common.api.ApiClient.getInstance().getMyBangumi(1))
                    .concatWith(co.bangumi.common.api.ApiClient.getInstance().getMyBangumi(2))
                    .concatWith(co.bangumi.common.api.ApiClient.getInstance().getMyBangumi(4))
                    .concatWith(co.bangumi.common.api.ApiClient.getInstance().getMyBangumi(5))
                    .flatMap { Observable.just(it.getData()) }
        } else null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToList(list: List<Bangumi>) {
        bangumiList.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private class WideCardHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.title)
        val subtitle: TextView = view.findViewById(R.id.subtitle)
        val info: TextView = view.findViewById(R.id.info)
        val state: TextView = view.findViewById(R.id.state)
        val info2: TextView = view.findViewById(R.id.info2)
        val typeSub: TextView = view.findViewById(R.id.type_sub)
        val typeRaw: TextView = view.findViewById(R.id.type_raw)
    }

    private class PaddingItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val childCount = state.itemCount
            if (position == 0) {
                outRect.top =
                        outRect.top.plus(view.resources.getDimensionPixelSize(R.dimen.spacing_list))
            } else if (position + 1 == childCount) {
                outRect.bottom =
                        outRect.bottom.plus(view.resources.getDimensionPixelSize(R.dimen.spacing_list_bottom))
            }
        }
    }

    private inner class HomeAdapter : RecyclerView.Adapter<WideCardHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WideCardHolder = WideCardHolder(
            LayoutInflater.from(this@MyCollectionActivity).inflate(
                R.layout.include_bangumi_wide,
                p0,
                false
            )
        )

        override fun onBindViewHolder(viewHolder: WideCardHolder, p1: Int) {
            val bangumi = bangumiList[p1]
            viewHolder.title.text = co.bangumi.common.StringUtil.getName(bangumi)
            viewHolder.subtitle.text = co.bangumi.common.StringUtil.subTitle(bangumi)
            viewHolder.info.text = viewHolder.info.resources.getString(R.string.update_info)
                    .format(bangumi.eps, bangumi.air_weekday.let { co.bangumi.common.StringUtil.dayOfWeek(it) },
                            if (bangumi.isOnAir()) viewHolder.info.resources.getString(R.string.on_air) else viewHolder.info.resources.getString(R.string.finished))

            if (bangumi.favorite_status > 0) {
                val array = resources.getStringArray(R.array.array_favorite)
                if (array.size > bangumi.favorite_status) {
                    viewHolder.state.text = array[bangumi.favorite_status]
                }
            } else {
                viewHolder.state.text = ""
            }

            if (bangumi.type == Bangumi.Type.RAW.value) {
                viewHolder.typeRaw.visibility = View.VISIBLE
                viewHolder.typeSub.visibility = View.GONE
            } else{
                viewHolder.typeSub.visibility = View.VISIBLE
                viewHolder.typeRaw.visibility = View.GONE
            }

            viewHolder.info2.text = bangumi.summary.replace("\n", "")

            val bitmap = Bitmap.createBitmap(2, 3, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.parseColor(bangumi.coverColor))
            Glide.with(this@MyCollectionActivity)
                .load(bangumi.coverImage.url)
                .thumbnail(0.1f)
                .placeholder(BitmapDrawable(resources, bitmap))
                .crossFade()
                .into(viewHolder.image)

            viewHolder.itemView.setOnClickListener {
                this@MyCollectionActivity.startActivity(bangumi.let { it1 -> DetailActivity.intent(this@MyCollectionActivity, it1) })
            }
        }

        override fun getItemCount(): Int = bangumiList.size
    }
}
