package com.example.collaborator.views.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collaborator.databinding.QualificationRowBinding
import com.example.collaborator.databinding.SearchRowBinding
import com.example.collaborator.models.Expert

class QualificationAdapter (val text:String): RecyclerView.Adapter<QualificationAdapter.itemViewHolder>() {
    class itemViewHolder (val binding: QualificationRowBinding): RecyclerView.ViewHolder(binding.root)
    var expertList= emptyList<String>()
    var educationList = emptyList<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):itemViewHolder {
        return itemViewHolder(
            QualificationRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {

        holder.binding.apply {
            if(text=="Education"){
                val education=educationList[position]
                qualificationExpertTxt.text=education
            }else{
                val expert=expertList[position]
                qualificationExpertTxt.text=expert

            }
        }

    }

    override fun getItemCount():Int {
        if (text == "Education") {
            return educationList.size
        }
        return expertList.size
    }

    fun updateList(newList:List<String>,type:String){
        if (type == "Education") {
            educationList = newList
            notifyDataSetChanged()
            return
        }
         expertList = newList
        notifyDataSetChanged()

    }

}