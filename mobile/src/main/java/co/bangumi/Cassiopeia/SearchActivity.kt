package co.bangumi.Cassiopeia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import co.bangumi.common.api.ApiClient
import co.bangumi.common.model.Bangumi
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.functions.Consumer


class SearchActivity : co.bangumi.common.activity.BaseActivity() {

    companion object {
        fun intent(context: Context): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            return intent
        }

        public val TASK_ID_LOAD = 0x01
    }

    private val recyclerView by lazy { findViewById(R.id.recycler_view) as RecyclerView }
    private val edit by lazy { findViewById(R.id.edit) as EditText }
    private val bangumiList = arrayListOf<Bangumi>()
    private val adapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        (findViewById(R.id.back) as AppCompatImageButton).setOnClickListener { super.onBackPressed() }

        val mLayoutManager = LinearLayoutManager(this)
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
        ApiClient.getInstance().getSearchBangumi(null, null, "air_date", "desc", null)
                .withLifecycle()
                .onlyRunOneInstance(SearchActivity.TASK_ID_LOAD, true)
                .subscribe(Consumer {
                    display(it.getData())
                }, toastErrors())
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, s)
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SEARCH, bundle)
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
        inputManager.hideSoftInputFromWindow(this.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        edit.clearFocus()
    }

    private class WideCardHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById(R.id.imageView) as ImageView
        val title = view.findViewById(R.id.title) as TextView
        val subtitle = view.findViewById(R.id.subtitle) as TextView
        val info = view.findViewById(R.id.info) as TextView
        val state = view.findViewById(R.id.state) as TextView
        val info2 = view.findViewById(R.id.info2) as TextView
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
                    ?.format(bangumi.eps, bangumi.air_weekday.let { co.bangumi.common.StringUtil.dayOfWeek(it) }, bangumi.air_date)

            if (bangumi.favorite_status > 0) {
                val array = resources.getStringArray(R.array.array_favorite)
                if (array.size > bangumi.favorite_status) {
                    viewHolder.state.text = array[bangumi.favorite_status]
                }
            } else {
                viewHolder.state.text = ""
            }

            viewHolder.info2.text = bangumi.summary.replace("\n", "")

            Glide.with(this@SearchActivity)
                    .load(bangumi.image)
                    .into(viewHolder.image)

            viewHolder.itemView.setOnClickListener {
                this@SearchActivity.startActivity(bangumi.let { it1 -> DetailActivity.intent(this@SearchActivity, it1) })
            }
        }

        override fun getItemCount(): Int = bangumiList.size
    }
}
