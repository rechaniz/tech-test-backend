package com.rechaniz.techtestbackend.handlers

import com.rechaniz.techtestbackend.config.AppProperties
import com.rechaniz.techtestbackend.mail.MailSender
import com.rechaniz.techtestbackend.models.Movement
import com.rechaniz.techtestbackend.repositories.MovementRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(AppProperties::class)
class MovementHandler(
    private val mailSender: MailSender,
    private val movementRepository: MovementRepository,
    private val appProperties: AppProperties
) {
    fun getMovements(): Array<Movement> {
        val movements = movementRepository.findAll();
        movements.sortBy { it.declarationTime };
        return movements.take(50).toTypedArray()
    }

    fun createMovement(movement: Movement) {
        validateMovement(movement)
        movementRepository.insert(movement)
        mailSender.sendMail(appProperties.mailDestination, movement)
    }

    private fun validateMovement(movement: Movement) {

        if (movement.product.weight > movement.product.totalWeight) {
            throw IllegalArgumentException("Le poids de la référence doit être inférieur ou égal au poids total")
        }
        if (movement.product.quantity > movement.product.totalQuantity) {
            throw IllegalArgumentException("La quantité de la référence doit être inférieur ou égal à la quantité totale")
        }
        if (movement.type == "SORTIE" && (movement.customsDocumentType == null || movement.customsDocumentRef == null)) {
            throw IllegalArgumentException("Le type et la référence du document douanier sont obligatoires pour une sortie")
        }
        if (movement.product.refType == "AWB" && !("^[0-9]{11}$".toRegex().matches(movement.product.refNumber))) {
            throw IllegalArgumentException("Pour une référence AWB, le numéro doit être composé de 11 chiffres")
        }
        if (movement.type == "SORTIE" && movementRepository.countByTypeAndProductRefTypeAndProductRefNumber(
                "ENTREE",
                movement.product.refType,
                movement.product.refNumber
            ) == 0
        ) {
            throw IllegalArgumentException("Aucune entrée n'a été déclarée pour cette référence")
        }
    }
}