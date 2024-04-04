package com.juileebhombe.upstoxassignment.ui.holdings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.juileebhombe.upstoxassignment.ui.holdings.widgets.HoldingList
import com.juileebhombe.upstoxassignment.ui.holdings.widgets.Portfolio

@Composable
fun HoldingsScreen(
    viewModel: HoldingsViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.fetchHoldings()
    }
    val data = viewModel.uiState.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState()

    if (isLoading.value == false) data.value?.let {
        Box {
            HoldingList(data = it, modifier = Modifier.padding(bottom = 40.dp))
            Portfolio(Modifier.align(Alignment.BottomCenter))
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) { CircularProgressIndicator(color = Color.Blue) }
    }
}

