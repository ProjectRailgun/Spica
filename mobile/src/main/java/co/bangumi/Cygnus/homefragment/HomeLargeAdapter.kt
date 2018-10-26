package co.bangumi.Cygnus.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import co.bangumi.common.model.Bangumi
import co.bangumi.Cygnus.R

class HomeLargeAdapter(
        ndatas: HomeData,
        callback: (Bangumi) -> Unit
) : HomeHorizontalAdapter(ndatas, callback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumCardHolder? {
        return MediumCardHolder(LayoutInflater.from(parent.context).inflate(co.bangumi.Cygnus.R.layout.include_bangumi_large, parent, false))
    }
}