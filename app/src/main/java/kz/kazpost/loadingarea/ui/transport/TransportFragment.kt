package kz.kazpost.loadingarea.ui.transport

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.loadingarea.base.NavigateUpActivity
import kz.kazpost.loadingarea.databinding.FragmentTransportBinding
import kz.kazpost.loadingarea.ui._adapters.TransportAdapter
import kz.kazpost.loadingarea.ui._adapters.TransportAdapter.TransportActionType
import kz.kazpost.loadingarea.ui._decorations.RecyclerViewItemMarginsDecoration
import kz.kazpost.loadingarea.ui._models.TransportModel

@AndroidEntryPoint
class TransportFragment : Fragment(), TransportAdapter.TransportActionListener {

    private val navController by lazy { findNavController() }
    private var _binding: FragmentTransportBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransportViewModel by viewModels()
    private val transportAdapter = TransportAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitOnBackButtonPress()


        initViews()

        initObservers()

        viewModel.loadTransportList()

        connectToLoadingViewModel(viewModel)
    }

    private fun exitOnBackButtonPress() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitUserConfirmationDialog {
                    this.remove()
                    viewModel.exitUser()
                    requireActivity().onBackPressed()
                }
            }
        }
        val onBackButtonPressedCallback =
            object : NavigateUpActivity.OnBackButtonPressedCallback() {
                override fun onNavigateUp(
                    navController: NavController,
                    configuration: AppBarConfiguration
                ) {
                    showExitUserConfirmationDialog {
                        viewModel.exitUser()
                        super.onNavigateUp(navController, configuration)
                    }
                }
            }

        (requireActivity() as NavigateUpActivity).apply {
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
            setOnBackButtonPressedCallback(viewLifecycleOwner, onBackButtonPressedCallback)
        }
    }

    fun showExitUserConfirmationDialog(onExit: () -> Unit) {
        AlertDialog.Builder(requireContext()).setTitle(R.string.exit)
            .setMessage(getString(R.string.do_you_want_to_exit))
            .setPositiveButton(R.string.yes) { dialog, _ ->
                onExit()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun initObservers() {
        viewModel.transportLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                transportAdapter.submitList(it)
                binding.layoutEmpty.root.isGone = true
            } else {
                transportAdapter.submitList(emptyList())
                binding.layoutEmpty.root.isVisible = true
            }
        }
    }

    private fun initViews() {
        binding.rvTransports.apply {
            adapter = transportAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)

            val mediumIndent = resources.getDimension(R.dimen.indent_medium)
            val smallIndent = resources.getDimension(R.dimen.indent_small)
            addItemDecoration(
                RecyclerViewItemMarginsDecoration(
                    mediumIndent,
                    smallIndent,
                    mediumIndent,
                    mediumIndent,
                    mediumIndent
                )
            )
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadTransportList()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTransportAction(
        transportModel: TransportModel,
        actionType: TransportActionType
    ) {
        when (actionType) {
            TransportActionType.ADD_S_INVOICE -> {
                navController.navigate(
                    TransportFragmentDirections.actionTransportFragmentToAddSInvoiceFragment(
                        transportModel.id,
                        transportModel.tInvoiceNumber,
                        transportModel.notYetVisitedDepartments.toTypedArray()
                    )
                )
            }
            TransportActionType.REMOVE_S_INVOICE -> {
                navController.navigate(
                    TransportFragmentDirections.actionTransportFragmentToRemoveSInvoiceFragment(
                        transportModel.tInvoiceNumber
                    )
                )
            }
            TransportActionType.LOAD_TRANSPORT -> {
                navController.navigate(
                    TransportFragmentDirections.actionTransportFragmentToScanFragment(
                        transportModel.index,
                        transportModel.tInvoiceNumber,
                        transportModel.id
                    )
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_transport, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item.itemId}")
        return when (item.itemId) {
            R.id.item_update -> {
                viewModel.loadTransportList()
                true
            }
            R.id.item_exit -> {
                viewModel.exitUser()
                navController.popBackStack()
                true
            }
            else -> false
        }
    }

    companion object {
        private const val TAG = "TransportFragment"
    }


}