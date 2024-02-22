package cloud.tyty.unca

import java.util.concurrent.TimeUnit

fun timestampConversion(timestamp: Long): String {
    val currentTimeMillis = System.currentTimeMillis()
    val diffMillis = currentTimeMillis - timestamp

    val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
    val days = TimeUnit.MILLISECONDS.toDays(diffMillis)

    return when {
        seconds < 60 -> "now"
        minutes < 60 -> "$minutes min ago"
        hours < 24 -> "$hours hr ago"
        days < 7 -> "$days day ago"
        else -> "more than a week ago"
    }
}