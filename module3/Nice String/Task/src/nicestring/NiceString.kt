package nicestring

fun String.isNice(): Boolean {
    return setOf(
        ::noBadSubs,
        ::threeVowels,
        ::hasDouble
    ).count { it(this) } >= 2
}

fun noBadSubs(s: String): Boolean {
    return setOf("bu", "ba", "be").none { s.contains(it) }
}

fun threeVowels(s: String): Boolean {
    return s.count { it in "aeiou" } >= 3
}

fun hasDouble(s: String): Boolean {
    return s.zipWithNext().any { it.first == it.second }
}
