package kz.kazpost.loadingarea.ui.scan

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.loadingarea.databinding.FragmentScanBinding
import kz.kazpost.loadingarea.ui._adapters.ParcelCategoryAdapter
import kz.kazpost.loadingarea.ui._models.MissingShpisModel
import kz.kazpost.loadingarea.util.EventObserver

@AndroidEntryPoint
class ScanFragment : Fragment() {
    private val navController by lazy { findNavController() }
    private val safeArgs by lazy { ScanFragmentArgs.fromBundle(requireArguments()) }
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ScanViewModel by viewModels()

    private val categoryAdapter = ParcelCategoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initObservers()
        initViews()
    }

    private fun initViews() {
        binding.etShpi.apply {
            requestFocus()
            doOnTextChanged { text, _, _, _ ->
                viewModel.onShpiChanged(text.toString())
            }
        }

        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        binding.bCancel.setOnClickListener {
            navController.popBackStack()
        }

        binding.bFinish.setOnClickListener {
            viewModel.confirmParcels()
        }
    }

    private fun initObservers() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                categoryAdapter.submitList(it)
                binding.layoutEmpty.root.isGone = true
            } else {
                categoryAdapter.submitList(emptyList())
                binding.layoutEmpty.root.isVisible = true
            }
        }

        viewModel.clearShpiLiveData.observe(viewLifecycleOwner, EventObserver {
            if (it) binding.etShpi.setText("")
        })

        viewModel.missingShpisLiveData.observe(viewLifecycleOwner, EventObserver { missingShpis ->
            showMissingShpisDialog(missingShpis)
        })

        viewModel.scanSuccessLiveData.observe(viewLifecycleOwner) {
            if (it == true) {
                navController.popBackStack()
            }
        }
    }

    private fun showMissingShpisDialog(missingShpisModel: MissingShpisModel) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.error)
            .setMessage(
                getString(
                    R.string.shpis_missing,
                    missingShpisModel.missingShpis.joinToString(separator = ",\n"),
                    missingShpisModel.tInvoiceNumber
                )
            )
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun initViewModel() {
        connectToLoadingViewModel(viewModel)
        viewModel.init(safeArgs.index, safeArgs.tInvoiceNumber, safeArgs.tInvoiceId)
        viewModel.loadTInvoiceInfo()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}