package co.bangumi.Cassiopeia.homefragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.bangumi.Cassiopeia.R
import co.bangumi.common.model.Bangumi
import com.bumptech.glide.Glide

open class HomeHorizontalAdapter(private var datas: HomeData,
                                 private var callback: (Bangumi) -> Unit) : RecyclerView.Adapter<MediumCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediumCardHolder {
        return MediumCardHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.include_bangumi_medium,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediumCardHolder, position: Int) {
        val bangumi = datas.datas?.get(position) ?: return

        holder.title.text = co.bangumi.common.StringUtil.getName(bangumi)
        holder.subtitle?.text = co.bangumi.common.StringUtil.subTitle(bangumi)
        holder.time?.text = bangumi.air_date
        holder.new?.text = holder.itemView.resources.getString(R.string.unwatched)
            .format(bangumi.unwatched_count)
        holder.eps?.text = holder.itemView.resources.getString(R.string.eps_all).format(bangumi.eps)

        Glide.with(holder.image.context)
                .load(bangumi.image)
                .into(holder.image)

        holder.itemView.setOnClickListener { callback.invoke(bangumi) }
    }

    override fun getItemCount(): Int {
        datas.datas?.let { return it.size }
        return 0
    }
}
