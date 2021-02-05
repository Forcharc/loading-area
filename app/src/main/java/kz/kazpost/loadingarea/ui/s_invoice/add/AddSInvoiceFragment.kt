package kz.kazpost.loadingarea.ui.s_invoice.add

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
import kz.kazpost.loadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.loadingarea.databinding.FragmentAddSInvoiceBinding
import kz.kazpost.loadingarea.ui.s_invoice.SInvoiceAdapter
import kz.kazpost.loadingarea.util.extentions.showSnackShort


@AndroidEntryPoint
class AddSInvoiceFragment() : Fragment() {

    private val safeArgs by lazy { AddSInvoiceFragmentArgs.fromBundle(requireArguments()) }
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

        viewModel.addSInvoicesResultLiveData.observe(viewLifecycleOwner) {
            if (it.get() == true) {
                requireView().showSnackShort("S-накладные успешно добавлены")
                navController.popBackStack()
            } else {
                requireView().showSnackShort("Ошибка. Не удалось добавить S-накладные")
            }
        }

    }

    private fun initViews() {
        binding.rvSInvoices.apply {
            adapter = sInvoiceAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.bCancel.setOnClickListener {
            navController.popBackStack()
        }

        binding.bAdd.setOnClickListener {
            viewModel.addSInvoicesToTInvoice(sInvoiceAdapter.getCheckedItems().map { it.id})
        }
    }

    private fun initViewModel() {
        val tInvoiceNumber = safeArgs.tInvoiceNumber
        val notYetVisitedDepartments = safeArgs.notYetVisitedDepartments
        val tInvoiceId = safeArgs.tInvoiceId
        viewModel.init(tInvoiceId, tInvoiceNumber, notYetVisitedDepartments.toList())
        connectToLoadingViewModel(viewModel)
        viewModel.loadSInvoices()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
