package com.github.nowakprojects.learning.kotlin.pluralsight.usingcoroutines.part9

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.runBlocking
import java.lang.IllegalStateException
import java.util.*

sealed class Move {
    override fun toString(): String = this.javaClass.simpleName

}

object Rock : Move()
object Paper : Move()
object Scissors : Move()

sealed class Game
class Start(val response: CompletableDeferred<Int>) : Game()
class Play(val sender: Channel<Game>, val name: String) : Game()
class Throw(val actor: String, val move: Move) : Game()

fun playerActor() = GlobalScope.actor<Game> {
    var name: String

    channel.consumeEach {
        when (it) {
            is Play -> {
                name = it.name
                val selection = (1..4).random()
                val move = when (selection) {
                    1 -> Rock
                    2 -> Paper
                    3 -> Scissors
                    else -> throw IllegalStateException("Bad move!")
                }
                it.sender.send(Throw(name, move))
            }
            else -> throw IllegalStateException("Invalid message!")
        }
    }
}

fun coordinatorActor() = GlobalScope.actor<Game> {
    lateinit var response: CompletableDeferred<Int>

    val player1 = playerActor()
    val player2 = playerActor()

    channel.consumeEach {
        when (it) {
            is Start -> {
                response = it.response
                player1.send(Play(channel, "Player 1"))
                player2.send(Play(channel, "Player 2"))
            }
            is Throw -> {
                val playerA = it.actor
                val moveA = it.move
                val message2 = channel.receive() as Throw
                val playerB = message2.actor
                val moveB = message2.move
                announce(playerA, moveA, playerB, moveB)
                response.complete(0)
            }
        }
    }

}


fun announce(playerA: String, moveA: Move, playerB: String, moveB: Move) {
    var awin = false

    print("$playerA -> $moveA, $playerB -> $moveB ")

    if (moveA == moveB) {
        println("Draw")
        return
    }
    when (moveA) {
        is Rock -> {
            when (moveB) {
                is Scissors -> {
                    awin = true
                }
            }
        }
        is Scissors -> {
            when (moveB) {
                is Paper -> {
                    awin = true
                }
            }
        }
        is Paper -> {
            when (moveB) {
                is Rock -> {
                    awin = true
                }
            }
        }
    }

    if (awin) {
        println("$playerA wins")
    } else {
        println("$playerB wins")
    }
}


fun main(args: Array<String>) = runBlocking<Unit> {

    val response = CompletableDeferred<Int>()
    val coord = coordinatorActor()
    coord.send(Start(response))

    //Wait for end of the game
    response.await()
}

fun log(msg: String) = print("[${Thread.currentThread().name}] $msg")

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start