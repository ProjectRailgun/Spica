package co.railgun.spica.service

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import android.util.Log
import co.railgun.spica.Constant
import co.railgun.common.BuildConfig
import co.railgun.common.StringUtil
import co.railgun.common.api.ApiClient
import co.railgun.common.model.Bangumi
import com.google.firebase.appindexing.FirebaseAppIndex
import com.google.firebase.appindexing.builders.Indexables

class AppIndexingUpdateService : JobIntentService() {

    companion object {

        fun enqueueWork(context: Context) {
            enqueueWork(
                context,
                AppIndexingUpdateService::class.java,
                Constant.UPDATE_INDEX_ALL_BANGUMI,
                Intent()
            )
        }
    }

    override fun onHandleWork(intent: Intent) {
        if (BuildConfig.DEBUG) Log.d("IndexingUpdateService", "onHandleWorkStart")
        var allBangumi: List<Bangumi> = ArrayList()

        ApiClient.getInstance()
            .getSearchBangumi(1, 300, "air_date", "desc", null, Bangumi.Type.ALL.value)
            .subscribe {
                allBangumi = it.getData()
            }

        if (allBangumi.isEmpty()) return
        val firebaseAppIndex: FirebaseAppIndex = FirebaseAppIndex.getInstance(getApplicationContext())
        for (bangumi in allBangumi) {
            val index = Indexables.digitalDocumentBuilder()
                .setName(StringUtil.getName(bangumi))
                .setText(bangumi.summary)
                .setUrl(Constant.DETAIL_URL_PREFIX + bangumi.id)
                .setImage(bangumi.cover)
                .build()

            firebaseAppIndex.update(index)
        }
    }
}
