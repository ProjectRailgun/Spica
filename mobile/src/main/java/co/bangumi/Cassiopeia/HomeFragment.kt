package co.bangumi.Cassiopeia


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.bangumi.Cassiopeia.homefragment.HomeData
import co.bangumi.Cassiopeia.homefragment.HomeHorizontalAdapter
import co.bangumi.Cassiopeia.homefragment.HomeLargeAdapter
import co.bangumi.common.api.ListResponse
import co.bangumi.common.model.Announce
import co.bangumi.common.model.Bangumi
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.functions.Function3
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : co.bangumi.common.activity.BaseFragment() {

    private val recyclerView by lazy { findViewById(R.id.recycler_view) as RecyclerView }
    private val swipeRefresh by lazy { findViewById(R.id.swipe_refresh) as SwipeRefreshLayout }
    private val homeDataAdapter by lazy { HomeDataAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh.setColorSchemeResources(R.color.meguminRed)
        (activity as HomeActivity).addListener(recyclerView)
        homeDataAdapter.attachTo(recyclerView)

        swipeRefresh.setOnRefreshListener {
            loadData()
        }

        loadData()
    }

    private fun loadData() {
        swipeRefresh.isRefreshing = true
        Observable.zip(
                withLifecycle(co.bangumi.common.api.ApiClient.getInstance().getAnnounceBangumi()),
                withLifecycle(co.bangumi.common.api.ApiClient.getInstance().getMyBangumi(1, -1, Bangumi.Status.WATCHING.value)),
                withLifecycle(co.bangumi.common.api.ApiClient.getInstance().getOnAir(Bangumi.Type.ALL.value)),
            Function3 { t1: ListResponse<Announce>, t2: ListResponse<Bangumi>, t3: ListResponse<Bangumi> ->
                    arrayOf(t1.getData(), t2.getData(), t3.getData())
            })
                .subscribe({
                    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    homeDataAdapter.list.clear()

                    // TODO 待重构
                    val t1 = it[0] as List<Announce>
                    val t2 = it[1] as List<Bangumi>
                    val t3 = it[2] as List<Bangumi>

                    if (t1.isNotEmpty()) {
                        val announceMap = t1.toHashSet()
                            .filter { return@filter true }
                            .sortedBy { it.sortOrder }
                            .groupBy { it.position }
                        homeDataAdapter.list.add(HomeData(getString(R.string.recommended)))
                        homeDataAdapter.list.add(HomeData(HomeData.TYPE.LARGE, null, null,
                            announceMap[Announce.Type.RECOMMENDATION.value]?.map { it.bangumi }))

                        announceMap[Announce.Type.NOTICE.value]?.let {
                            (activity as HomeActivity).setBanner(it)
                        }
                    }

                    val todayUpdate = t2.toHashSet()
                        .filter {
                            return@filter it.favorite_status == Bangumi.Status.WATCHING.value
                        }
                        .sortedBy { -it.unwatched_count }

                    if (todayUpdate.isNotEmpty()) {
                        homeDataAdapter.list.add(HomeData(resources.getStringArray(R.array.array_favorite)[Bangumi.Status.WATCHING.value]))
                        homeDataAdapter.list.add(HomeData(todayUpdate))
                        homeDataAdapter.list.add(HomeData(HomeData.TYPE.MY_COLLECTION))
                    }

                    if (t3.isNotEmpty()) {
                        homeDataAdapter.list.add(HomeData(getString(R.string.releasing)))
                        homeDataAdapter.list.addAll(t3.map { HomeData(it) })
                    }

                    // TODO
                    homeDataAdapter.list.add(HomeData())
                    homeDataAdapter.list.add(HomeData(HomeData.TYPE.MY_COLLECTION))
                    homeDataAdapter.notifyDataSetChanged()
                    swipeRefresh.isRefreshing = false
                }, {
                    swipeRefresh.isRefreshing = false
                    toastErrors().accept(it)
                })
    }

    private class HomeDataAdapter(val parent: Fragment) {
        val spanCount = parent.resources.getInteger(R.integer.home_screen_span_count)
        val list = arrayListOf<HomeData>()
        val adapter = HomeAdapter()
        val lm = LinearLayoutManager(parent.context)

        fun attachTo(recyclerView: RecyclerView) {
            recyclerView.layoutManager = lm
            recyclerView.adapter = adapter
        }

        fun notifyDataSetChanged() {
            adapter.notifyDataSetChanged()
        }

        class HomeLineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var recyclerView: RecyclerView =
                itemView.findViewById(R.id.recyclerView) as RecyclerView
        }

        class HomeLargeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var recyclerView: RecyclerView =
                itemView.findViewById(R.id.recyclerView) as RecyclerView
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

        private class TitleHolder(view: View) : RecyclerView.ViewHolder(view) {
            val text = view.findViewById(R.id.textView) as TextView
        }

        private class AllBangumiHolder(view: View) : RecyclerView.ViewHolder(view) {
            init {
                view.setOnClickListener { view.context.startActivity(AllBangumiActivity.intent(view.context)) }
            }
        }
        private class MyCollectionHolder(view: View) : RecyclerView.ViewHolder(view) {
            init {
                (view as TextView).text = view.context.getString(R.string.my_collection)
                view.setOnClickListener { view.context.startActivity(MyCollectionActivity.intent(view.context)) }
            }
        }

        private class PaddingItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
            val dp = context.resources.getDimensionPixelSize(R.dimen.dp_8)

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val position = parent.getChildAdapterPosition(view)
                val childCount = state.itemCount
                if (position == 0) {
                    outRect.left = outRect.left.plus(dp)
                } else if (position + 1 == childCount) {
                    outRect.right = outRect.right.plus(dp)
                }
            }
        }

        private inner class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

                val bangumi = list[position].bangumi
                when (viewHolder) {
                    is HomeLineHolder -> {
                        if (viewHolder.recyclerView.layoutManager == null) {
                            viewHolder.recyclerView.layoutManager = LinearLayoutManager(viewHolder
                                    .recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                            viewHolder.recyclerView.addItemDecoration(PaddingItemDecoration(viewHolder.recyclerView.context))
                        }

                        viewHolder.recyclerView.adapter =
                                HomeHorizontalAdapter(
                                        list[position]) { parent.startActivity(DetailActivity.intent(parent.context, it)) }
                    }
                    is HomeLargeHolder -> {
                        if (viewHolder.recyclerView.layoutManager == null) {
                            viewHolder.recyclerView.layoutManager = LinearLayoutManager(viewHolder
                                    .recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                            viewHolder.recyclerView.addItemDecoration(PaddingItemDecoration(viewHolder.recyclerView.context))
                        }

                        viewHolder.recyclerView.adapter =
                                HomeLargeAdapter(
                                        list[position]) { parent.startActivity(DetailActivity.intent(parent.context, it)) }
                    }
                    is WideCardHolder -> {
                        if (bangumi == null) {
                            return
                        }

                        viewHolder.title.text = co.bangumi.common.StringUtil.getName(bangumi)
                        viewHolder.subtitle.text = co.bangumi.common.StringUtil.subTitle(bangumi)
                        viewHolder.info.text =
                                viewHolder.info.resources.getString(R.string.update_info)
                                .format(bangumi.eps, bangumi.air_weekday.let { co.bangumi.common.StringUtil.dayOfWeek(it) },
                                        if (bangumi.isOnAir()) viewHolder.info.resources.getString(R.string.on_air) else viewHolder.info.resources.getString(R.string.finished))

                        if (bangumi.favorite_status > 0) {
                            val array =
                                viewHolder.state.resources.getStringArray(R.array.array_favorite)
                            if (array.size > bangumi.favorite_status) {
                                viewHolder.state.text = array[bangumi.favorite_status]
                            }
                        } else {
                            viewHolder.state.text = ""
                        }

                        if (bangumi.type == Bangumi.Type.RAW.value) {
                            viewHolder.typeRaw.visibility = View.VISIBLE
                            viewHolder.typeSub.visibility = View.INVISIBLE
                        } else{
                            viewHolder.typeSub.visibility = View.VISIBLE
                            viewHolder.typeRaw.visibility = View.INVISIBLE
                        }

                        viewHolder.info2.text = bangumi.summary.replace("\n", "")

                        val bitmap = Bitmap.createBitmap(2, 3, Bitmap.Config.ARGB_8888)
                        bitmap.eraseColor(Color.parseColor(bangumi.coverColor))

                        Glide.with(parent)
                            .load(bangumi.cover)
                            .thumbnail(0.1f)
                            .placeholder(BitmapDrawable(parent.resources, bitmap))
                            .crossFade()
                            .into(viewHolder.image)

                        viewHolder.itemView.setOnClickListener {
                            parent.startActivity(bangumi.let { it1 -> DetailActivity.intent(parent.context, it1) })
                        }
                    }
                    is TitleHolder -> {
                        viewHolder.text.text = list[position].string
                    }
                }
            }

            override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
                return when (p1) {
                    HomeData.TYPE.TITLE.value -> TitleHolder(
                        LayoutInflater.from(p0.context).inflate(
                            R.layout.include_home_title,
                            p0,
                            false
                        )
                    )
                    HomeData.TYPE.WIDE.value -> WideCardHolder(
                        LayoutInflater.from(p0.context).inflate(
                            R.layout.include_bangumi_wide,
                            p0,
                            false
                        )
                    )
                    HomeData.TYPE.LARGE.value -> HomeLargeHolder(
                        LayoutInflater.from(p0.context).inflate(
                            R.layout.include_home_line_container,
                            p0,
                            false
                        )
                    )
                    HomeData.TYPE.CONTAINER.value -> HomeLineHolder(
                        LayoutInflater.from(p0.context).inflate(
                            R.layout.include_home_line_container,
                            p0,
                            false
                        )
                    )
                    HomeData.TYPE.ALL_BANGUMI.value -> AllBangumiHolder(
                        LayoutInflater.from(p0.context).inflate(
                            R.layout.include_home_tail,
                            p0,
                            false
                        )
                    )
                    HomeData.TYPE.MY_COLLECTION.value -> MyCollectionHolder(
                        LayoutInflater.from(p0.context).inflate(
                            R.layout.include_home_tail,
                            p0,
                            false
                        )
                    )
                    else -> throw RuntimeException("unknown type")
                }
            }

            override fun getItemCount(): Int {
                return list.size
            }

            override fun getItemViewType(position: Int): Int {
                return list[position].type.value
            }
        }

    }
}
