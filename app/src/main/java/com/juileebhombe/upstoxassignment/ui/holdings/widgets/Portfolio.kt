package com.juileebhombe.upstoxassignment.ui.holdings.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juileebhombe.upstoxassignment.ui.holdings.HoldingsViewModel
import com.juileebhombe.upstoxassignment.utils.AppConstants
import com.juileebhombe.upstoxassignment.utils.isNegative

@Composable
fun Portfolio(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val viewmodel: HoldingsViewModel = viewModel()

    Card(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                expanded = !expanded
            }) {
        Column(Modifier.padding(horizontal = 5.dp)) {
            AnimatedVisibility(
                visible = expanded, modifier = Modifier.animateContentSize()
            ) {
                Column {
                    viewmodel.calculateCurrentValue()
                        ?.let { PortfolioItem(AppConstants.CURRENT_VALUE, it) }
                    viewmodel.calculateTotalInvestment()?.let {
                        PortfolioItem(
                            AppConstants.TOTAL_INVESTMENT, it
                        )
                    }
                    viewmodel.calculateTodaySPNL()?.let {
                        PortfolioItem(
                            AppConstants.TODAY_S_PROFIT_N_LOSS,
                            it,
                            if (it.isNegative()) Color.Red else Color.Green
                        )
                    }
                }
            }
            HorizontalDivider()
            viewmodel.calculateTotalPNL()?.let {
                PortfolioItem(
                    AppConstants.PROFIT_N_LOSS, it, if (it.isNegative()) Color.Red else Color.Green
                ) {
                    if (expanded) Icon(
                        imageVector = AppConstants.EXPANDED_ICON,
                        contentDescription = AppConstants.EXPANDED_ICON_DESCRIPTION
                    ) else Icon(
                        imageVector = AppConstants.COLLAPSED_ICON,
                        contentDescription = AppConstants.COLLAPSED_ICON_DESCRIPTION
                    )
                }
            }

        }
    }
}


@Composable
fun PortfolioItem(
    key: String,
    value: String,
    valueColor: Color? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(text = key)
            icon?.let { it() }
        }
        Text(text = value, color = valueColor ?: Color.Black)
    }
}
