package aykhan.task.exchange.ui.fragment.exchangeRate

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import aykhan.task.exchange.local.ExchangeRate

class ExchangeRateRecyclerAdapter(
    private val clickListener: (code: String) -> Unit,
    private val textListener: (amount: Double) -> Unit
) : ListAdapter<ExchangeRate, RecyclerView.ViewHolder>(ExchangeItemDiffCallback) {

    private val TYPE_BASE = 10
    private val TYPE_RATE = 20

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
            0 -> (holder as ExchangeBaseItemViewHolder).bind(getItem(position), textListener)
            else -> (holder as ExchangeStandartItemViewHolder).bind(
                getItem(position),
                clickListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_BASE else TYPE_RATE
    }

}