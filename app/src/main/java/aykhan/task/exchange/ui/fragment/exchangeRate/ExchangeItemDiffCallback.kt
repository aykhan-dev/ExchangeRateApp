package aykhan.task.exchange.ui.fragment.exchangeRate

import androidx.recyclerview.widget.DiffUtil
import aykhan.task.exchange.local.ExchangeRate

object ExchangeItemDiffCallback : DiffUtil.ItemCallback<ExchangeRate>() {

    override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate): Boolean {
        return oldItem == newItem
    }

}