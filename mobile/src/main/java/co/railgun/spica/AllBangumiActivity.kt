package co.railgun.spica

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import co.railgun.common.BuildConfig
import co.railgun.common.DisplayUtil
import co.railgun.common.model.Bangumi
import com.bumptech.glide.Glide
import io.reactivex.Observable
import java.util.*

@SuppressLint("Registered")
class AllBangumiActivity : BaseThemeActivity() {
    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, AllBangumiActivity::class.java)
        }
    }

    private val TASK_ID_LOAD by lazy { UUID.randomUUID().toString() }

    private var loaded = false
    private var pageNow = 1
    private var type: Int = Bangumi.Type.ALL.value
    private var isAll = true

    private val spinner by lazy { findViewById<AppCompatSpinner>(R.id.spinner) }
    private val bangumiList = arrayListOf<Bangumi>()
    private val adapter = HomeAdapter()

    private val loadingHud by lazy { DisplayUtil.createHud(this, resources.getString(R.string.loading)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_bangumi)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.title_bangumi)

        val sp = ArrayAdapter.createFromResource(this,
            R.array.array_bangumi_type, R.layout.spinner_item
        )
        sp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = sp
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type = when (position) {
                    1 -> Bangumi.Type.SUB.value
                    2 -> Bangumi.Type.RAW.value
                    else -> Bangumi.Type.ALL.value
                }
                reloadData()
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val mLayoutManager =
            LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(PaddingItemDecoration())

        val mScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = mLayoutManager.childCount
                val totalItemCount = mLayoutManager.itemCount
                val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    if (!isAll) {
                        loadData()
                    }
                }
            }
        }
        recyclerView.addOnScrollListener(mScrollListener)
    }

    fun reloadData() {
        loaded = false
        pageNow = 1
        bangumiList.clear()
        adapter.notifyDataSetChanged()
        loadData()
    }

    fun loadData() {
        onLoadData()?.let {
            it.withLifecycle()
                .subscribe({ addToList(it) }, {
                    loadingHud.dismiss()
                    toastErrors(it)
                }, { loadingHud.dismiss() })
        }
    }

    fun onLoadData(): Observable<List<Bangumi>>? {
        return if (!loaded) {
            loadingHud.show()
            loaded = true
            if (BuildConfig.DEBUG) Log.i("AllBangumiActivity", "onLoadData:$pageNow")
            co.railgun.common.api.ApiClient.getInstance().getSearchBangumi(pageNow, 15, "air_date", "desc", null, type)
                .withLifecycle()
                .onlyRunOneInstance(TASK_ID_LOAD, false)
                .flatMap {
                    pageNow += 1
                    loaded = it.getData().isEmpty()
                    isAll = it.count == bangumiList.size + it.getData().size
                    Observable.just(it.getData())
                }
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
            LayoutInflater.from(this@AllBangumiActivity).inflate(
                R.layout.include_bangumi_wide,
                p0,
                false
            )
        )

        override fun onBindViewHolder(viewHolder: WideCardHolder, p1: Int) {
            val bangumi = bangumiList[p1]
            viewHolder.title.text = co.railgun.common.StringUtil.getName(bangumi)
            viewHolder.subtitle.text = co.railgun.common.StringUtil.subTitle(bangumi)
            viewHolder.info.text = viewHolder.info.resources.getString(R.string.update_info)
                .format(bangumi.eps, bangumi.air_weekday.let { co.railgun.common.StringUtil.dayOfWeek(it) },
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
                viewHolder.typeSub.visibility = View.INVISIBLE
            } else {
                viewHolder.typeSub.visibility = View.VISIBLE
                viewHolder.typeRaw.visibility = View.INVISIBLE
            }

            viewHolder.info2.text = bangumi.summary.replace("\n", "")

            val bitmap = Bitmap.createBitmap(2, 3, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.parseColor(bangumi.coverColor))
            Glide.with(this@AllBangumiActivity)
                .load(bangumi.cover)
                .thumbnail(0.1f)
                .placeholder(BitmapDrawable(resources, bitmap))
                .crossFade()
                .into(viewHolder.image)

            viewHolder.itemView.setOnClickListener {
                this@AllBangumiActivity.startActivity(bangumi.let { it1 -> DetailActivity.intent(this@AllBangumiActivity, it1) })
            }
        }

        override fun getItemCount(): Int = bangumiList.size
    }
}
