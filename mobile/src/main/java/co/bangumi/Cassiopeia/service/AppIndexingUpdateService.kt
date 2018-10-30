package co.bangumi.Cassiopeia.service

import android.content.Context
import android.content.Intent
import android.support.v4.app.JobIntentService
import android.util.Log
import co.bangumi.Cassiopeia.Constant
import co.bangumi.common.StringUtil
import co.bangumi.common.api.ApiClient
import co.bangumi.common.model.Bangumi
import com.google.firebase.appindexing.FirebaseAppIndex
import com.google.firebase.appindexing.builders.Indexables

class AppIndexingUpdateService : JobIntentService() {

    companion object {

        fun enqueueWork(context: Context) {
            JobIntentService.enqueueWork(
                context,
                AppIndexingUpdateService::class.java,
                Constant.UPDATE_INDEX_ALL_BANGUMI,
                Intent()
            )
        }
    }

    override fun onHandleWork(intent: Intent) {
        Log.d("IndexingUpdateService", "onHandleWorkStart")
        var allBangumi: List<Bangumi> = ArrayList();

        ApiClient.getInstance()
            .getSearchBangumi(1, 300, "air_date", "desc", null)
            .subscribe {
                allBangumi = it.getData()
            }

        if (allBangumi.isEmpty()) return;
        val firebaseAppIndex: FirebaseAppIndex = FirebaseAppIndex.getInstance();
        for (bangumi in allBangumi) {
            val index = Indexables.digitalDocumentBuilder()
                .setName(StringUtil.getName(bangumi))
                .setText(bangumi.summary)
                .setUrl(Constant.DETAIL_URL_PREFIX + bangumi.id)
                .setImage(bangumi.image)
                .build()

            firebaseAppIndex.update(index)
        }
    }
}
