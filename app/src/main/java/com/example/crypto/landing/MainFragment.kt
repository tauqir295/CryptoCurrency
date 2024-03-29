package com.example.crypto.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.crypto.R
import com.example.crypto.databinding.FragmentMainBinding
import com.example.crypto.detail.DetailFragment
import com.example.crypto.landing.adapter.CurrencyAdapter
import com.example.crypto.model.Currency
import com.example.crypto.util.Constants
import com.example.crypto.util.handleAPIFail
import com.example.crypto.util.replaceWithNextFragment
import com.example.network.Status
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainFragment : Fragment(), CurrencyAdapter.OnRecyclerItemClickListener {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainFragmentViewModel by viewModels()
    private val adapter = CurrencyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // data binding is used
        binding = FragmentMainBinding.inflate(inflater, container, false)

        // data binding is used
        binding.apply {
            currencyRv.adapter = adapter
            adapter.setOnItemClickListener(this@MainFragment)

            binding.viewModel = this@MainFragment.viewModel

            // Specify the current fragment as the lifecycle owner of the binding.
            // This is necessary so that the binding can observe updates.
            lifecycleOwner = this@MainFragment
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment MainFragment.
         */
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //disable the home back button
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
                title = getString(R.string.currency_wallet_list)
            }
        }
        setUpObserver()
    }

    /**
     * check value change on viewModel.currencies live data
     */
    private fun setUpObserver() {
        viewModel.currencies.observe(viewLifecycleOwner, {
            when (it.status) {

                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { currencyList ->
                        if (currencyList.isNotEmpty()) {
                            binding.currencyRv.itemAnimator = null
                            adapter.updateList(currencyList)
                        } else {
                            Toast.makeText(requireContext(), getString(R.string.no_data_found), Toast.LENGTH_SHORT).show()
                        }
                    }?: handleAPIFail(requireActivity() as AppCompatActivity) // if no data found then show generic error message
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                    handleAPIFail(requireActivity() as AppCompatActivity)
                }
            }
        })
    }

    override fun onItemClick(item: View, currency: Currency) {
        requireActivity().run {
            replaceWithNextFragment(
                this@MainFragment.id,
                supportFragmentManager,
                DetailFragment.newInstance(),
                Bundle().apply {
                    putParcelable(Constants.CURRENCY, currency)
                }
            )
        }

    }
}