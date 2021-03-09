package com.example.crypto.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.crypto.util.Constants.CURRENCY
import com.example.crypto.R
import com.example.crypto.databinding.FragmentDetailBinding
import com.example.crypto.model.Currency

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private var currency: Currency? = null
    private lateinit var binding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currency = it.getParcelable(CURRENCY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.currency = currency

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

            findViewById<TextView>(R.id.tvDetail).text = currency?.price.toString()
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