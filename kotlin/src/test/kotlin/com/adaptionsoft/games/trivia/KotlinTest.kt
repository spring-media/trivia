package com.adaptionsoft.games.trivia

import io.kotlintest.specs.BehaviorSpec
import org.amshove.kluent.`should equal`

object BehaviorSpecExampleTest : BehaviorSpec({
    Given("an integer") {
        var i = 1
        println("i = 1")
        When("calling incr") {
            i++
            println("i++")
            Then("the integer should be incremented") {
                i `should equal` 2
            }
        }
    }
})
