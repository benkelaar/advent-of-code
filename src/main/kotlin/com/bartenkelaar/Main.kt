package com.bartenkelaar

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.readFile
import com.bartenkelaar.year2015.baking.CookieBaker
import com.bartenkelaar.year2015.classification.NaughtyNiceClassifier
import com.bartenkelaar.year2015.classification.NumberCounter
import com.bartenkelaar.year2015.code.JsonCounter
import com.bartenkelaar.year2015.code.PasswordIncrementer
import com.bartenkelaar.year2015.code.StringCounter
import com.bartenkelaar.year2015.delivery.PackageCounter
import com.bartenkelaar.year2015.delivery.RoutePlanner
import com.bartenkelaar.year2015.electronics.BitwiseCircuitryEmulator
import com.bartenkelaar.year2020.passports.PassportChecker
import com.bartenkelaar.year2015.movement.BracketMover
import com.bartenkelaar.year2015.electronics.LightInstructionRunner
import com.bartenkelaar.year2015.mining.AdventCoinMiner
import com.bartenkelaar.year2015.movement.ReindeerRacer
import com.bartenkelaar.year2015.organization.SeatingArranger
import com.bartenkelaar.year2015.packaging.WrappingCalculator
import com.bartenkelaar.year2020.adapters.JoltChecker
import com.bartenkelaar.year2020.boarding.BusFinder
import com.bartenkelaar.year2020.boarding.SeatFinder
import com.bartenkelaar.year2020.boarding.TicketTranslator
import com.bartenkelaar.year2020.comunication.AllergenFinder
import com.bartenkelaar.year2020.comunication.ImageRebuilder
import com.bartenkelaar.year2020.comunication.MessageMatcher
import com.bartenkelaar.year2020.construction.FloorPatternBuilder
import com.bartenkelaar.year2020.customs.CustomsFormsCounter
import com.bartenkelaar.year2020.encryption.DoorEncryption
import com.bartenkelaar.year2020.encryption.XmasBreaker
import com.bartenkelaar.year2020.expenses.AddUpResolver
import com.bartenkelaar.year2020.expenses.SumChecker
import com.bartenkelaar.year2020.gaming.Combat
import com.bartenkelaar.year2020.gaming.Cups
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
import com.bartenkelaar.year2021.entertainment.SquidBingo
import com.bartenkelaar.year2021.measurement.*
import com.bartenkelaar.year2021.movement.*
import com.bartenkelaar.year2021.simulation.LanternFishGrowth

private val solvers2015 = listOf(
    BracketMover(),
    WrappingCalculator(),
    PackageCounter(),
    AdventCoinMiner().disabled(),
    NaughtyNiceClassifier(),
    LightInstructionRunner(),
    BitwiseCircuitryEmulator(),
    StringCounter(),
    RoutePlanner(),
    NumberCounter().disabled(),
    PasswordIncrementer(),
    JsonCounter(),
    SeatingArranger(),
    ReindeerRacer(),
    CookieBaker()
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
    ConwaySeating().disabled(),
    FerrySailer(),
    BusFinder(),
    DockCommunicator(),
    Memory(2020),
    TicketTranslator(),
    ConwayPower().disabled(),
    ExpressionSolver(),
    MessageMatcher(),
    ImageRebuilder(),
    AllergenFinder(),
    Combat(),
    Cups(),
    FloorPatternBuilder(),
    DoorEncryption()
)

private val solvers2021 = listOf(
    DepthIncreaseMeasurer(),
    SubmarineMover(),
    DiagnosticsReport(),
    SquidBingo(),
    HydroMeasurer(),
    LanternFishGrowth(),
    CrabAlignment(),
    SignalReader(),
    SmokeAvoidance(),
    NavigationFixer(),
    OctoFlashCounter(),
    PathNavigator(),
)

fun main() {
//    2015.printSolutions(solvers2015)
//    2020.printSolutions(solvers2020)
    2021.printSolutions(solvers2021)
}

private fun Int.printSolutions(solvers: List<Solver>) {
    println("Solutions for year $this\n")
    solvers.forEachIndexed { i, solver ->
        val day = i + 1
        val input = solver.readFile("day$day.txt", this)
        println("Day $day: ${solver.maybeSolve(input)}")
    }
    println("=======================\n")
}

