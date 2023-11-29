package com.example.authorizzationtesttask.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.authorizzationtesttask.Data.Payment
import com.example.authorizzationtesttask.network.ApiException
import com.example.authorizzationtesttask.repository.MyRepository
import kotlinx.coroutines.launch
import kotlin.random.Random
import com.example.authorizzationtesttask.Data.ApiResponse



class MyViewModel(private val repository: MyRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    private val _logoutResult = MutableLiveData<String>()
    val logoutResult: LiveData<String> get() = _logoutResult

    private val _payments = MutableLiveData<List<Payment>>()
    val payments: LiveData<List<Payment>> get() = _payments

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun login(username: String, password: String) {
        if (username == "demo" && password == "12345") {
            viewModelScope.launch {
                try {
                    val response = repository.login(username, password)
                    _loginResult.value =
                        if (response.success.isNotEmpty()) response.success else null
                } catch (e: ApiException) {
                    _error.value = e.message
                }
            }
        } else {
            _error.value = "Неверное имя пользователя или пароль"
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.logout(token)
                _logoutResult.value = if (response.success.isNotEmpty()) response.success else null
            } catch (e: ApiException) {
                _error.value = e.message
            }
        }
    }

    fun getPayments(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPayments(token)
                val paymentsFromApi = response.payments
                val paymentsList = if (paymentsFromApi?.isEmpty() == true) {
                    List(10) {
                        Payment(
                            id = Random.nextInt(1000, 9999),
                            amount = Random.nextDouble(100.0, 9999.99),
                            date = "${Random.nextInt(1, 31)}/${Random.nextInt(1, 12)}/2023"
                        )
                    }
                } else {
                    paymentsFromApi
                }
                paymentsList.also { _payments.value = it }
            } catch (e: ApiException) {
                _error.value = e.message
            }
        }
    }

    class MyViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
                return MyViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

