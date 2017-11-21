package com.cleganeBowl2k18.trebuchet.presentation.common

/**
 * Created by khersey on 2017-10-25.
 */
class SplitUtil {
    companion object {
        fun equalSplit(amount: Long, userIds: List<Long>) : MutableMap<Long, Long> {
            var remainder : Long = 0
            var splitVal : Long = 0
            if (amount != 0.toLong()) {
                remainder = amount % userIds.size
                splitVal = amount / userIds.size
            }
            var paySplit: MutableMap<Long, Long> = (userIds.map{ id -> id to splitVal }.toMap() as MutableMap<Long, Long>)
            for (i in remainder downTo 1) {
                val id: Long = userIds[(i-1).toInt()]
                val money: Long = paySplit[id]!! + 1
                paySplit[id] = money
            }
            // TODO: validate
            return paySplit
        }
    }
}