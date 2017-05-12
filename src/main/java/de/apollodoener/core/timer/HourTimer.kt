package de.apollodoener.core.timer

import java.util.*

/**
 * Created by raffael on 24.01.17.
 */

class HourTimer : Thread() {

    private var hourTimer: HashMap<HourTimerInterval, Int> = HashMap()

    override fun run() {
        while (!this.isInterrupted) {
            sleep(60 * 60 * 1000)
            for ((interval, runs) in hourTimer) {
                var newRuns = runs
                newRuns++
                if (interval.getInterval() == newRuns) {
                    interval.run()
                    newRuns = 0
                }
                hourTimer.set(interval, newRuns)
            }
        }
    }

    fun registerTimer(hourTimerInterval: HourTimerInterval) {
        hourTimer.put(hourTimerInterval, 0)
    }
}