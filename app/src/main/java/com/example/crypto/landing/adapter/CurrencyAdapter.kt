package com.example.crypto.landing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto.databinding.CurrencyListItemBinding
import com.example.crypto.model.Currency

class CurrencyAdapter: RecyclerView.Adapter<CurrencyAdapter.AdapterViewHolder>() {
    private var mOnItemClickListener: OnRecyclerItemClickListener? = null

    private val currencyList = ArrayList<Currency>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val currency = currencyList[position]
        holder.onBind(currency)
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(it, currency)
        }
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    /**
     * adding the list received to adapter
     * @param: [list] - used to populate the adapter items
     */
    fun updateList(list: ArrayList<Currency>) {
        currencyList.clear()
        val size = currencyList.size
        currencyList.addAll(list)
        notifyItemRangeChanged(size, currencyList.size)
    }

    /**
     * initiating the listener
     *
     * @param:[listener] - use this to assign the class level listener
     */
    fun setOnItemClickListener(listener: OnRecyclerItemClickListener) {
        mOnItemClickListener = listener
    }

    class AdapterViewHolder(private val binding: CurrencyListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): AdapterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CurrencyListItemBinding.inflate(layoutInflater, parent, false)
                return AdapterViewHolder(binding)
            }
        }

        fun onBind(currency: Currency) {
            binding.currency = currency
            binding.executePendingBindings()
        }
    }

    /**
     * custom interface to pass the click event on item to the listener
     */
    interface OnRecyclerItemClickListener {
        fun onItemClick(
                item: View,
                currency: Currency
        )
    }
}