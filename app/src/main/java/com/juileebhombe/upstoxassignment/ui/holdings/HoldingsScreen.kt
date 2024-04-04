package com.juileebhombe.upstoxassignment.ui.holdings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HoldingsScreen(
    viewModel: HoldingsViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.fetchHoldings()
    }
    val data = viewModel.uiState.observeAsState()

    Column {
        data.value?.userHolding?.forEach {
            it?.symbol?.let { it1 -> Text(text = it1) }
        }
    }
}