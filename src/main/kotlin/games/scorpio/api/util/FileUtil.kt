package games.scorpio.api.util

import java.io.*
import java.lang.Exception
import java.util.stream.Collectors

object FileUtil {

    fun read(file: File, unit: (BufferedReader) -> Unit) {
        try {
            val reader = BufferedReader(FileReader(file))
            unit.invoke(reader)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun write(str: String, file: File) {
        try {
            val writer = BufferedWriter(FileWriter(file))
            writer.write(str)
            writer.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun copy(copy: File, copyTo: File) {
        read(copy) {
            write(it.lines().collect(Collectors.joining("\n")), copyTo)
        }
    }

}