package com.bartenkelaar

import com.bartenkelaar.util.readFile
import com.bartenkelaar.year2015.delivery.PackageCounter
import com.bartenkelaar.year2020.passports.PassportChecker
import com.bartenkelaar.year2015.elevators.BracketMover
import com.bartenkelaar.year2015.packaging.WrappingCalculator
import com.bartenkelaar.year2020.adapters.JoltChecker
import com.bartenkelaar.year2020.boarding.BusFinder
import com.bartenkelaar.year2020.boarding.SeatFinder
import com.bartenkelaar.year2020.boarding.TicketTranslator
import com.bartenkelaar.year2020.customs.CustomsFormsCounter
import com.bartenkelaar.year2020.encryption.XmasBreaker
import com.bartenkelaar.year2020.expenses.AddUpResolver
import com.bartenkelaar.year2020.expenses.SumChecker
import com.bartenkelaar.year2020.gaming.GameRunner
import com.bartenkelaar.year2020.gaming.Memory
import com.bartenkelaar.year2020.luggage.LuggageChecker
import com.bartenkelaar.year2020.math.ExpressionSolver
import com.bartenkelaar.year2020.navigation.DockCommunicator
import com.bartenkelaar.year2020.navigation.FerrySailer
import com.bartenkelaar.year2020.navigation.TreeFinder
import com.bartenkelaar.year2020.passwords.PasswordChecker
import com.bartenkelaar.year2020.power.ConwayPower
import com.bartenkelaar.year2020.seats.ConwaySeating

interface Solver {
    fun solve(input: List<String>): Pair<Number, Number>
}

private val solvers2015 = listOf(
    BracketMover(),
    WrappingCalculator(),
    PackageCounter(),
)

private val solvers2020 = listOf(
    AddUpResolver(SumChecker()),
    PasswordChecker(),
    TreeFinder(),
    PassportChecker(),
    SeatFinder(),
    CustomsFormsCounter(),
    LuggageChecker(),
    GameRunner(),
    XmasBreaker(SumChecker()),
    JoltChecker(),
    ConwaySeating(),
    FerrySailer(),
    BusFinder(),
    DockCommunicator(),
    Memory(2020),
    TicketTranslator(),
    ConwayPower(),
    ExpressionSolver(),
)

fun main() {
    2015.printSolutions(solvers2015)
    2020.printSolutions(solvers2020)
}

private fun Int.printSolutions(solvers: List<Solver>) {
    println("Solutions for year $this\n")
    solvers.forEachIndexed { i, solver ->
        val day = i + 1
        val input = solver.readFile("day$day.txt", this)
        println("Day $day: ${solver.solve(input)}")
    }
    println("=======================\n")
}

