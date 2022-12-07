package com.bartenkelaar.year2022.system

import com.bartenkelaar.util.Solver
import com.bartenkelaar.util.isPositiveNumber
import com.bartenkelaar.util.nonBlank

sealed class FileObject(val name: String, val parent: Dir?) {
    abstract fun size(): Long
}

class File(name: String, parent: Dir, private val size: Int) : FileObject(name, parent) {
    override fun size() = size.toLong()
}
class Dir(name: String, parent: Dir?) : FileObject(name, parent) {
    private val contents: MutableList<FileObject> = mutableListOf()

    override fun size() = contents.sumOf { it.size() }

    fun add(file: FileObject) {
        contents.add(file)
    }

    fun find(dirName: String): Dir = contents.first { it.name == dirName } as Dir

    fun toDirList(): List<Dir> = listOf(this) + contents.filterIsInstance<Dir>().flatMap { it.toDirList() }
}

class FileTree : Solver() {
    override fun solve(input: List<String>): Pair<Any, Any> {
        val root = Dir("/", null)
        var pwd = root

        val commands = input.subList(1, input.size).nonBlank()
        commands.forEach { instruction ->
            val instructionParts = instruction.split(" ")
            val first = instructionParts.first()
            val last = instructionParts.last()
            when {
                first == "dir" -> pwd.add(Dir(last, pwd))
                first.isPositiveNumber() -> pwd.add(File(last, pwd, first.toInt()))
                last == ".." -> pwd = pwd.parent!!
                last != "ls" -> pwd = pwd.find(last)
            }
        }

        val smallDirSum = root.toDirList()
            .map { it.size() }.filter { it < 100_000 }.sum()

        val remainingSize = 70_000_000 - root.size()
        val targetSize = 30_000_000 - remainingSize
        val smallestFixingDirSize = root.toDirList()
            .map { it.size() }
            .sorted()
            .first { it > targetSize }

        return smallDirSum to smallestFixingDirSize
    }
}