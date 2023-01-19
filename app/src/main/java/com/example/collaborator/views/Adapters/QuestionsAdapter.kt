package com.example.collaborator.views.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collaborator.databinding.QuestionsRowBinding
import com.example.collaborator.models.Questions

class QuestionsAdapter (var clickListener: onClickListener): RecyclerView.Adapter<QuestionsAdapter.itemViewHolder>() {
    class itemViewHolder (val binding: QuestionsRowBinding): RecyclerView.ViewHolder(binding.root)
    var questionsList= emptyList<Questions>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):itemViewHolder {
        return itemViewHolder(QuestionsRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        var question=questionsList[position]
        holder.binding.apply {
            userTV.text= question.UserId
            questionTV.text=question.Question
            answerBtn.setOnClickListener {
                clickListener.AnswerBtn(question)
            }
        }
    }

    override fun getItemCount()=questionsList.size

    interface onClickListener{
        fun AnswerBtn(question: Questions)
    }

    fun updateList(questionsList:ArrayList<Questions>){
            this.questionsList = questionsList
            notifyDataSetChanged()

    }
}