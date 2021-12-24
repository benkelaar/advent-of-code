package com.bartenkelaar.year2015.baking

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.nonBlank
import java.util.*

private data class Medicine(val molecule: String, val matchIndex: Int, val replacements: Int) {
    fun matchScore(target: String) = matchIndex * 10 + (target.length - molecule.length) - replacements
}

class ReindeerMedicineBaker : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val replacements = input.filter { "=>" in it }
            .map { it.split(" => ") }
            .groupBy { it.first() }
            .mapValues { (_, rules) -> rules.map { it[1] } }
        val molecule = input.nonBlank().last()

        val uniqueMolecules = replacements.flatMap { (f, ts) ->
            ts.flatMap { t -> f.toRegex().findAll(molecule).map { molecule.replaceRange(it.range, t) } }
        }.toSet().size

        val replacementsNecessary = replacements.searchFor(molecule)

        return uniqueMolecules to replacementsNecessary
    }

    private fun Map<String, List<String>>.searchFor(molecule: String): Int {
        val initial = Medicine("e", 0, 0)
        val queue = PriorityQueue<Medicine> { o1, o2 -> o1.matchScore(molecule).compareTo(o2.matchScore(molecule)) }
        queue.offer(initial)

        while (true) {
//            val closest = queue.poll()
//            var index = closest.matchIndex
            return 0
//            val
        }
    }
}

/**
 * Manual exploration
 *
 target
CRnSiRnCaPTiMgYCaPTiRnFArSiThFArCaSiThSiThPBCaCaSiRnSiRnTiTiMgArPBCaPMgYPTiRnFArFArCaSiRnBPMgArPRnCaPTiRnFArCaSiThCaCaFArPBCaCaPTiTiRnFArCaSiRnSiAlYSiThRnFArArCaSiRnBFArCaCaSiRnSiThCaCaCaFYCaPTiBCaSiThCaSiThPMgArSiRnCaPBFYCaCaFArCaCaCaCaSiThCaSiRnPRnFArPBSiThPRnFArSiRnMgArCaFYFArCaSiRnSiAlArTiTiTiTiTiTiTiRnPMgArPTiTiTiBSiRnSiAlArTiTiRnPMgArCaFYBPBPTiRnSiRnMgArSiThCaFArCaSiThFArPRnFArCaSiRnTiBSiThSiRnSiAlYCaFArPRnFArSiThCaFArCaCaSiThCaCaCaSiRnPRnCaFArFYPMgArCaPBCaPBSiRnFYPBCaFArCaSiAl

 **** Options ***
HF
  xCRnAlArF
  CrnTh...
  CRnFYFYFArF
    xCRnSiAlYFYFArF
      CRnSiTh...
    CRnCaFYFYFArF
      CRnSiRnFYFArFYFYFArF
        CRnSiRnCaFYFArFYFYFArF
          CRnSiRnCaPMgYFArFYFYFArF
            CRnSiRnCaPTiMgYFArFYFYFArF
              CRnSiRnCaPTiMgYCaFArFYFYFArF
      CRnSiRnMgArFYFYFArF
    CRnPMgYFYFArF
  CRnFYMgArF
  CRnMgYFArF
NAl
  CRnFArAl
OMg
  CRnFYFArMg
  CRnMgArMg

 */