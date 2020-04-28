package com.example.imagegallery.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.airbnb.epoxy.*
import com.example.imagegallery.R
import kotlinx.android.synthetic.main.item_epoxy_rv.view.*

@EpoxyModelClass(layout = R.layout.item_epoxy_rv)
abstract class SinglePersonModel : EpoxyModelWithHolder<SinglePersonModel.PersonHolder>() {

    @EpoxyAttribute
    @DrawableRes
    var avatar: Int = 0

    @EpoxyAttribute
    var description: String = ""

    @EpoxyAttribute
    var age: Int = 0

    override fun bind(holder: PersonHolder) {
        holder.avatarImage.setImageResource(avatar)
        holder.descriptionView.setText(description)
        holder.ageView.text = Integer.toString(age)
    }

    inner class PersonHolder : EpoxyHolder() {
        lateinit var avatarImage: ImageView
        lateinit var ageView: TextView
        lateinit var descriptionView: TextView

        override fun bindView(itemView: View) {
            avatarImage = itemView.avatar
            ageView = itemView.age
            descriptionView = itemView.description
        }
    }
}