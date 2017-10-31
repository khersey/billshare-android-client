package com.cleganeBowl2k18.trebuchet.presentation.common

/**
 * Created by khersey on 2017-10-25.
 */
class SplitUtil {
    companion object {
        fun equalSplit (amount: Long, userIds: List<Long>) : MutableMap<Long, Long> {
            val remainder : Long = amount % userIds.size
            val splitVal : Long = amount / userIds.size
            var paySplit: MutableMap<Long, Long> = (userIds.map{ id -> id to splitVal }.toMap() as MutableMap<Long, Long>)
            for (i in remainder downTo 1) {
                val id: Long = userIds[((i-1) as Int)]
                val money: Long = paySplit[id]!! + 1
                paySplit[id] = money
            }
            // TODO: validate
            return paySplit
        }
    }
}