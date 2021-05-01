package com.example.quizotime.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizotime.adapters.OptionAdapter
import com.example.quizotime.databinding.ActivityQuestionBinding
import com.example.quizotime.models.Question
import com.example.quizotime.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {
    lateinit var binding: ActivityQuestionBinding

    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpFireStore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
        binding.btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        binding.btnNext.setOnClickListener {
            index++
            bindViews()
        }

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            val json: String = Gson().toJson(quizzes!![0])
            Log.e("QUIZES", json)
            intent.putExtra("QUIZ", json)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpFireStore() {
        val firestore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }
                    else{
                        binding.noQuizText.visibility = View.VISIBLE
                    }
                }
        }
    }

    private fun bindViews() {

        binding.btnNext.visibility = View.GONE
        binding.btnPrevious.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE


        //First Question
        if (questions!!.size > 1) {

            if (index == 1) {
                binding.btnNext.visibility = View.VISIBLE
            } else if (index == questions!!.size) { //Last Question
                binding.btnPrevious.visibility = View.VISIBLE
                binding.btnSubmit.visibility = View.VISIBLE
            } else {
                binding.btnNext.visibility = View.VISIBLE
                binding.btnPrevious.visibility = View.VISIBLE
            }

        } else {
            binding.btnSubmit.visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]

        question?.let {
            binding.description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            binding.optionList.layoutManager = LinearLayoutManager(this)
            binding.optionList.adapter = optionAdapter
            binding.optionList.setHasFixedSize(true)
        }


    }
}