package org.nowakproject.functionalprogramming.kotlinarrow.statet

data class Elevator(val floors: Int, val currentFloor: Int) {

}

sealed class ElevatorError {
    object ElevatorLiftError : ElevatorError()
    object ElevatorDownError : ElevatorError()
}
