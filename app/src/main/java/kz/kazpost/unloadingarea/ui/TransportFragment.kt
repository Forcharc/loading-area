package kz.kazpost.unloadingarea.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.unloadingarea.R
import kz.kazpost.unloadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.unloadingarea.databinding.FragmentTransportsBinding
import kz.kazpost.unloadingarea.ui.adapters.TransportAdapter
import kz.kazpost.unloadingarea.util.RecyclerViewItemMarginsDecoration
import kz.kazpost.unloadingarea.util.dpToPx

@AndroidEntryPoint
class TransportFragment : Fragment() {

    private var _binding: FragmentTransportsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransportViewModel by viewModels()
    private val transportAdapter = TransportAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        initObservers()

        viewModel.loadTransportList()

        connectToLoadingViewModel(viewModel)
    }

    private fun initObservers() {
        viewModel.transportLiveData.observe(viewLifecycleOwner) {
            if (it != null) transportAdapter.submitList(it)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}