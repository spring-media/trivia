package com.adaptionsoft.games.trivia

import com.adaptionsoft.games.trivia.runner.GameRunner
import com.adaptionsoft.games.uglytrivia.Game
import io.kotlintest.specs.BehaviorSpec
import org.amshove.kluent.`should equal`
import java.util.*
import java.io.PrintStream
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets

val goldenMasterString = """
                    Chet is the current player
                    They have rolled a 1
                    Chet's new location is 1
                    The category is Science
                    Science Question 0
                    Answer was corrent!!!!
                    Chet now has 1 Gold Coins.
                    Pat is the current player
                    They have rolled a 3
                    Pat's new location is 3
                    The category is Rock
                    Rock Question 0
                    Answer was corrent!!!!
                    Pat now has 1 Gold Coins.
                    Sue is the current player
                    They have rolled a 5
                    Sue's new location is 5
                    The category is Science
                    Science Question 1
                    Answer was corrent!!!!
                    Sue now has 1 Gold Coins.
                    Chet is the current player
                    They have rolled a 5
                    Chet's new location is 6
                    The category is Sports
                    Sports Question 0
                    Answer was corrent!!!!
                    Chet now has 2 Gold Coins.
                    Pat is the current player
                    They have rolled a 4
                    Pat's new location is 7
                    The category is Rock
                    Rock Question 1
                    Answer was corrent!!!!
                    Pat now has 2 Gold Coins.
                    Sue is the current player
                    They have rolled a 5
                    Sue's new location is 10
                    The category is Sports
                    Sports Question 1
                    Question was incorrectly answered
                    Sue was sent to the penalty box
                    Chet is the current player
                    They have rolled a 3
                    Chet's new location is 9
                    The category is Science
                    Science Question 2
                    Answer was corrent!!!!
                    Chet now has 3 Gold Coins.
                    Pat is the current player
                    They have rolled a 3
                    Pat's new location is 10
                    The category is Sports
                    Sports Question 2
                    Question was incorrectly answered
                    Pat was sent to the penalty box
                    Sue is the current player
                    They have rolled a 3
                    Sue is getting out of the penalty box
                    Sue's new location is 1
                    The category is Science
                    Science Question 3
                    Answer was correct!!!!
                    Sue now has 2 Gold Coins.
                    Chet is the current player
                    They have rolled a 2
                    Chet's new location is 11
                    The category is Rock
                    Rock Question 2
                    Answer was corrent!!!!
                    Chet now has 4 Gold Coins.
                    Pat is the current player
                    They have rolled a 2
                    Pat is not getting out of the penalty box
                    Sue is the current player
                    They have rolled a 1
                    Sue is getting out of the penalty box
                    Sue's new location is 2
                    The category is Sports
                    Sports Question 3
                    Answer was correct!!!!
                    Sue now has 3 Gold Coins.
                    Chet is the current player
                    They have rolled a 5
                    Chet's new location is 4
                    The category is Pop
                    Pop Question 0
                    Answer was corrent!!!!
                    Chet now has 5 Gold Coins.
                    Pat is the current player
                    They have rolled a 4
                    Pat is not getting out of the penalty box
                    Sue is the current player
                    They have rolled a 3
                    Sue is getting out of the penalty box
                    Sue's new location is 5
                    The category is Science
                    Science Question 4
                    Answer was correct!!!!
                    Sue now has 4 Gold Coins.
                    Chet is the current player
                    They have rolled a 1
                    Chet's new location is 5
                    The category is Science
                    Science Question 5
                    Answer was corrent!!!!
                    Chet now has 6 Gold Coins.

                """.trimIndent()

class GoldenMasterTest : BehaviorSpec({
    Given("a game") {
        val game = Game()
        game.add("Chet")
        game.add("Pat")
        game.add("Sue")

        When("running the game to a log") {
            val result = game.runGameToLog(1)
            Then("it should return always the same log") {
                result `should equal` goldenMasterString
            }
        }
    }
})

private fun Game.runGameToLog(seed: Long) =
    redirectOutToString {
        val rand = Random(seed)
        do {
            roll(rand.nextInt(5) + 1)

            if (rand.nextInt(9) == 7) {
                GameRunner.notAWinner = wrongAnswer()
            } else {
                GameRunner.notAWinner = wasCorrectlyAnswered()
            }

        } while (GameRunner.notAWinner)
    }

fun redirectOutToString(function: () -> Unit): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    redirectOut(byteArrayOutputStream, function)
    return byteArrayOutputStream.toString(StandardCharsets.UTF_8.toString())!!
}
fun redirectOut(byteArrayOutputStream: ByteArrayOutputStream, function: () -> Unit) {
    val printStream = PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8.toString())
    return redirectOut(printStream, function)
}

fun redirectOut(printStream: PrintStream, function: () -> Unit) {
    val originalOut = System.out
    System.setOut(printStream)
    function()
    System.setOut(originalOut)
}
