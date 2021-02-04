package kz.kazpost.loadingarea.ui.s_invoice.remove

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kz.kazpost.loadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.loadingarea.databinding.FragmentRemoveSInvoiceBinding
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceAdapter

class RemoveSInvoiceFragment : Fragment() {
    private val safeArgs by lazy { RemoveSInvoiceFragmentArgs.fromBundle(requireArguments()) }
    private val navController by lazy { findNavController() }
    private val viewModel: RemoveSInvoiceViewModel by viewModels()
    private var _binding: FragmentRemoveSInvoiceBinding? = null
    private val binding get() = _binding!!
    private val sInvoiceAdapter = SInvoiceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRemoveSInvoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewModel.sInvoiceListLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                sInvoiceAdapter.submitList(it)
                binding.layoutEmpty.root.isGone = true
            } else {
                sInvoiceAdapter.submitList(emptyList())
                binding.layoutEmpty.root.isVisible = true
            }
        }
    }

    private fun initViews() {
        binding.rvSInvoices.apply {
            adapter = sInvoiceAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initViewModel() {
        val tInvoiceNumber = safeArgs.tInvoiceNumber
        viewModel.init(tInvoiceNumber)
        connectToLoadingViewModel(viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}