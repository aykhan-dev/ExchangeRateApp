package aykhan.task.exchange.ui.fragment.exchangeRate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import aykhan.task.exchange.databinding.FragmentExchangeRateBinding
import aykhan.task.exchange.viewModel.fragment.ExchangeRateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ExchangeRateFragment : Fragment() {

    private lateinit var binding: FragmentExchangeRateBinding

    private val viewModel by lazy { ViewModelProvider(this).get(ExchangeRateViewModel::class.java) }
    private val recyclerViewAdapter by lazy {
        ExchangeRateRecyclerAdapter(
            { viewModel.updateExchangeCode(it) },
            { viewModel.onAmountChange(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExchangeRateBinding.inflate(inflater)

        binding.apply {
            lifecycleOwner = this@ExchangeRateFragment
            viewModel = this@ExchangeRateFragment.viewModel
        }

        with(binding) {
            recyclerViewExchangeRates.adapter = recyclerViewAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchExchangeRates(false)
        observeData()
    }

    private fun observeData() {

        viewModel.exchangeRates.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                CoroutineScope(Main).launch {
                    val data = viewModel.modifyData(it)

                    binding.progressCircular.isVisible = false
                    recyclerViewAdapter.submitList(data)

                    viewModel.fetchExchangeRates(true)
                }
            }
        })

    }


}