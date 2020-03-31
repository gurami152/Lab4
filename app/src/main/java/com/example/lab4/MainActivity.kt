package com.example.lab4

import android.annotation.SuppressLint
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.Int
import com.example.lab4.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*
import android.os.CountDownTimer
import android.media.RingtoneManager
import android.media.Ringtone
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.*


class MainActivity : AppCompatActivity() {

    private var colorRight : Int = 1
    private var score: Int =0
    private var numberOfQuestions: Int = 0
    private var colorLeftText: Int = 1
    private var colorRightText: Int = 1
    private var colorLeft: Int = 1
    // таймер на 1 минуту



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        button.setOnClickListener { yesClick() }
        button2.setOnClickListener{ noClick() }
        button3.setOnClickListener{ start() }
        // скрытие перед стартом
        button.visibility=Button.INVISIBLE
        button2.visibility=Button.INVISIBLE
        textView2.visibility=TextView.INVISIBLE
        textView.visibility=TextView.INVISIBLE
        textView4.visibility=TextView.INVISIBLE
        textView5.visibility=TextView.INVISIBLE
        textView6.visibility=TextView.INVISIBLE
        progressBar.visibility=ProgressBar.INVISIBLE
    }

    fun start(){
        button3.visibility=Button.INVISIBLE
        textView7.visibility=TextView.INVISIBLE
        spinner.visibility=Spinner.INVISIBLE
        checkBox.visibility=CheckBox.INVISIBLE
        toggleButton.visibility=ToggleButton.INVISIBLE
        button.visibility=Button.VISIBLE
        button2.visibility=Button.VISIBLE
        textView2.visibility=TextView.VISIBLE
        textView.visibility=TextView.VISIBLE
        textView4.visibility=TextView.VISIBLE
        textView5.visibility=TextView.VISIBLE
        textView6.visibility=TextView.VISIBLE
        generateColors()
        val timer = object: CountDownTimer((spinner.selectedItemPosition+1)*10000.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView2.text = "Времени осталось 0:" + millisUntilFinished / 1000
            }

            override fun onFinish() {results()}
        }
        timer.start()
    }

    fun results(){
        progressBar.visibility=ProgressBar.VISIBLE
        if(toggleButton.isChecked) {
            try {
                val notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val r = RingtoneManager.getRingtone(applicationContext, notify)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        button.visibility= Button.INVISIBLE
        button2.visibility= Button.INVISIBLE
        textView6.visibility= TextView.VISIBLE
        textView6.text=("Было задано $numberOfQuestions вопросов из которых Вы правильно ответили на $score")
        progressBar.visibility=ProgressBar.INVISIBLE
    }


    private fun noClick(){
        numberOfQuestions++
        if(colorRight!=colorLeft){
            score++
        }
        generateColors()
    }

    private fun yesClick(){
        numberOfQuestions++
        if(colorRight==colorLeftText){
            score++
        }
        generateColors()
    }

    private fun rand(from: Int, to: Int) : Int {
        return (from..to).random()
    }

    @SuppressLint("ResourceType", "Recycle")
    fun generateColors() {

        val colors: TypedArray = resources.obtainTypedArray(R.array.rainbow)
        val strings: Array<out String> = resources.getStringArray(R.array.color_name)
        var color=rand(0,6)
        textView4.setTextColor(colors.getColor(color,0))
        colorLeft=color

        color = rand(0,6)
        textView4.text = strings[color]
        colorLeftText=color

        color = rand(0,6)
        textView5.setTextColor(colors.getColor(color,0))
        colorRight=color

        color = rand(0,6)
        textView5.text = strings[color]
        colorRightText=color
        if(checkBox.isChecked) {
            while ((color == colorRight) || (color == colorLeft)) {
                color = rand(0, 6)
            }
            main.setBackgroundColor(colors.getColor(color, 0))
        }

    }


}

