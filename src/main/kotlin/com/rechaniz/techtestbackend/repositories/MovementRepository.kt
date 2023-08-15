package com.rechaniz.techtestbackend.repositories

import com.rechaniz.techtestbackend.models.Movement
import org.springframework.data.mongodb.repository.MongoRepository

interface MovementRepository : MongoRepository<Movement, String> {

    fun countByTypeAndProductRefTypeAndProductRefNumber(
        refType: String,
        refNumber: String,
        type: String
    ): Int

}