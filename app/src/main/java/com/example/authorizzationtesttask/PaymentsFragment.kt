package com.example.authorizzationtesttask

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.authorizzationtesttask.databinding.FragmentPaymentsBinding
import com.example.authorizzationtesttask.recycler.PaymentsAdapter
import com.example.authorizzationtesttask.viewModel.MyViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.authorizzationtesttask.repository.MyRepository


class PaymentsFragment : Fragment() {

    private lateinit var viewModel: MyViewModel
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val myApi = RetrofitClient.instance
        val repository = MyRepository(myApi)
        val factory = MyViewModel.MyViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)


        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        Log.d("LogPayments", "${sharedPref?.getString("token", "Authorization")}")
        val token = sharedPref?.getString("token", "Authorization")

        if (token != null) {
            viewModel.getPayments(token)
        }


        val layoutManager = LinearLayoutManager(context)
        binding.paymentsRecyclerView.layoutManager = layoutManager


        viewModel.payments.observe(viewLifecycleOwner, Observer { payments ->

            val adapter = PaymentsAdapter(payments)
            binding.paymentsRecyclerView.adapter = adapter
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                val token = sharedPref?.getString("token", "Authorization")

                if (token != null) {
                    viewModel.logout(token)
                }
                findNavController().navigate(R.id.action_paymentsFragment_to_loginFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}