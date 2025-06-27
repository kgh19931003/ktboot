import java.nio.file.Files
import java.nio.file.Paths
import java.io.IOException

fun deleteImageFile(filePath: String) {
    try {
        val path = Paths.get(filePath)
        if (Files.exists(path)) {
            Files.delete(path)
            println("파일 삭제 완료: $filePath")
        } else {
            println("파일이 존재하지 않음: $filePath")
        }
    } catch (e: IOException) {
        println("파일 삭제 실패: ${e.message}")
    }
}
