package co.railgun.spica

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.railgun.common.DisplayUtil
import co.railgun.common.api.ApiClient
import co.railgun.common.model.Bangumi
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import java.util.*


class SearchActivity : BaseThemeActivity() {

    private val loadingHud by lazy { DisplayUtil.createHud(this, resources.getString(R.string.searching)) }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val edit by lazy { findViewById<EditText>(R.id.edit) }
    private val bangumiList = arrayListOf<Bangumi>()
    private val adapter = HomeAdapter()

    private val TASK_ID_LOAD by lazy { UUID.randomUUID().toString() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        (findViewById<AppCompatImageButton>(R.id.back)).setOnClickListener { super.onBackPressed() }

        val mLayoutManager =
            LinearLayoutManager(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter

        edit.setOnEditorActionListener { v, actionId, event ->
            if (actionId == IME_ACTION_SEARCH && edit.text.isNotEmpty()) {
                hideKeyboard()
                search(edit.text.toString())
            }
            true
        }
    }

    private fun search(s: String) {
        loadingHud.show()
        ApiClient.getInstance().getSearchBangumi(1, 300, "air_date", "desc", s, Bangumi.Type.ALL.value)
            .withLifecycle()
            .onlyRunOneInstance(TASK_ID_LOAD, true)
            .subscribe({
                display(it.getData())
            }, {
                loadingHud.dismiss()
                toastErrors(it)
            }, { loadingHud.dismiss() })
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SEARCH) {
            param(FirebaseAnalytics.Param.SEARCH_TERM, s)
        }
    }

    private fun display(data: List<Bangumi>) {
        if (data.isEmpty()) {
            showToast(getString(R.string.empty))
        } else {
            bangumiList.clear()
            bangumiList.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    private fun hideKeyboard() {
        if (currentFocus == null) return
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        edit.clearFocus()
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

    private inner class HomeAdapter : RecyclerView.Adapter<WideCardHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WideCardHolder = WideCardHolder(
            LayoutInflater.from(this@SearchActivity).inflate(
                R.layout.include_bangumi_wide,
                p0,
                false
            )
        )

        override fun onBindViewHolder(viewHolder: WideCardHolder, p1: Int) {
            val bangumi = bangumiList[p1]
            viewHolder.title.text = bangumi.name_cn
            viewHolder.subtitle.text = bangumi.name
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
                viewHolder.typeSub.visibility = View.GONE
            } else {
                viewHolder.typeSub.visibility = View.VISIBLE
                viewHolder.typeRaw.visibility = View.GONE
            }

            viewHolder.info2.text = bangumi.summary.replace("\n", "")

            val bitmap = Bitmap.createBitmap(2, 3, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.parseColor(bangumi.coverColor))

            Glide.with(this@SearchActivity)
                .load(bangumi.cover)
                .thumbnail(0.1f)
                .placeholder(BitmapDrawable(resources, bitmap))
                .crossFade()
                .into(viewHolder.image)

            viewHolder.itemView.setOnClickListener {
                this@SearchActivity.startActivity(bangumi.let { it1 -> DetailActivity.intent(this@SearchActivity, it1) })
            }
        }

        override fun getItemCount(): Int = bangumiList.size
    }
}
