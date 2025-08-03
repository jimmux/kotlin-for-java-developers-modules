package rationals

import java.math.BigInteger
import kotlin.toBigInteger

class Rational(val numerator: BigInteger, val denominator: BigInteger) {
    operator fun plus(other: Rational): Rational {
        val (a, b) = this.common(other)

        return Rational(
            a.numerator + b.numerator,
            a.denominator
        )
    }

    operator fun minus(other: Rational): Rational {
        val (a, b) = this.common(other)

        return Rational(
            a.numerator - b.numerator,
            a.denominator
        )
    }

    operator fun times(other: Rational): Rational = Rational(
        this.numerator * other.numerator,
        this.denominator * other.denominator
    )

    operator fun div(other: Rational): Rational = Rational(
        this.numerator * other.denominator,
        this.denominator * other.numerator
    )

    operator fun unaryMinus(): Rational = Rational(
        -numerator,
        denominator
    )

    override operator fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Rational) return false

        return this.compareTo(other) == 0
    }

    operator fun compareTo(other: Rational): Int {
        val (a, b) = common(other)
        return (a.numerator).compareTo(b.numerator)
    }

    operator fun rangeTo(other: Rational) = Pair(this, other)

    override fun toString(): String {
        val normalized = this.normalized()

        if (normalized.denominator == BigInteger.ONE) {
            return normalized.numerator.toString()
        }

        return "${normalized.numerator}/${normalized.denominator}"
    }

    fun normalized(): Rational {
        var gcd = denominator.gcd(numerator)
        if (denominator < BigInteger.ZERO) {
            gcd *= -BigInteger.ONE
        }
        return Rational(numerator / gcd, denominator / gcd)
    }

    fun common(other: Rational): Pair<Rational, Rational> {
        val d = this.denominator * other.denominator
        return Pair(
            Rational(this.numerator * other.denominator, d),
            Rational(other.numerator * this.denominator, d)
        )
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }
}

operator fun Pair<Rational, Rational>.contains(r: Rational): Boolean =
    this.first <= r && r <= this.second

infix fun Int.divBy(denominator: Int) =
    Rational(this.toBigInteger(), denominator.toBigInteger())
infix fun Long.divBy(denominator: Long): Rational =
    Rational(this.toBigInteger(), denominator.toBigInteger())
infix fun BigInteger.divBy(denominator: BigInteger): Rational =
    Rational(this, denominator)

fun String.toRational(): Rational {
    val parts = this.split("/").map(String::toBigInteger)
    val numerator = parts.getOrElse(0, { BigInteger.ZERO})
    val denominator = parts.getOrElse(1, {BigInteger.ONE})

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