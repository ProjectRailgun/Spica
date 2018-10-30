package co.bangumi.Cassiopeia.homefragment

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import co.bangumi.Cassiopeia.R

class MediumCardHolder(view: View) : ViewHolder(view) {
    val image = view.findViewById(R.id.imageView) as ImageView
    val title = view.findViewById(R.id.title) as TextView
    val subtitle = view.findViewById(R.id.subtitle) as TextView?
    val time = view.findViewById(R.id.time) as TextView?
    val new = view.findViewById(R.id.new_count) as TextView?
    val eps = view.findViewById(R.id.eps) as TextView?
}
