package com.juileebhombe.upstoxassignment.ui.holdings.utils

import com.juileebhombe.upstoxassignment.data.local.entity.Holding
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class HoldingsHelper @Inject constructor() {
    fun calculateTotalPNL(holding: Holding): Double {
        return ((holding.ltp ?: 0.0).times(
            holding.quantity ?: 0
        )).minus((holding.avgPrice ?: 0.0).times(holding.quantity ?: 0))
    }

    fun calculateCurrentValue(holdings: List<Holding?>): Double {
        return holdings.sumOf { (it?.ltp ?: 0.0).times(it?.quantity ?: 0) }
    }

    fun calculateTotalInvestment(holdings: List<Holding?>): Double {
        return holdings.sumOf { (it?.avgPrice ?: 0.0).times(it?.quantity ?: 0) }
    }

    fun calculateTodaySPNL(holdings: List<Holding?>): Double {
        return holdings.sumOf {
            ((it?.close ?: 0.0).minus(it?.ltp ?: 0.0)).times(
                it?.quantity ?: 0
            )
        }
    }

    fun calculateTotalPNL(holdings: List<Holding?>): Double {
        return holdings.sumOf {
            if (it != null) {
                calculateTotalPNL(it)
            } else {
                0.0
            }
        }
    }

    fun calculatePercentagePNL(holdings: List<Holding?>): Double {
        return (calculateTotalPNL(holdings) / calculateTotalInvestment(holdings)) * 100
    }
}
