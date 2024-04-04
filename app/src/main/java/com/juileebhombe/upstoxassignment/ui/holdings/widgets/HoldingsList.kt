package com.juileebhombe.upstoxassignment.ui.holdings.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juileebhombe.upstoxassignment.data.model.HoldingsDataModel
import com.juileebhombe.upstoxassignment.data.model.UserHolding
import com.juileebhombe.upstoxassignment.ui.holdings.HoldingsViewModel
import com.juileebhombe.upstoxassignment.utils.AppConstants
import com.juileebhombe.upstoxassignment.utils.formatCost
import com.juileebhombe.upstoxassignment.utils.isNegative
import kotlin.random.Random

@Composable
fun HoldingList(
    data: HoldingsDataModel,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        data.userHolding?.forEachIndexed { index, it ->
            item {
                if (it != null) {
                    UserHoldingItem(data = it)
                }
                if (index < (data.userHolding.lastIndex)) {
                    HorizontalDivider(Modifier.fillMaxWidth())
                }
            }
        }
    }
}


@Composable
fun UserHoldingItem(
    data: UserHolding,
) {
    val viewmodel: HoldingsViewModel = viewModel()

    Column(Modifier.padding(12.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                data.symbol?.let { Text(text = it.uppercase(), fontWeight = FontWeight.SemiBold) }
                if (Random.nextBoolean()) {
                    Text(
                        text = AppConstants.HOLDING_TAG,
                        color = Color.DarkGray,
                        fontSize = 8.sp,
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .background(
                                Color.LightGray
                            )
                            .padding(horizontal = 2.dp, vertical = 0.dp)
                    )
                }
            }
            data.ltp?.let {
                UserHoldingItemValue(key = AppConstants.LTP, value = it.formatCost())
            }
        }
        Row(
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.quantity?.let {
                UserHoldingItemValue(key = AppConstants.NET_QTY, value = it.toString())
            }
            viewmodel.calculateTotalPNL(data).let {
                UserHoldingItemValue(
                    key = AppConstants.PNL,
                    value = it,
                    valueColor = if (it.isNegative()) Color.Red else Color.Green
                )
            }
        }
    }
}

@Composable
fun UserHoldingItemValue(
    key: String,
    value: String,
    valueColor: Color? = null,
) {
    Text(buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Gray, fontSize = 8.sp, fontStyle = FontStyle.Italic
            )
        ) {
            append("$key:  ")
        }
        withStyle(
            style = SpanStyle(
                color = valueColor ?: Color.DarkGray,
                fontSize = 12.sp,
            )
        ) {
            append(value)
        }
    })
}