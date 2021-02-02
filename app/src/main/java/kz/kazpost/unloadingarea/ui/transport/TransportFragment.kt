package kz.kazpost.unloadingarea.ui.transport

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.unloadingarea.R
import kz.kazpost.unloadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.unloadingarea.databinding.FragmentTransportBinding
import kz.kazpost.unloadingarea.ui.adapters.TransportAdapter
import kz.kazpost.unloadingarea.util.RecyclerViewItemMarginsDecoration
import kz.kazpost.unloadingarea.util.extentions.showSnackShort

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


        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    this.remove()
                    viewModel.exitUser()
                    requireActivity().onBackPressed()
                }
            }
        )


        initViews()

        initObservers()

        viewModel.loadTransportList()

        connectToLoadingViewModel(viewModel)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: ${item.itemId}")
        return when (item.itemId) {
            android.R.id.home -> {
                view?.showSnackShort("home")
                true
            }
            else -> {
                view?.showSnackShort("other")
                true
                //super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTransportAction(
        tInvoiceNumber: String,
        actionType: TransportAdapter.TransportActionType
    ) {
        when (actionType) {
            TransportAdapter.TransportActionType.ADD_S_INVOICE -> {
                navController.navigate(
                    TransportFragmentDirections.actionTransportFragmentToAddSInvoiceFragment(
                        tInvoiceNumber
                    )
                )
            }
            TransportAdapter.TransportActionType.REMOVE_S_INVOICE -> {
                view?.showSnackShort("REMOVE")
            }
            TransportAdapter.TransportActionType.LOAD_TRANSPORT -> {
                view?.showSnackShort("LOAD")
            }
        }
    }

    companion object {
        private const val TAG = "TransportFragment"
    }

}