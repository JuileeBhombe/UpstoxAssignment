package com.juileebhombe.upstoxassignment.ui.holdings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juileebhombe.upstoxassignment.data.model.HoldingsDataModel
import com.juileebhombe.upstoxassignment.data.repository.HoldingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(private val repository: HoldingsRepository) :
    ViewModel() {

    private val _uiState: MutableLiveData<HoldingsDataModel> by lazy {
        MutableLiveData(
            HoldingsDataModel()
        )
    }
    val uiState: LiveData<HoldingsDataModel?>
        get() = _uiState


    fun fetchHoldings() {
        viewModelScope.launch {
            val data = repository.getHoldings()

            withContext(Dispatchers.Main) {
                _uiState.value = data
            }
        }
    }
}