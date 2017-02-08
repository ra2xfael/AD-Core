package de.tigifan.core.bukkit.timer

/**
 * Created by raffael on 24.01.17.
 */

interface HourTimerInterval : Runnable {

    fun getInterval(): Int
}