package aykhan.task.exchange.ui.fragment.exchangeRate

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aykhan.task.exchange.databinding.ItemLayoutExchangeBaseBinding
import aykhan.task.exchange.local.ExchangeRate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch

class ExchangeBaseItemViewHolder private constructor(
    private val binding: ItemLayoutExchangeBaseBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: ExchangeRate,
        textListener: (amount: Double) -> Unit
    ) {
        binding.apply {
            exchangeItemData = data
        }
        with(binding) {
            editTextAmount.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    CoroutineScope(Default).launch {
                        val input = editTextAmount.text.toString()
                        val multiplexer = if (input.isEmpty()) 1.0 else input.toDouble()
                        textListener(multiplexer)
                    }
                }

            })
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