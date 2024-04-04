package com.juileebhombe.upstoxassignment.ui.holdings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juileebhombe.upstoxassignment.data.local.entity.Holding
import com.juileebhombe.upstoxassignment.data.repository.HoldingsRepository
import com.juileebhombe.upstoxassignment.ui.holdings.utils.HoldingsHelper
import com.juileebhombe.upstoxassignment.utils.NetworkHelper
import com.juileebhombe.upstoxassignment.utils.formatCost
import com.juileebhombe.upstoxassignment.utils.formatPercentage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repository: HoldingsRepository,
    private val helper: HoldingsHelper,
    private val networkHelper: NetworkHelper,
) : ViewModel() {


    private val _uiState: MutableLiveData<List<Holding>> by lazy {
        MutableLiveData(
            listOf()
        )
    }
    val uiState: LiveData<List<Holding>?>
        get() = _uiState

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(true)
    }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isNetworkConnected: LiveData<Boolean> = networkHelper.observeNetworkConnectivity()
    private val _isError: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }
    val isError: LiveData<Boolean>
        get() = _isError


    init {
        isNetworkConnected.observeForever { isConnected ->
            if (isConnected) {
                fetchHoldings()
            } else {
                fetchHoldingsFromDB()
            }
        }
    }

    private fun fetchHoldings() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getHoldings()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _isError.value = true
                }.collect {
                    _isLoading.value = false
                    _uiState.value = it
                }

        }
    }

    private fun fetchHoldingsFromDB() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getHoldingsFromDB()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _isError.value = true
                }.collect {
                    _isLoading.value = false
                    _uiState.value = it
                }

        }
    }

    fun calculateTotalPNL(userHolding: Holding): String {
        return helper.calculateTotalPNL(userHolding).formatCost()
    }

    fun calculateCurrentValue(): String? {
        uiState.value?.let {
            return helper.calculateCurrentValue(it).formatCost()
        }
        return null
    }

    fun calculateTotalInvestment(): String? {
        uiState.value?.let {
            return helper.calculateTotalInvestment(it).formatCost()
        }
        return null
    }

    fun calculateTodaySPNL(): String? {
        uiState.value?.let {
            return helper.calculateTodaySPNL(it).formatCost()
        }
        return null
    }

    fun calculateTotalPNL(): String? {
        uiState.value?.let {
            return helper.calculateTotalPNL(it).formatCost()
        }
        return null
    }

    fun calculatePercentPNL(): String? {
        uiState.value?.let {
            return helper.calculatePercentagePNL(it).formatPercentage(2)
        }
        return null
    }

}