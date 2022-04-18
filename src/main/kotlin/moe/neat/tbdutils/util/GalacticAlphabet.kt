package moe.neat.tbdutils.util

object GalacticAlphabet {
    private val alphabet = mutableMapOf(
        "a" to "ᔑ",
        "b" to "ʖ",
        "c" to "ᓵ",
        "d" to "↸", // weird
        "e" to "ᒷ",
        "f" to "⎓",
        "g" to "⊣",
        "h" to "⍑",
        "i" to "╎",
        "j" to "⋮",
        "k" to "ꖌ",
        "l" to "ꖎ",
        "m" to "ᒲ",
        "n" to "リ",
        "o" to "\uD835\uDE79", // bruh
        "p" to "!¡", // double char
        "q" to "ᑑ",
        "r" to "∷",
        "s" to "ᓭ",
        "t" to "ℸ ̣",
        "u" to "⚍",
        "v" to "⍊",
        "w" to "∴",
        "x" to "/",
        "y" to "\\|\\|", // double char
        "z" to "⨅",
    )

    fun translateToGalactic(text: String): String {
        return text.map {
            return@map alphabet[it.lowercase()] ?: it
        }.joinToString("")
    }
}
