package com.juileebhombe.upstoxassignment.ui.holdings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.juileebhombe.upstoxassignment.ui.holdings.widgets.HoldingList
import com.juileebhombe.upstoxassignment.ui.holdings.widgets.Portfolio
import com.juileebhombe.upstoxassignment.utils.AppConstants

@Composable
fun HoldingsScreen(
    viewModel: HoldingsViewModel = hiltViewModel(),
) {

    val data = viewModel.uiState.observeAsState()
    val isLoading = viewModel.isLoading.observeAsState()
    val isNetworkConnected = viewModel.isNetworkConnected.observeAsState()
    val isError = viewModel.isError.observeAsState()

    if (isLoading.value == false) data.value?.let {
        Column(
            Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            isNetworkConnected.value?.let { it1 ->
                AnimatedVisibility(
                    visible = !it1, modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    Card(shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)) {
                        Text(text = AppConstants.NO_INTERNET, Modifier.padding(5.dp))
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                if (it.isNotEmpty()) {
                    HoldingList(data = it, modifier = Modifier.padding(bottom = 40.dp))
                    Portfolio(Modifier.align(Alignment.BottomCenter))

                } else {
                    Text(
                        text = AppConstants.EMPTY_HOLDINGS,
                        Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    } else if (isError.value == true) {

        Box(modifier = Modifier.fillMaxSize()) {

            Text(
                text = AppConstants.ERROR,
                Modifier.align(Alignment.Center)
            )

        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) { CircularProgressIndicator(color = Color.Blue) }
    }
}

