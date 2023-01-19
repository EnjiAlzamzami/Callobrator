package com.example.collaborator.views.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collaborator.R
import com.example.collaborator.models.Expert
import com.example.collaborator.databinding.SearchRowBinding

class SearchAdapter (val clickListener: onClickListener): RecyclerView.Adapter<SearchAdapter.itemViewHolder>() {
    class itemViewHolder (val binding: SearchRowBinding): RecyclerView.ViewHolder(binding.root)
    var expertList= emptyList<Expert>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
      return itemViewHolder(SearchRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val expert = expertList[position]

        holder.binding.apply {

            if(expert.gender=="Female"){
                Glide.with(root.context).load(R.drawable.expertfemale).into(expertImg)
            }else{
                Glide.with(root.context).load(R.drawable.expertmale).into(expertImg)
            }
            expertName.text=expert.username
            expertExperience.text= "${expert.noOfExperience} Years."
            expertRate.text= expert.Rate.toString()
            //Rate

            searchExpertsLL.setOnClickListener {
                clickListener.onClick(expert)
            }

        }



    }


    override fun getItemCount(): Int {
        return expertList.size
    }

    fun updateAdapterList(expertList: List<Expert>){
        this.expertList = expertList
        notifyDataSetChanged()

    }

    interface onClickListener{
        fun onClick(expert:Expert)
    }
}