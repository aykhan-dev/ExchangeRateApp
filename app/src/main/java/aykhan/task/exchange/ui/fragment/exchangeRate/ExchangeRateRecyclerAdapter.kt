package aykhan.task.exchange.ui.fragment.exchangeRate

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import aykhan.task.exchange.local.ExchangeRate

class ExchangeRateRecyclerAdapter(
    private val clickListener: (code: String) -> Unit
) : ListAdapter<ExchangeRate, ExchangeStandartItemViewHolder>(ExchangeItemDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExchangeStandartItemViewHolder {
        return ExchangeStandartItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExchangeStandartItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

}