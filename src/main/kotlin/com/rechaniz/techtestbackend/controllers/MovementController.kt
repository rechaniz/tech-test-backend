package com.rechaniz.techtestbackend.controllers

import com.rechaniz.techtestbackend.handlers.MovementHandler
import com.rechaniz.techtestbackend.models.Movement
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MovementController (private val movementHandler: MovementHandler){

    @GetMapping("/movements")
    fun getMovements() : ResponseEntity<Array<Movement>> {
        val movements = movementHandler.getMovements()

        return ResponseEntity.ok(movements)
    }

    @PostMapping("/movements")
    fun createMovement(@RequestBody movement: Movement): ResponseEntity<Void> {
        movementHandler.createMovement(movement)
        return ResponseEntity.ok().build()
    }


}