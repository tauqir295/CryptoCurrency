package com.example.crypto.detail

import android.graphics.Color
import android.graphics.Paint
import androidx.lifecycle.ViewModel
import com.example.crypto.model.Currency
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * View model class used for updating the UI
 */
@HiltViewModel
class DetailViewModel @Inject constructor(): ViewModel() {
    var currency: Currency? = null

    // show historical chart using candle stick
    // this is for demo purpose only
    private val marketSize = 10
    private val entries = mutableListOf<CandleEntry>()
    var candleData: CandleData = CandleData()

    init {

        // source is https://steemit.com/utopian-io/@andrixyz/how-to-implement-candle-stick-chart-like-trading-stocks-or-cryptocurrency
        for (i in 0 until marketSize) {
            val mul: Int = marketSize + 10
            val value = (Math.random() * 10).toFloat() + mul
            val high = (Math.random() * 20).toFloat() + 8f
            val low = (Math.random() * 20).toFloat() + 8f
            val open = (Math.random() * 3).toFloat() + 1f
            val close = (Math.random() * 3).toFloat() + 1f
            val odd = i % 2 != 0

            entries.add(
                CandleEntry(
                    (i + 21).toFloat(), value + high,
                    value - low,
                    if (!odd) value + open else value - open,
                    if (odd) value - close else value + close
                )
            )
        }

        val candleDataSet = CandleDataSet(entries, "Market").apply {
            color = Color.rgb(80, 80, 80)
            shadowColor = Color.GRAY
            shadowWidth = 0.5f
            decreasingColor = Color.RED
            decreasingPaintStyle = Paint.Style.FILL
            increasingColor = Color.GREEN
            increasingPaintStyle = Paint.Style.FILL
            neutralColor = Color.LTGRAY
            setDrawValues(false)
        }

        candleData.addDataSet(candleDataSet)
    }
}