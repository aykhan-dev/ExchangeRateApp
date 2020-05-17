package aykhan.task.exchange.ui.fragment.exchangeRate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aykhan.task.exchange.databinding.ItemLayoutExchangeBaseBinding
import aykhan.task.exchange.local.ExchangeRate

class ExchangeBaseItemViewHolder private constructor(
    private val binding: ItemLayoutExchangeBaseBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ExchangeRate) {
        binding.apply {
            exchangeItemData = data

        }
        with(binding) {

        }
    }

    companion object {

        fun from(parent: ViewGroup): ExchangeBaseItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = ItemLayoutExchangeBaseBinding.inflate(inflater, parent, false)
            return ExchangeBaseItemViewHolder(view)
        }

    }

}