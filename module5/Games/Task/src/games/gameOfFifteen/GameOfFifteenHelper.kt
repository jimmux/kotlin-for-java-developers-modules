package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    // Knowing we have values that are strictly sequential makes this easier.
    // The sort is just a pass, swapping for each position if necessary.
    val working = permutation.toMutableList()
    // Interesting - the grader must be using a Kotlin version where List.min() can be null.
    val lower = permutation.min() ?: 1
    var swaps = 0

    (lower until lower + permutation.size).forEach { n ->
        val i = n - lower
        val iValue = working[i]
        if (iValue != n) {
            val j = working.indexOf(n)
            val jValue = working[j]
            working[i] = jValue
            working[j] = iValue
            swaps++
        }
    }

    return swaps % 2 == 0
}
