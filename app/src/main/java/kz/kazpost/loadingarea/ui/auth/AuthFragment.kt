package kz.kazpost.loadingarea.ui.auth

import android.os.Bundle
import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
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

        binding.etLogin.apply {
            doOnTextChanged { text, _, _, _ ->
                viewModel.setLogin(text.toString())
            }

            // Transform uppercase letters to lower case
            filters = arrayOf(object : InputFilter {
                override fun filter(
                    source: CharSequence,
                    start: Int,
                    end: Int,
                    dest: Spanned?,
                    dstart: Int,
                    dend: Int
                ): CharSequence? {
                    for (i in start until end) {
                        if (source[i].isUpperCase()) {
                            val v = CharArray(end - start)
                            TextUtils.getChars(source, start, end, v, 0)
                            val s = String(v).toLowerCase()

                            return if (source is Spanned) {
                                val sp = SpannableString(s)
                                TextUtils.copySpansFrom(source, start, end, null, sp, 0)
                                sp
                            } else {
                                s
                            }
                        }
                    }
                    return null // keep original
                }
            })
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