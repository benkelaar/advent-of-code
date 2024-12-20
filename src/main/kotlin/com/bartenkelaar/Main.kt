package com.bartenkelaar

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.readFile
import com.bartenkelaar.year2015.baking.CookieBaker
import com.bartenkelaar.year2015.baking.EggNogStore
import com.bartenkelaar.year2015.baking.ReindeerMedicineBaker
import com.bartenkelaar.year2015.classification.AuntSueSelector
import com.bartenkelaar.year2015.classification.NaughtyNiceClassifier
import com.bartenkelaar.year2015.classification.NumberCounter
import com.bartenkelaar.year2015.code.JsonCounter
import com.bartenkelaar.year2015.code.PasswordIncrementer
import com.bartenkelaar.year2015.code.RpgFighter
import com.bartenkelaar.year2015.code.StringCounter
import com.bartenkelaar.year2015.delivery.ElfDeliveryCounter
import com.bartenkelaar.year2015.delivery.PackageCounter
import com.bartenkelaar.year2015.delivery.RoutePlanner
import com.bartenkelaar.year2015.electronics.BitwiseCircuitryEmulator
import com.bartenkelaar.year2015.electronics.LightAnimator
import com.bartenkelaar.year2015.electronics.LightInstructionRunner
import com.bartenkelaar.year2015.mining.AdventCoinMiner
import com.bartenkelaar.year2015.movement.BracketMover
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
import com.bartenkelaar.year2020.passports.PassportChecker
import com.bartenkelaar.year2020.passwords.PasswordChecker
import com.bartenkelaar.year2020.power.ConwayPower
import com.bartenkelaar.year2020.seats.ConwaySeating
import com.bartenkelaar.year2021.SeaCucumberMoverYear21Day25
import com.bartenkelaar.year2021.Y21D24
import com.bartenkelaar.year2021.communication.BitsDecoder
import com.bartenkelaar.year2021.entertainment.DiracDice
import com.bartenkelaar.year2021.entertainment.SquidBingo
import com.bartenkelaar.year2021.materials.PolymerCreation
import com.bartenkelaar.year2021.measurement.BeaconScanner
import com.bartenkelaar.year2021.measurement.DepthIncreaseMeasurer
import com.bartenkelaar.year2021.measurement.DiagnosticsReport
import com.bartenkelaar.year2021.measurement.HydroMeasurer
import com.bartenkelaar.year2021.measurement.InstructionOrigami
import com.bartenkelaar.year2021.measurement.OceanFloorMapper
import com.bartenkelaar.year2021.measurement.OctoFlashCounter
import com.bartenkelaar.year2021.measurement.ProbeLauncher
import com.bartenkelaar.year2021.measurement.SignalReader
import com.bartenkelaar.year2021.measurement.SnailfishMath
import com.bartenkelaar.year2021.movement.AmphipodArrangerY21D23
import com.bartenkelaar.year2021.movement.ChitonAvoidance
import com.bartenkelaar.year2021.movement.CrabAlignment
import com.bartenkelaar.year2021.movement.NavigationFixer
import com.bartenkelaar.year2021.movement.PathNavigator
import com.bartenkelaar.year2021.movement.ReactorStarter
import com.bartenkelaar.year2021.movement.SmokeAvoidance
import com.bartenkelaar.year2021.movement.SubmarineMover
import com.bartenkelaar.year2021.simulation.LanternFishGrowth
import com.bartenkelaar.year2022.calories.CalorieCounter
import com.bartenkelaar.year2022.cleaning.SectionDeterminer
import com.bartenkelaar.year2022.coms.CathodeRaySignal
import com.bartenkelaar.year2022.coms.DeviceTuner
import com.bartenkelaar.year2022.coms.DistressSignalDecoder
import com.bartenkelaar.year2022.coms.FileTree
import com.bartenkelaar.year2022.coms.SignalFinder
import com.bartenkelaar.year2022.coms.TreeFile
import com.bartenkelaar.year2022.gaming.MonkeyBusiness
import com.bartenkelaar.year2022.gaming.RockPaperScissorsGuide
import com.bartenkelaar.year2022.packing.HanoiStacking
import com.bartenkelaar.year2022.packing.RucksackPacker
import com.bartenkelaar.year2022.physics.RopeSimulation
import com.bartenkelaar.year2023.farming.AlmanacReader
import com.bartenkelaar.year2023.farming.OasisEnvironmentPredictor
import com.bartenkelaar.year2023.gaming.BoatRacer
import com.bartenkelaar.year2023.gaming.CamelCardPlayer
import com.bartenkelaar.year2023.gaming.CubeConundrum
import com.bartenkelaar.year2023.gaming.ScratchCardCounter
import com.bartenkelaar.year2023.machinery.CalibrationDecoder
import com.bartenkelaar.year2023.machinery.EngineSchematicReader
import com.bartenkelaar.year2023.machinery.LensProductionInitializer
import com.bartenkelaar.year2023.machinery.MirrorBender
import com.bartenkelaar.year2023.machinery.SpringRepair
import com.bartenkelaar.year2023.navigation.DesertMapReader
import com.bartenkelaar.year2023.navigation.PipeLooper
import com.bartenkelaar.year2023.navigation.ReflectionFinder
import com.bartenkelaar.year2023.science.GalaxyObserver
import com.bartenkelaar.year2024.communication.AntennaTuner
import com.bartenkelaar.year2024.gaming.CrossMas
import com.bartenkelaar.year2024.gaming.HikePathFinder
import com.bartenkelaar.year2024.gaming.StoneBlinker
import com.bartenkelaar.year2024.history.LocationFinder
import com.bartenkelaar.year2024.operation.BridgeCalibration
import com.bartenkelaar.year2024.operation.DiskDefragmenter
import com.bartenkelaar.year2024.operation.LabGuarder
import com.bartenkelaar.year2024.operation.MullComputer
import com.bartenkelaar.year2024.operation.NuclearReportAssessor
import com.bartenkelaar.year2024.printing.PaperSorter
import com.bartenkelaar.year2024.production.PlantFencing
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit.MILLIS

private val solvers2015 =
    listOf(
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
        PasswordIncrementer().disabled(),
        JsonCounter(),
        SeatingArranger().disabled(),
        ReindeerRacer(),
        CookieBaker(),
        AuntSueSelector(),
        EggNogStore(),
        LightAnimator(),
        ReindeerMedicineBaker(),
        ElfDeliveryCounter().disabled(),
        RpgFighter(),
    )

private val solvers2020 =
    listOf(
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
        DoorEncryption(),
    )

private val solvers2021 =
    listOf(
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
        PathNavigator().disabled(),
        InstructionOrigami(),
        PolymerCreation(),
        ChitonAvoidance().disabled(),
        BitsDecoder(),
        ProbeLauncher(),
        SnailfishMath(),
        BeaconScanner().disabled(),
        OceanFloorMapper().disabled(),
        DiracDice(),
        ReactorStarter(),
        AmphipodArrangerY21D23().disabled(),
        Y21D24(),
        SeaCucumberMoverYear21Day25(),
    )

private val solvers2022: List<Solver> =
    listOf(
        CalorieCounter(),
        RockPaperScissorsGuide(),
        RucksackPacker(),
        SectionDeterminer(),
        HanoiStacking(),
        DeviceTuner(),
        FileTree(),
        TreeFile(),
        RopeSimulation(),
        CathodeRaySignal(),
        MonkeyBusiness(),
        SignalFinder(),
        DistressSignalDecoder(),
    )

private val solvers2023: List<Solver> =
    listOf(
        CalibrationDecoder(),
        CubeConundrum(),
        EngineSchematicReader(),
        ScratchCardCounter(),
        AlmanacReader(),
        BoatRacer(),
        CamelCardPlayer(),
        DesertMapReader(),
        OasisEnvironmentPredictor(),
        PipeLooper(),
        GalaxyObserver(),
        SpringRepair(),
        ReflectionFinder(),
        MirrorBender(),
        LensProductionInitializer(),
    )

private val solvers2024: List<Solver> =
    listOf(
        LocationFinder(),
        NuclearReportAssessor(),
        MullComputer(),
        CrossMas(),
        PaperSorter(),
        LabGuarder().disabled(),
        BridgeCalibration().disabled(),
        AntennaTuner(),
        DiskDefragmenter(),
        HikePathFinder(),
        StoneBlinker(),
        PlantFencing(),
    )

fun main() {
//    2015.printSolutions(solvers2015)
//    2020.printSolutions(solvers2020)
//    2021.printSolutions(solvers2021)
//    2022.printSolutions(solvers2022)
    2023.printSolutions(solvers2023)
    2024.printSolutions(solvers2024)
}

private fun Int.printSolutions(solvers: List<Solver>) {
    println("Solutions for year $this\n")
    solvers.forEachIndexed { i, solver ->
        val day = i + 1
        val input = solver.readFile("day$day.txt", this)
        val startTime = OffsetDateTime.now()
        val result = solver.maybeSolve(input)
        val duration = startTime.until(OffsetDateTime.now(), MILLIS)
        println("Day $day: $result in $duration milliseconds")
    }
    println("=======================\n")
}
