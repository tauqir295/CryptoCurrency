package com.example.crypto.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.crypto.R
import com.example.crypto.databinding.FragmentDetailBinding
import com.example.crypto.util.Constants.CURRENCY
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            viewModel.currency = it.getParcelable(CURRENCY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //disable the home back button
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                title = getString(R.string.detail)
            }

            // source is https://steemit.com/utopian-io/@andrixyz/how-to-implement-candle-stick-chart-like-trading-stocks-or-cryptocurrency
            findViewById<CandleStickChart>(R.id.candleStickChart).apply {

                setDrawBorders(true)
                description = Description().apply {
                    text = viewModel.currency?.name
                }

                axisLeft.run {
                    setDrawLabels(false)
                    setDrawGridLines(false)
                }
                axisRight.setDrawGridLines(false)

                xAxis.run {
                    setDrawGridLines(false) // disable x axis grid lines
                    position = XAxis.XAxisPosition.BOTTOM

                }
                data = viewModel.candleData
                invalidate()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of fragment
         * @return A new instance of fragment DetailFragment.
         */
        @JvmStatic
        fun newInstance() = DetailFragment()
    }
}