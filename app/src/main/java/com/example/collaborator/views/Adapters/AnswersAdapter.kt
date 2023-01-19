package com.example.collaborator.views.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collaborator.databinding.AnswersRowBinding
import com.example.collaborator.models.Questions

class AnswersAdapter (var clickListener: onClick): RecyclerView.Adapter<AnswersAdapter.itemViewHolder>() {
    class itemViewHolder (val binding: AnswersRowBinding): RecyclerView.ViewHolder(binding.root)
    var answersList= emptyList<Questions>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):itemViewHolder {
        return itemViewHolder(AnswersRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
       val currentanswer=answersList[position]
        holder.binding.apply {
            expertName.text=currentanswer.ExpertId
            questionTxt.text=currentanswer.Question
            answerTxt.text=currentanswer.answer

            rateButton.setOnClickListener {
                clickListener.RateClick(currentanswer.ExpertId!!)
            }
        }
    }

    override fun getItemCount()=answersList.size

    fun updateList(answersList:ArrayList<Questions>){
        this.answersList=answersList
        notifyDataSetChanged()
    }

    interface onClick{
        fun RateClick(expertname:String)
    }
}