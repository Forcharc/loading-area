package kz.kazpost.loadingarea.ui.s_invoice.add

import android.os.Bundle
import android.view.*
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.loadingarea.databinding.FragmentAddSInvoiceBinding
import kz.kazpost.loadingarea.ui._adapters.SInvoiceAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
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
        binding.swl.setOnRefreshListener {
            viewModel.loadSInvoices()
            binding.swl.isRefreshing = false
        }

        binding.rvSInvoices.apply {
            adapter = sInvoiceAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.bCancel.setOnClickListener {
            navController.popBackStack()
        }

        binding.bAdd.setOnClickListener {
            viewModel.addSInvoicesToTInvoice(sInvoiceAdapter.getCheckedItems().map { it.id })
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_s_invoice, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_update -> {
                viewModel.loadSInvoices()
                true
            }
            else -> false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val TAG = "AddSInvoiceFragment"
    }
}
