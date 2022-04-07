package moe.neat.tbdutils.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

@Serializable
data class Pixel(val r: Int, val g: Int, val b: Int, val a: Int)
@Serializable
data class ImageData(val name: String, val width: Int, val height: Int, val data: List<List<Pixel>>)


@OptIn(ExperimentalSerializationApi::class)
class Image(file: File) {

    init {
        val data = Json.decodeFromStream<ImageData>(file.inputStream())
    }

}