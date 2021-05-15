package com.piexxi.coinflipdb

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import com.piexxi.coinflipdb.databinding.ActivityMainBinding
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var heads = 0
    private var tails = 0
    private var total = 0

    var coinStatus = "Click Flip Button to Flip the Coin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.myActivity = this

        initinListeners()
        binding.etNumberofFlips.visibility = View.INVISIBLE
        binding.btSimulate.visibility = View.INVISIBLE
    }

    private fun initinListeners() {
        binding.swSimulate.setOnCheckedChangeListener { buttonView, isChecked -> enableSim(isChecked) }
        binding.btFlip.setOnClickListener { flip() }
        binding.btReset.setOnClickListener { resset() }
        binding.btSimulate.setOnClickListener { simulate() }
    }

    private fun enableSim(checked: Boolean) {

        if (checked) {
            binding.etNumberofFlips.visibility = View.VISIBLE
            binding.btSimulate.visibility = View.VISIBLE
        } else {
            binding.etNumberofFlips.visibility = View.INVISIBLE
            binding.btSimulate.visibility = View.INVISIBLE
        }

    }

    private fun flip() {
        val randomNumber = (0..1).random()
        if (randomNumber == 0) {
            updateFlip("heads")
        } else {
            updateFlip("tails")
        }
    }

    private fun updateFlip(coinvalue: String) {
        if (coinvalue == "heads") {
            heads++
            binding.ivCoin.setImageResource(R.drawable.ic_heads_icon)
            coinStatus = "You flipped a Head !!"
        } else {
            tails++
            binding.ivCoin.setImageResource(R.drawable.ic_tails_icon)
            coinStatus = "You flipped a Tail !!"
        }

        binding.apply { invalidateAll() }

        total++

        binding.tvTotalFlips.text = "Total Flips : $total"
        binding.tvTotalHeads.text = "Total Heads : $heads"
        binding.tvTotalTails.text = "Total Tails : $tails"

        updateStatistics()

    }

    private fun updateStatistics() {
        var headPercentResult = 0.0
        var tailPercentResult = 0.0

        if (total != 0) {
            headPercentResult = round((heads.toDouble() / total) * 10000) / 100
            tailPercentResult = round((tails.toDouble() / total) * 10000) / 100
        }

        binding.tvHeadPercent.text = "Heads : $headPercentResult %"
        binding.tvTailsPercent.text = "Tails : $tailPercentResult %"

        binding.pbHeadsPercent.progress = headPercentResult.toInt()
        binding.pbTailsPercent.progress = tailPercentResult.toInt()
    }

    private fun resset() {
        binding.ivCoin.setImageResource(R.drawable.ic_flip_icon)

        heads = 0
        tails = 0
        total = 0

        binding.tvTotalFlips.text = "Total Flips : $total"
        binding.tvTotalHeads.text = "Total Heads : $heads"
        binding.tvTotalTails.text = "Total Tails : $tails"

        coinStatus = "Click Flip Button to Flip the Coin"
        binding.apply { invalidateAll() }

        updateStatistics()
    }

    private fun simulate() {
        val enterNumber = binding.etNumberofFlips.text.toString().toInt()
        binding.etNumberofFlips.setText("")
        hideKeyboard()

        for (i in 1..enterNumber) {
            flip()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etNumberofFlips.windowToken, 0)
    }
}