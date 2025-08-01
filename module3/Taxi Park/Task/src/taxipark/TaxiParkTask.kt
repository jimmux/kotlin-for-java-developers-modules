package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {
    val activeDrivers = trips.map { it.driver }.toSet()
    return allDrivers.minus(activeDrivers)
}

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers
        .filter { p -> trips.count { it.passengers.contains(p) } >= minTrips }
        .toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    return trips
        .filter { it.driver == driver }
        .flatMap { it.passengers }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .keys
}

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers
        .filter { p ->
            val passengerTrips = trips.filter { it.passengers.contains(p) }
            val discountedCount = passengerTrips.count { it.discount != null }
            discountedCount > (passengerTrips.size / 2)
        }
        .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? =
    trips
        .map {
            val tens = Math.floorDiv(it.duration, 10) * 10
            tens .. (tens + 9)
        }
        .groupingBy { it }
        .eachCount()
        .maxByOrNull { it.value }
        ?.key

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val eightyPercentOfAllIncome = trips.sumOf { it.cost } * 0.8

    if (eightyPercentOfAllIncome == 0.0) return false

    val numDriversInTopTwentyPercent = (allDrivers.size * 0.2).toInt()
    val topTwentyPercentIncome = allDrivers.associateWith { d ->
            trips
                .filter { it.driver == d }
                .sumOf { it.cost }
        }
        .toList()
        .sortedByDescending { it.second }
        .slice(0 until numDriversInTopTwentyPercent)
        .sumOf { it.second }

    return topTwentyPercentIncome >= eightyPercentOfAllIncome
}
