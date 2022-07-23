package com.htetarkarzaw.codigotest1.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.htetarkarzaw.codigotest1.R
import com.htetarkarzaw.codigotest1.common.general.Endpoint
import com.htetarkarzaw.codigotest1.databinding.ViewHolderMovieBinding
import com.htetarkarzaw.codigotest1.domain.vo.MovieVo

class MovieAdapter(
    private val onClickDetail: (Int) -> Unit,
    private val onClickFav: (Int) -> Unit
) :
    ListAdapter<MovieVo, RecyclerView.ViewHolder>(newDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ViewHolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LanguageViewHolder).bind(getItem(position))
    }

    companion object {
        val newDiffUtil = object : DiffUtil.ItemCallback<MovieVo>() {
            override fun areItemsTheSame(oldItem: MovieVo, newItem: MovieVo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieVo, newItem: MovieVo): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun getClickItem(position: Int): MovieVo = getItem(position)
    fun getClickFav(position: Int): MovieVo = getItem(position)

    inner class LanguageViewHolder(private val binding: ViewHolderMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieVo?) {
            binding.tvTitle.text = item?.title ?: ""
            binding.tvDescription.text = item?.overview ?: ""
            Glide.with(itemView.context)
                .load(Endpoint.MOVIES_IMAGE_URL + (item?.imageUrl ?: ""))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.place_holder)
                .into(binding.imgMovie)
            if (item?.isFav == true) {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_fav_select)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.place_holder)
                    .into(binding.imgFav)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_fav_unselect)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.place_holder)
                    .into(binding.imgFav)
            }
            binding.imgFav.setOnClickListener {
                onClickFav(adapterPosition)
            }
            itemView.setOnClickListener { onClickDetail(adapterPosition) }
        }
    }
}