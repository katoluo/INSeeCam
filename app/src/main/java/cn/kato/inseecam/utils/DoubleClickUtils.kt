package cn.kato.inseecam.utils

import android.os.SystemClock

/**
 * time :   2022-01-01
 */
object DoubleClickUtils {
    private val TIME_ARRAY: LongArray = LongArray(2)

    fun isOnDoubleClick(time: Long = 1500): Boolean {
        System.arraycopy(TIME_ARRAY, 1, TIME_ARRAY, 0, TIME_ARRAY.size - 1);
        TIME_ARRAY[TIME_ARRAY.size - 1] = SystemClock.uptimeMillis();
        return TIME_ARRAY[0] >= (SystemClock.uptimeMillis() - time)
    }
}