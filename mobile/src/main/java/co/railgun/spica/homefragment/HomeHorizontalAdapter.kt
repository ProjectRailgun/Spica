package co.railgun.spica.homefragment

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.railgun.spica.R
import co.railgun.common.model.Bangumi
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

        holder.title.text = co.railgun.common.StringUtil.getName(bangumi)
        holder.subtitle?.text = co.railgun.common.StringUtil.subTitle(bangumi)
        holder.time?.text = bangumi.air_date
        holder.new?.text = holder.itemView.resources.getString(R.string.unwatched)
            .format(bangumi.unwatched_count)
        holder.eps?.text = holder.itemView.resources.getString(R.string.eps_all).format(bangumi.eps)

        val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.parseColor(bangumi.coverColor))
        Glide.with(holder.image.context)
            .load(bangumi.coverImage.url)
            .thumbnail(0.1f)
            .placeholder(BitmapDrawable(holder.image.resources, bitmap))
            .crossFade()
            .into(holder.image)

        holder.itemView.setOnClickListener { callback.invoke(bangumi) }
    }

    override fun getItemCount(): Int {
        datas.datas?.let { return it.size }
        return 0
    }
}
