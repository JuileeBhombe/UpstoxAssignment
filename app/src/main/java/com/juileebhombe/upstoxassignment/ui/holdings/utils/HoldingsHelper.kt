package com.juileebhombe.upstoxassignment.ui.holdings.utils

import com.juileebhombe.upstoxassignment.data.model.UserHolding
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HoldingsHelper @Inject constructor() {
    fun calculateTotalPNL(holding: UserHolding): Double {
        return ((holding.ltp ?: 0.0).times(
            holding.quantity ?: 0
        )).minus((holding.avgPrice ?: 0.0).times(holding.quantity ?: 0))
    }

    fun calculateCurrentValue(holdings: List<UserHolding?>): Double {
        return holdings.sumOf { (it?.ltp ?: 0.0).times(it?.quantity ?: 0) }
    }

    fun calculateTotalInvestment(holdings: List<UserHolding?>): Double {
        return holdings.sumOf { (it?.avgPrice ?: 0.0).times(it?.quantity ?: 0) }
    }

    fun calculateTodaySPNL(holdings: List<UserHolding?>): Double {
        return holdings.sumOf {
            ((it?.close ?: 0.0).minus(it?.ltp ?: 0.0)).times(
                it?.quantity ?: 0
            )
        }
    }

    fun calculateTotalPNL(holdings: List<UserHolding?>): Double {
        return holdings.sumOf {
            if (it != null) {
                calculateTotalPNL(it)
            } else {
                0.0
            }
        }
    }
}
