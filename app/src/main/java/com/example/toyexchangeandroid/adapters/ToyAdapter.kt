package com.example.toyexchangeandroid.adapters;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.toyexchangeandroid.R
import com.example.toyexchangeandroid.models.Toy

class ToyAdapter(var items: MutableList<Toy>) :
    RecyclerView.Adapter<ToyAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PostViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val postTitleTV: TextView = itemView.findViewById(R.id.Toyname)
        private val ToyPicIv: ImageView = itemView.findViewById(R.id.ToyPic)
        //private val text: TextView = itemView.findViewById(R.id.DescriptionPost)
        //private val userName: TextView = itemView.findViewById(R.id.UserNamePublication)
        //private val video: VideoView = itemView.findViewById(R.id.videoFeed)
        //private val progressbar: ProgressBar = itemView.findViewById(R.id.progressvideo)
        //private val probleem: ImageView = itemView.findViewById(R.id.errorVideo)
        //private val likesnumber: TextView = itemView.findViewById(R.id.likesnumber)
        //private val likeIcon: ImageView = itemView.findViewById(R.id.likeIcon)
        //private val likeIconRed: ImageView = itemView.findViewById(R.id.likeIconRed)

        fun bindView(item: Toy) {
            postTitleTV.text = item.Name

            itemView.setOnClickListener {

            }
        }
    }
}