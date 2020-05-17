package aykhan.task.exchange.utils

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import aykhan.task.exchange.local.ExchangeRate
import aykhan.task.exchange.ui.UiState
import aykhan.task.exchange.ui.fragment.exchangeRate.ExchangeRateRecyclerAdapter

@BindingAdapter("loading")
fun ProgressBar.bindUiState(uiState: UiState) {
    this.visibility = if (uiState == UiState.Loading) VISIBLE else GONE
}

