package aykhan.task.exchange.ui.fragment.exchangeRate

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import aykhan.task.exchange.local.ExchangeRate

class ExchangeRateRecyclerAdapter(
    private val clickListener: (code: String) -> Unit
) : ListAdapter<ExchangeRate, RecyclerView.ViewHolder>(ExchangeItemDiffCallback) {

    private val TYPE_BASE = 10
    private val TYPE_RATE = 20

    var amount = 1.0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_BASE -> ExchangeBaseItemViewHolder.from(parent)
            else -> ExchangeStandartItemViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> (holder as ExchangeBaseItemViewHolder).bind(getItem(position))
            else -> (holder as ExchangeStandartItemViewHolder).bind(
                getItem(position),
                clickListener,
                amount
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_BASE else TYPE_RATE
    }

}