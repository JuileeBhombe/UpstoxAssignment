package com.juileebhombe.upstoxassignment.ui.holdings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juileebhombe.upstoxassignment.data.model.HoldingsDataModel
import com.juileebhombe.upstoxassignment.data.model.UserHolding
import com.juileebhombe.upstoxassignment.data.repository.HoldingsRepository
import com.juileebhombe.upstoxassignment.ui.holdings.utils.HoldingsHelper
import com.juileebhombe.upstoxassignment.utils.formatCost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repository: HoldingsRepository,
    private val helper: HoldingsHelper,
) : ViewModel() {

    private val _uiState: MutableLiveData<HoldingsDataModel> by lazy {
        MutableLiveData(
            HoldingsDataModel()
        )
    }
    val uiState: LiveData<HoldingsDataModel?>
        get() = _uiState

    private val _isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData(true)
    }
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchHoldings() {
        _isLoading.value = true
        viewModelScope.launch {
            val data = repository.getHoldings()

            withContext(Dispatchers.Main) {
                _uiState.value = data
                _isLoading.value = false
            }
        }
    }

    fun calculateTotalPNL(userHolding: UserHolding): String {
        return helper.calculateTotalPNL(userHolding).formatCost()
    }

    fun calculateCurrentValue(): String? {
        uiState.value?.userHolding?.let {
            return helper.calculateCurrentValue(it).formatCost()
        }
        return null
    }

    fun calculateTotalInvestment(): String? {
        uiState.value?.userHolding?.let {
            return helper.calculateTotalInvestment(it).formatCost()
        }
        return null
    }

    fun calculateTodaySPNL(): String? {
        uiState.value?.userHolding?.let {
            return helper.calculateTodaySPNL(it).formatCost()
        }
        return null
    }

    fun calculateTotalPNL(): String? {
        uiState.value?.userHolding?.let {
            return helper.calculateTotalPNL(it).formatCost()
        }
        return null
    }
}