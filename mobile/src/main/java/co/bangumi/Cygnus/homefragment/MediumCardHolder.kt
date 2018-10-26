package co.bangumi.Cygnus.homefragment

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import co.bangumi.Cygnus.R

class MediumCardHolder(view: View) : ViewHolder(view) {
    val image = view.findViewById(co.bangumi.Cygnus.R.id.imageView) as ImageView
    val title = view.findViewById(co.bangumi.Cygnus.R.id.title) as TextView
    val subtitle = view.findViewById(co.bangumi.Cygnus.R.id.subtitle) as TextView?
    val time = view.findViewById(co.bangumi.Cygnus.R.id.time) as TextView?
    val new = view.findViewById(co.bangumi.Cygnus.R.id.new_count) as TextView?
    val eps = view.findViewById(co.bangumi.Cygnus.R.id.eps) as TextView?
}
