package com.example.authorizzationtesttask

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.authorizzationtesttask.databinding.FragmentLoginBinding
import com.example.authorizzationtesttask.repository.MyRepository
import com.example.authorizzationtesttask.viewModel.MyViewModel

class LoginFragment : Fragment() {

    private lateinit var viewModel: MyViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myApi = RetrofitClient.instance
        val repository = MyRepository(myApi)
        val factory = MyViewModel.MyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val username = binding.login.text.toString()
            val password = binding.password.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(username, password)
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess == "true") {
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                val editor = sharedPref?.edit()
                editor?.putString("token", "app-key")
                editor?.apply()
                findNavController().navigate(R.id.action_loginFragment_to_paymentsFragment)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}