package kz.kazpost.loadingarea.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.loadingarea.BuildConfig
import kz.kazpost.loadingarea.R
import kz.kazpost.loadingarea.base.LoadingViewModel.Companion.connectToLoadingViewModel
import kz.kazpost.loadingarea.databinding.FragmentAuthBinding
import kz.kazpost.loadingarea.util.EventObserver

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initViewModel()
    }

    private fun initViewModel() {
        connectToLoadingViewModel(viewModel) {
            if (it) {
                binding.bSignIn.startLoading()
            } else {
                binding.bSignIn.cancelLoading()
            }
        }
        viewModel.init()
    }

    private fun initObservers() {
        viewModel.authResultLiveData.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                binding.bSignIn.loadingSuccessful()
                navController.navigate(AuthFragmentDirections.actionAuthFragmentToTransportFragment())
            } else {
                binding.bSignIn.loadingFailed()
            }
        })
    }

    private fun initViews() {
        setVersionName()

        binding.bSignIn.setOnClickListener {
            viewModel.authorize()
        }

        binding.etLogin.doOnTextChanged { text, _, _, _ ->
            viewModel.setLogin(text.toString())
        }

        binding.etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.setPassword(text.toString())
        }
    }

    private fun setVersionName() {
        binding.tvVersion.text = getString(
            R.string.version,
            BuildConfig.VERSION_NAME
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "AuthFragment"
    }
}