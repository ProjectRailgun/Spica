package co.bangumi.Cassiopeia

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.BottomSheetDialog
import android.support.v4.app.FragmentManager
import android.support.v4.content.FileProvider
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import co.bangumi.common.FileUtil
import co.bangumi.common.PackageUtil
import co.bangumi.common.StringUtil
import co.bangumi.common.api.*
import co.bangumi.common.cache.JsonUtil
import co.bangumi.common.model.Bangumi
import co.bangumi.common.model.EpisodeDetail
import com.bumptech.glide.Glide
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_detail.*
import java.io.File
import java.util.*

class DetailActivity: co.bangumi.common.activity.BaseActivity(), OnMenuItemClickListener {
    // TODO 重构
    val iv by lazy { findViewById(R.id.image) as ImageView? }
    val subtitle by lazy { findViewById(R.id.subtitle) as TextView }
    val info by lazy { findViewById(R.id.info) as TextView }
    val summary by lazy { findViewById(R.id.summary) as TextView }
    val summary2 by lazy { findViewById(R.id.summary2) as TextView }
    val more by lazy { findViewById(R.id.button_more) as TextView }
    val recyclerView by lazy { findViewById(R.id.recycler_view) as RecyclerView }
    val summaryLayout by lazy { (findViewById(R.id.summary_layout) as LinearLayout) }
    val btnBgmTv by lazy { (findViewById(R.id.button_bgm_tv) as Button) }
    val imgShare by lazy { (findViewById(R.id.img_share) as ImageView) }

    val episodeAdapter by lazy { EpisodeAdapter() }

    private lateinit var cassiopeiaApplication: CassiopeiaApplication
    private lateinit var mTracker: Tracker
    private lateinit var mMenuDialogFragment: ContextMenuDialogFragment
    private lateinit var bgm: Bangumi
    private lateinit var fragmentManager: FragmentManager

    companion object {
        fun intent(context: Context?, bgm: Bangumi): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            val json = JsonUtil.toJson(bgm)
            intent.putExtra(INTENT_KEY_BANGMUMI, json)
            return intent
        }

        private val INTENT_KEY_BANGMUMI = "INTENT_KEY_BANGMUMI"
        private val REQUEST_CODE = 0x81
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""

        recyclerView.layoutManager = co.bangumi.common.view.ScrollStartLayoutManager(this, co.bangumi.common.view.ScrollStartLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = episodeAdapter
        fragmentManager = supportFragmentManager

        val json = intent.getStringExtra(INTENT_KEY_BANGMUMI)
        checkNotNull(json)
        bgm = JsonUtil.fromJson(json, Bangumi::class.java)!!
        checkNotNull(bgm)
        initMenuFragment()
        setData(bgm)
        loadData(bgm.id)

        val name = StringUtil.getName(bgm)
        title = name

        // TODO compare Firebase Analytics and Google Analytics
        cassiopeiaApplication = application as CassiopeiaApplication
        mTracker = cassiopeiaApplication.defaultTracker

//        val index = Indexables.digitalDocumentBuilder()
//            .setName(name)
//            .setText(bgm.summary)
//            .setUrl(Constant.DETAIL_URL_PREFIX + bgm.id)
//            .setImage(bgm.image)
//            .build()
//        FirebaseAppIndex.getInstance().update(index)
//
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, bgm.id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        FirebaseAnalytics.getInstance(this).logEvent("view_detail", bundle)

        mTracker.send(HitBuilders.EventBuilder()
            .setAction("View Detail")
            .setLabel(bgm.name_cn)
            .build())
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams()
        menuParams.actionBarSize = resources.getDimension(R.dimen.context_menu_height).toInt()
        val collectionStatusArray = resources.getStringArray(R.array.array_favorite)
        val contextMenuList = ArrayList<MenuObject>()
        collectionStatusArray.forEachIndexed { index,value ->
            val menuObject = MenuObject(value)
            menuObject.resource = when(index) {
                0 -> R.drawable.ic_watchlist
                1 -> R.drawable.ic_wish
                2 -> R.drawable.ic_watched
                3 -> R.drawable.ic_watching
                4 -> R.drawable.ic_paused
                5 -> R.drawable.ic_abandoned
                else -> R.drawable.ic_wish
            }
            contextMenuList.add(menuObject)
        }
        menuParams.menuObjects = contextMenuList
        menuParams.isClosableOutside = true
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams)
        mMenuDialogFragment.setItemClickListener(this)
    }

    override fun onMenuItemClick(v: View?, position: Int) {
        if (collectionStatusText.text == resources.getStringArray(R.array.array_favorite).get(position)) return

        collectionStatusText.text = resources.getStringArray(R.array.array_favorite).get(position)
        ApiClient.getInstance().uploadFavoriteStatus(bgm.id, FavoriteChangeRequest(position))
                .withLifecycle()
                .subscribe({
                    bgm.favorite_status = position
                }, {
                    toastErrors()
                    collectionStatusText.text = resources.getStringArray(R.array.array_favorite).get(bgm.favorite_status)
                })
    }

    private fun loadData(bgmId: String) {
        ApiClient.getInstance().getBangumiDetail(bgmId)
                .withLifecycle()
                .subscribe({
                    setData(it.getData())
                }, {
                    toastErrors().accept(it)
                    finish()
                })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun playVideo(episode: co.bangumi.common.model.Episode) {
        assert(!TextUtils.isEmpty(episode.id))

        ApiClient.getInstance().getEpisodeDetail(episode.id)
                .withLifecycle()
                .subscribe({
                    val url = it.video_files[0].url
                    val path = this.getExternalFilesDir(
                        Environment.DIRECTORY_DOWNLOADS
                                + '/' + StringUtil.getName(it.bangumi))
                    val file = File(path, url.substring(url.lastIndexOf('/')))
                    val builder =  HitBuilders.EventBuilder()
                        .setAction("Play Video")
                        .setLabel("${it.bangumi.name_cn} - ${it.episode_no}")
                    if (FileUtil.isFileExist(file)) {
                        startActivityForResult(
                            PlayerActivity.intent(
                                this,
                                file.path,
                                episode.id,
                                episode.bangumi_id
                            ),
                            DetailActivity.REQUEST_CODE
                        )
                        mTracker.send(builder
                            .setCategory("local")
                            .build())
                        return@subscribe
                    }
                        startActivityForResult(
                            PlayerActivity.intent(
                                this,
                                url,
                                episode.id,
                                episode.bangumi_id
                            ),
                            DetailActivity.REQUEST_CODE
                        )
                    mTracker.send(builder
                        .setCategory("online")
                        .build())
                }, {
                    toastErrors()
                })
    }

    private fun markWatched(episodeDetail: EpisodeDetail) {
        ApiClient.getInstance().uploadWatchHistory(
                HistoryChangeRequest(Collections.singletonList(HistoryChangeItem(episodeDetail.bangumi_id,
                        episodeDetail.id,
                        System.currentTimeMillis(),
                        0,
                        1f,
                        true))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        Consumer {
                            loadData(episodeDetail.bangumi_id)
                        },
                        ignoreErrors())
    }

    private fun openWith(file: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= 24) {
            val contentUri = FileProvider.getUriForFile(this, "co.bangumi.fileprovider", file)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(contentUri, "video/*")
        } else {
            intent.setDataAndType(Uri.fromFile(file), "video/*")
        }
        startActivity(intent)
    }

    private fun downloadBgm(episodeDetail: EpisodeDetail) {
        // TODO 待重构
        val bgmName = StringUtil.getName(episodeDetail.bangumi)
        val url = episodeDetail.video_files[0].url
        val dir = Environment.DIRECTORY_DOWNLOADS + '/' + StringUtil.getName(episodeDetail.bangumi)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
            .setDestinationInExternalFilesDir(this, dir, url.substring(url.lastIndexOf('/') + 1))
            .setTitle(bgmName + " - " + episodeDetail.episode_no)
            .setDescription(StringUtil.getName(episodeDetail))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setMimeType("video/*")
        request.allowScanningByMediaScanner()
        downloadManager.enqueue(request)
    }

    @SuppressLint("SetTextI18n")
    private fun openMenu(episodeDetail: EpisodeDetail) {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_episode_menu, null, false)

        val videoUrl = episodeDetail.video_files[0].url
        val path = this.getExternalFilesDir(
            Environment.DIRECTORY_DOWNLOADS
                    + '/' + StringUtil.getName(episodeDetail.bangumi))
        val file = File(path, videoUrl.substring(videoUrl.lastIndexOf('/')))

        val episodeName = StringUtil.getName(episodeDetail)

        val btnMarkWatched = view.findViewById(R.id.button_mark_watched) as TextView
        val btnDownload = view.findViewById(R.id.button_download) as TextView
        val btnOpenExternal = view.findViewById(R.id.button_open_external) as TextView

        btnMarkWatched.setOnClickListener {
            markWatched(episodeDetail)
            dialog.dismiss()
        }

        (view.findViewById(R.id.title) as TextView).text = "${episodeDetail.episode_no}. $episodeName"

        btnDownload.setOnClickListener {
            downloadBgm(episodeDetail)
            dialog.dismiss()

            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, episodeDetail.id)
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, StringUtil.getName(episodeDetail.bangumi))
            bundle.putString(
                FirebaseAnalytics.Param.ITEM_LOCATION_ID,
                episodeDetail.episode_no.toString()
            )
            FirebaseAnalytics.getInstance(parent).logEvent("download_start", bundle)

            mTracker.send(HitBuilders.EventBuilder()
                .setAction("Download Video")
                .setLabel("${episodeDetail.bangumi.name_cn} - ${episodeDetail.episode_no}")
                .build())
        }

        if (FileUtil.isFileExist(file)) {
            btnOpenExternal.visibility = View.VISIBLE
            btnOpenExternal.setOnClickListener {
                openWith(file)
                dialog.dismiss()
            }
            btnDownload.text = getString(R.string.delete)
            btnDownload.setOnClickListener {
                showToast(getString(if (file.delete()) R.string.delete_success else R.string.delete_failed))
                dialog.dismiss()
            }
        }

        dialog.setContentView(view)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DetailActivity.REQUEST_CODE
                && resultCode == Activity.RESULT_OK
                && data != null) {
            val id = data.getStringExtra(PlayerActivity.RESULT_KEY_ID)
            val bgmId = data.getStringExtra(PlayerActivity.RESULT_KEY_ID_2)
            val duration = data.getLongExtra(PlayerActivity.RESULT_KEY_DURATION, 0)
            val position = data.getLongExtra(PlayerActivity.RESULT_KEY_POSITION, 0)
            ApiClient.getInstance().uploadWatchHistory(
                    HistoryChangeRequest(Collections.singletonList(HistoryChangeItem(bgmId,
                            id,
                            System.currentTimeMillis(),
                            position / 100,
                            position.toFloat() / duration,
                            duration == position))))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            Consumer {
                                loadData(bgmId)
                            },
                            ignoreErrors())
        }
    }

    private fun setData(detail: Bangumi) {
        recyclerView.isNestedScrollingEnabled = false

        iv?.let {
            val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.parseColor(detail.coverColor))
            Glide.with(this)
                .load(detail.image)
                .thumbnail(0.1f)
                .placeholder(BitmapDrawable(resources, bitmap))
                .crossFade()
                .into(iv)
        }

        subtitle.text = co.bangumi.common.StringUtil.subTitle(detail)
        info.text = resources.getString(R.string.update_info)
                ?.format(detail.eps, co.bangumi.common.StringUtil.dayOfWeek(detail.air_weekday), detail.air_date)

        btnBgmTv.visibility = if (detail.bgm_id > 0) View.VISIBLE else View.GONE

        btnBgmTv.setOnClickListener {
            if (detail.bgm_id <= 0) {
                return@setOnClickListener
            }

            val url = "https://bgm.tv/subject/" + detail.bgm_id
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        imgShare.setOnClickListener {
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this) != ConnectionResult.SUCCESS) {
                val textIntent = Intent(Intent.ACTION_SEND)
                textIntent.type = "text/plain"
                textIntent.putExtra(Intent.EXTRA_TEXT, Constant.DETAIL_URL_PREFIX + detail.id)
                startActivity(Intent.createChooser(textIntent, StringUtil.getName(detail)))
                return@setOnClickListener
            }
            FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(Constant.DETAIL_URL_PREFIX + detail.id))
                .setDomainUriPrefix(Constant.DYNAMIC_LINK_PREFIX)
                .setAndroidParameters(
                    DynamicLink.AndroidParameters.Builder(packageName)
                        .setMinimumVersion(PackageUtil.getVersionCode(this))
                        .build()
                )
                .setSocialMetaTagParameters(
                    DynamicLink.SocialMetaTagParameters.Builder()
                        .setTitle(StringUtil.getName(detail))
                        .setDescription(detail.summary)
                        .setImageUrl(Uri.parse(detail.image))
                        .build()
                )
                .buildShortDynamicLink(ShortDynamicLink.Suffix.SHORT)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val shortLink = it.result!!.shortLink
                        val textIntent = Intent(Intent.ACTION_SEND)
                        textIntent.type = "text/plain"
                        textIntent.putExtra(Intent.EXTRA_TEXT, shortLink.toString())
                        startActivity(Intent.createChooser(textIntent, StringUtil.getName(detail)))
                    } else {
                        showToast(getString(R.string.network_error), Toast.LENGTH_SHORT)
                    }
                }
        }

        if (TextUtils.isEmpty(detail.summary)) {
            summary.visibility = View.GONE
            summary2.visibility = View.GONE
            more.visibility = View.GONE
        } else {
            fun less() {
                summary.text = detail.summary.replace("\n", "\t")
                summary.maxLines = 1
                summary.post {
                    summary2.text = summary.text.toString().substring(summary.layout.getLineEnd(0))
                }
                more.setText(R.string.more)
                more.tag = 1
            }

            fun more() {
                summary.text = detail.summary
                summary2.text = ""
                summary.maxLines = 20
                more.setText(R.string.less)
                more.tag = 0
            }

            summaryLayout.setOnClickListener {
                if (more.tag == 0) {
                    less()
                } else {
                    more()
                }
            }

            if (more.tag == null) {
                less()
            }
        }
        val adapter = ArrayAdapter.createFromResource(this,
            R.array.array_favorite, R.layout.spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        collectionStatus.setOnClickListener {
            if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG)
            }
        }
        val collectionStatusArray = resources.getStringArray(R.array.array_favorite)
        collectionStatusText.text = collectionStatusArray.get(detail.favorite_status)

        if (detail is co.bangumi.common.model.BangumiDetail && detail.episodes != null && detail.episodes.isNotEmpty()) {
            episodeAdapter.setEpisodes(detail.episodes)
            detail.episodes
                    .map { it?.watch_progress?.last_watch_time }
                    .withIndex()
                    .filter { it.value != null }
                    .sortedBy { it.value }
                    .lastOrNull()
                    ?.let {
                        recyclerView.post {
                            recyclerView.smoothScrollToPosition(it.index)
                        }
                    }
        }

    }

    inner class EpisodeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var episodes: List<co.bangumi.common.model.Episode> = java.util.ArrayList()

        fun setEpisodes(ep: List<co.bangumi.common.model.Episode>) {
            episodes = ep
            notifyDataSetChanged()
        }

        fun getEpisodes(): List<co.bangumi.common.model.Episode> {
            return episodes
        }

        inner class VH(v: View) : RecyclerView.ViewHolder(v) {
            // TODO 重构
            val view = v
            val tv = v.findViewById(R.id.tv) as TextView
            val image = v.findViewById(R.id.image) as ImageView
            val progress = v.findViewById(R.id.progress) as co.bangumi.common.view.ProgressCoverView
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
            if (holder is VH) {
                val d = episodes[p1]

                val name = StringUtil.getName(d)

                holder.tv.text = "${d.episode_no}. $name"
                if (d.watch_progress?.percentage != null
                        && d.watch_progress?.percentage!! < 0.15f) {
                    d.watch_progress?.percentage = 0.15f
                }
                holder.progress.setProgress(d.watch_progress?.percentage ?: 0f)

                val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
                bitmap.eraseColor(Color.parseColor(if(d.thumbnailColor != null) d.thumbnailColor else "#00000000"))
                Glide.with(this@DetailActivity)
                    .load(co.bangumi.common.api.ApiHelper.fixHttpUrl(d.thumbnail))
                    .thumbnail(0.1f)
                    .placeholder(BitmapDrawable(resources, bitmap))
                    .crossFade()
                    .into(holder.image)

                holder.tv.alpha = if (d.status != 0) 1f else 0.2f
                holder.view.setOnClickListener {
                    if (d.status != 0) playVideo(d)
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, d.id)
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
                    bundle.putString(
                        FirebaseAnalytics.Param.ITEM_LOCATION_ID,
                        d.episode_no.toString()
                    )
                    FirebaseAnalytics.getInstance(parent).logEvent("view_vod", bundle)
                }

                holder.view.setOnLongClickListener {
                    if (d.status != 0)
                        ApiClient.getInstance().getEpisodeDetail(d.id)
                            .withLifecycle()
                            .subscribe({
                                openMenu(it)
                            }, {
                                toastErrors()
                            })
                    true
                }

            }
        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            return VH(
                LayoutInflater.from(p0.context).inflate(
                    R.layout.rv_item_episode,
                    p0,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return episodes.size
        }

    }
}
