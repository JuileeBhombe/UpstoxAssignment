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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
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
import androidx.compose.ui.unit.dp

@Composable
fun Portfolio(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }


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
                    PortfolioItem("Current Value*", "some value")
                    PortfolioItem("Total Investment*", "some value")
                    PortfolioItem("Today's Profit & Loss*", "some value")
                }
            }
            HorizontalDivider()
            PortfolioItem(
                "Profit & Loss*",
                "some value"
            ) {
                if (expanded) Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Portfolio Closed"
                ) else Icon(
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Portfolio Opened"
                )
            }

        }
    }
}


@Composable
fun PortfolioItem(key: String, value: String, icon: @Composable (() -> Unit)? = null) {
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
        Text(text = value)
    }
}
