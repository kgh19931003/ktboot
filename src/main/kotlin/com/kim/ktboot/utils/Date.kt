import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun nowAsRegularFormat(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}


fun nowAsTimestamp(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val base = now.format(formatter)
    val micro = (now.nano / 1000).toString().padStart(6, '0')  // 나노초 → 마이크로초 (0~999999)
    return base + micro
}