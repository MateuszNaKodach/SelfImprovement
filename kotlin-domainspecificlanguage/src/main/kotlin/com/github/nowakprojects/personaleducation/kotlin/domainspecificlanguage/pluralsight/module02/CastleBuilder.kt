package com.github.nowakprojects.personaleducation.kotlin.domainspecificlanguage.pluralsight.module02

import com.github.nowakprojects.personaleducation.kotlin.domainspecificlanguage.pluralsight.model.Connectable
import com.github.nowakprojects.personaleducation.kotlin.domainspecificlanguage.pluralsight.model.StringSymbolTable


fun main(args : Array<String>) {
    CastleDsl().build()
}

class CastleDsl {
    fun build() {
        var builder = CastleBuilder()

        // function sequence
        builder.keep("keep")
        builder.tower("sw")
        builder.tower("nw")
        builder.tower("ne")
        builder.tower("se")
        builder.connect("sw", "nw")
        builder.connect("nw", "ne")
        builder.connect("ne", "se")
        builder.connect("se", "sw")

        // apply
        builder.apply {
            keep("keep")
            tower("sw")
            tower("nw")
            tower("ne")
            tower("se")
            connect("sw", "nw")
            connect("nw", "ne")
            connect("ne", "se")
            connect("se", "sw")
            connect("keep", "nw")
            connect("keep", "nw")
            connect("keep", "se")
            connect("keep", "sw")
        }

        // builders
        builder = CastleBuilder()
        builder.keep("keep").tower("sw")
                .tower("nw").tower("ne").tower("se")
        builder.connect("keep", "sw").connect("keep", "ne")
                .connect("keep", "se").connect("keep", "sw")
        builder.connect("sw", "nw").connect("nw", "ne")
                .connect("ne", "se").connect("se", "sw")
        val castle = builder.build()
        println("result: $castle")

        // use varargs for this one
        builder.connectToAll("keep", "sw", "nw", "ne", "se")

        // using map syntax
        builder.connect(mapOf("sw" to "nw", "nw" to "ne",
                "ne" to "se", "se" to "sw"))
    }
}

class CastleBuilder {
    private var towers = mutableListOf<Tower>()
    private var keep: Keep? = null
    private var connections = mutableMapOf<String, String>()

    fun tower(name: String) : CastleBuilder {
        val tower = Tower(name)
        towers.add(tower)
        return this
    }

    fun keep(name: String = "keep") : CastleBuilder {
        keep = Keep(name)
        return this
    }

    fun connect(from: String, to: String): CastleBuilder {
        connections[from] = to
        return this
    }

    fun connectToAll(from: String, vararg to: String): CastleBuilder {
        for (toConnect in to) {
            connect(from, toConnect)
        }
        return this
    }

    fun connect(map: Map<String, String>) {
        map.forEach { from, to ->
            connect(from, to)
        }
    }

    fun build(): Castle {
        val symbols = StringSymbolTable<Connectable>()
        towers.forEach { symbols.add(it.name, it) }
        keep?.let {
            symbols.add(it.name, it)
        }

        val allWalls = connections.map { (from, to) ->
            Wall(symbols.lookup(from), symbols.lookup(to)) }
        return Castle(keep, towers, allWalls)
    }
}

data class Castle(var keep: Keep?, var towers: List<Tower>, var walls: List<Wall>)
data class Keep(override var name: String = "keep"): Connectable
data class Tower(override var name:String): Connectable
data class Wall(var from: Connectable, var to: Connectable)
