package kz.kazpost.unloadingarea.ui.add_s_invoice

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
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.unloadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.unloadingarea.databinding.FragmentAddSInvoiceBinding


@AndroidEntryPoint
class AddSInvoiceFragment() : Fragment() {

    private val navController by lazy { findNavController() }
    private val viewModel: AddSInvoiceViewModel by viewModels()
    private var _binding: FragmentAddSInvoiceBinding? = null
    private val binding get() = _binding!!
    private val sInvoiceAdapter = SInvoiceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddSInvoiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
        initObservers()

        viewModel.loadSInvoices()
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
        val tInvoiceNumber = AddSInvoiceFragmentArgs.fromBundle(requireArguments()).tInvoiceNumber
        viewModel.init(tInvoiceNumber)
        connectToLoadingViewModel(viewModel)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
