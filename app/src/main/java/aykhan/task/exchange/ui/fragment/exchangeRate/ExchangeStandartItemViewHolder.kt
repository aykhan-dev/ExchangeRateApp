package aykhan.task.exchange.ui.fragment.exchangeRate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aykhan.task.exchange.databinding.ItemLayoutExchangeStandartBinding
import aykhan.task.exchange.local.ExchangeRate

class ExchangeStandartItemViewHolder private constructor(
    private val binding: ItemLayoutExchangeStandartBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: ExchangeRate,
        clickListener: (code: String) -> Unit,
        amount: Double
    ) {
        binding.apply {
            exchangeItemData = data
        }
        with(binding) {
            root.setOnClickListener { clickListener(data.code) }
        }
    }

    companion object {

        fun from(parent: ViewGroup): ExchangeStandartItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = ItemLayoutExchangeStandartBinding.inflate(inflater, parent, false)
            return ExchangeStandartItemViewHolder(view)
        }

    }

}