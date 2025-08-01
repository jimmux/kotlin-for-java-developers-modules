package rationals

import java.math.BigInteger

class Rational(val numerator: Long, val denominator: Long) {
    operator fun plus(other: Rational): Rational = Rational(
        this.numerator + other.numerator,
        this.denominator
    )

    operator fun minus(other: Rational): Rational = Rational(
        this.numerator - other.numerator,
        this.denominator
    )

    operator fun times(other: Rational): Rational = Rational(
        this.numerator * other.numerator,
        this.denominator * other.denominator
    )

    operator fun div(other: Rational): Rational = Rational(
        this.numerator * other.numerator,
        this.denominator * other.denominator
    )

    operator fun unaryMinus(): Rational = Rational(
        -this.numerator,
        this.denominator
    )

    operator fun compareTo(other: Rational): Int =
        (this.numerator * other.denominator).compareTo(other.numerator * this.denominator)

    operator fun rangeTo(other: Rational) = Pair(this, other)

    override fun toString() = "${this.numerator}/${this.denominator}"
}

operator fun Pair<Rational, Rational>.contains(r: Rational): Boolean =
    this.first <= r && r <= this.second

infix fun Int.divBy(denominator: Int) =
    Rational(this.toLong(), denominator.toLong())
infix fun Long.divBy(denominator: Long): Rational =
    Rational(this, denominator)
infix fun BigInteger.divBy(denominator: BigInteger): Rational =
    Rational(this.toLong(), denominator.toLong())

fun String.toRational(): Rational {
    val (numerator, denominator) = this.split("/").map(String::toLong)
    return Rational(numerator, denominator)
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}