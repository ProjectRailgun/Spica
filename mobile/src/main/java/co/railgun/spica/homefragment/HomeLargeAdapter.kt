package co.railgun.spica.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import co.railgun.spica.R
import co.railgun.common.model.Bangumi

class HomeLargeAdapter(
        ndatas: HomeData,
        callback: (Bangumi) -> Unit
) : HomeHorizontalAdapter(ndatas, callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumCardHolder {
        return MediumCardHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.include_bangumi_large,
                parent,
                false
            )
        )
    }
}