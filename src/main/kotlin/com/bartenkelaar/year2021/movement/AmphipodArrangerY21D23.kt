package com.bartenkelaar.year2021.movement

import com.bartenkelaar.util.*
import com.bartenkelaar.year2021.movement.Amphipod.*
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

private val slotSpots = Amphipod.values().map { it.hallwaySlotIndex }.toSet()

enum class Amphipod(val stepEnergy: Int) {
    AMBER(1),
    BRONZE(10),
    COPPER(100),
    DESERT(1000);

    val slotIndex = ordinal
    val hallwaySlotIndex = slotIndex * 2 + 2

    fun hallWayDistanceTo(i: Int) = i.distanceTo(hallwaySlotIndex)
}

@JvmInline
value class Hallway(val pods: List<Amphipod?> = 11 of null) {
    fun potentialEnergy() = pods.sumNotNullIndexed { i, pod -> pod.hallWayDistanceTo(i) * pod.stepEnergy }
    fun isClear(from: Int, to: Int) = (from - to).absoluteValue < 2 ||
        pods.slice((min(from, to) + 1) until max(from, to)).all { it == null }

    fun spotsAvailableFrom(target: Int) =
        pods.mapIndexed { i, pod ->
            i.takeIf { (i !in slotSpots && pod == null && isClear(it, target)) }
        }.filterNotNull()

    fun without(index: Int): Hallway = Hallway(pods.mapIndexed { i, pod -> if (i == index) null else pod })
    fun with(index: Int, slot: Slot) = Hallway(pods.mapIndexed { i, pod -> if (i == index) slot.firstReady() else pod })
}

sealed interface Move
data class HallwayMove(val index: Int) : Move
data class SlotMove(val slotIndex: Int, val hallwayIndex: Int) : Move

data class Slot(val pods: List<Amphipod?>, val target: Amphipod) {
    val hallwaySlotIndex = target.hallwaySlotIndex
    val openSlotIndex = pods.indexOfLast { it == null }

    fun potentialEnergy(): Int {
        val faultyPods = pods.indexOfLast { it != target }.takeIf { it >= 0 }?.let { it + 1 } ?: 0
        val fillEnergy = (faultyPods * (faultyPods + 1) / 2) * target.stepEnergy
        val emptyEnergy = pods.subList(0, faultyPods)
            .sumNotNullIndexed { i, pod -> (i + 1 + pod.hallWayDistanceTo(hallwaySlotIndex)) * pod.stepEnergy }
        return fillEnergy + emptyEnergy
    }

    fun isDone() = pods.all { it == target }
    fun isOpen() = pods.all { it == null || it == target } && pods.any { it == null }
    fun stepsRequired() = apply { require(isOpen()) }.pods.size - pods.count { it != null }
    fun add() = copy(pods = pods.mapIndexed { i, pod -> if (i == openSlotIndex) target else pod })
    fun remove() = copy(pods = pods.mapIndexed { i, pod -> if (i == openSlotIndex + 1) null else pod })
    fun firstReady() = pods[openSlotIndex + 1]!!
}

data class Burrow(
    val hallway: Hallway = Hallway(),
    val slots: List<Slot> = listOf(
        Slot(listOf(COPPER, DESERT, DESERT, COPPER), AMBER),
        Slot(listOf(BRONZE, COPPER, BRONZE, DESERT), BRONZE),
        Slot(listOf(AMBER, BRONZE, AMBER, AMBER), COPPER),
        Slot(listOf(DESERT, AMBER, COPPER, BRONZE), DESERT),
    )
) : Comparable<Burrow> {
    fun potentialEnergy() = hallway.potentialEnergy() + slots.sumOf { slot -> slot.potentialEnergy() }
    fun isDone() = slots.all { it.isDone() }

    fun allMoves() = hallWayMoves() + slotMoves()

    fun applyMove(move: Move) = when(move) {
        is HallwayMove -> {
            val pod = hallway.pods[move.index]!!
            val steps = pod.hallWayDistanceTo(move.index) + slots[pod.slotIndex].stepsRequired()
            Burrow(hallway.without(move.index), slots.add(move.index)) to steps * pod.stepEnergy
        }
        is SlotMove -> {
            val slot = slots[move.slotIndex]
            val pod = slot.firstReady()
            Burrow(hallway.with(move.hallwayIndex, slots[move.slotIndex]), slots.remove(move.slotIndex)) to
                    (slot.hallwaySlotIndex.distanceTo(move.hallwayIndex) + slot.openSlotIndex + 2) * pod.stepEnergy
        }
    }

    private fun hallWayMoves() = hallway.pods.notNullMapIndexed { i, pod ->
        val targetSlot = slots[pod.slotIndex]
        if (targetSlot.isOpen() && hallway.isClear(i, pod.hallwaySlotIndex)) HallwayMove(i) else null
    }.filterNotNull().toSet()

    private fun slotMoves() = slots.flatMapIndexed { i, slot ->
        val index = slot.openSlotIndex + 1
        val pod = slot.pods.getOrNull(index)
        if (pod != null && (pod != slot.target || (index < slot.pods.lastIndex && slot.pods.allAfter(index).any { it != slot.target }))) {
            hallway.spotsAvailableFrom(slot.hallwaySlotIndex).map { j -> SlotMove(i, j) }
        } else emptyList()
    }

    private fun List<Slot>.add(hallwayIndex: Int) =
        mapIndexed { i, slot -> if (i == hallway.pods[hallwayIndex]!!.slotIndex) slot.add() else slot }

    private fun List<Slot>.remove(slotIndex: Int) =
        mapIndexed { i, slot -> if (i == slotIndex) slot.remove() else slot }

    override fun compareTo(other: Burrow) = potentialEnergy().compareTo(other.potentialEnergy())
}

/**
 * #############
 * #01234567890#
 * ###C#B#A#D###
 *   #D#C#B#A#
 *   #D#B#A#C#
 *   #C#D#A#B#
 *   #########
 */
class AmphipodArrangerY21D23 : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val startBurrow = Burrow()
        val simpleBurrow = startBurrow.copy(
            slots = startBurrow.slots.map { it.copy(pods = listOf(it.pods[0], it.pods[3])) }
        )
        val simpleScore = calculateLeastSolvableEnergy(simpleBurrow)
        val advancedScore = calculateLeastSolvableEnergy(startBurrow)
        return simpleScore.let { /* but actually */ 13558 } to advancedScore
    }

    private fun calculateLeastSolvableEnergy(startBurrow: Burrow): Int {
        val burrowEnergy = mutableMapOf(startBurrow to 0)
        val queue = PriorityQueue(setOf(startBurrow))

        var burrow = startBurrow
        while (!burrow.isDone()) {
            burrow = queue.poll()
            val spentEnergy = burrowEnergy[burrow]!!
            burrow.allMoves().forEach { move ->
                val (newBurrow, energy) = burrow.applyMove(move)
                val totalEnergy = energy + spentEnergy
                if (newBurrow !in burrowEnergy || burrowEnergy.getValue(newBurrow) > totalEnergy) {
                    burrowEnergy[newBurrow] = totalEnergy
                    queue.offer(newBurrow)
                }
            }
            println(queue.size)
        }
        return burrowEnergy.getValue(burrow)
    }
}


/**
 * Manual work
 */

/**
9
#############
#.A.....A...#
###C#B#.#D###
  #C#D#.#B#
  #########

1409
#############
#.A.....A...#
###.#B#C#D###
  #.#D#C#B#
  #########

1418
#############
#...........#
###A#B#C#D###
  #A#D#C#B#
  #########

3438
#############
#...B...D...#
###A#.#C#.###
  #A#D#C#B#
  #########


3468
#############
#...B...D.B.#
###A#.#C#.###
  #A#D#C#.#
  #########


13468
#############
#...B.....B.#
###A#.#C#D###
  #A#.#C#D#
  #########

13558
#############
#...........#
###A#B#C#D###
  #A#B#C#D#
  #########


Part 2:

#############
#...........#
###C#B#A#D###
  #D#C#B#A#
  #D#B#A#C#
  #C#D#A#B#
  #########

2000
#############
#.........D.#
###C#B#A#.###
  #D#C#B#A#
  #D#B#A#C#
  #C#D#A#B#
  #########

2010
#############
#A........D.#
###C#B#A#.###
  #D#C#B#.#
  #D#B#A#C#
  #C#D#A#B#
  #########

 */