package com.rechaniz.techtestbackend.models

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.lang.StringBuilder
import java.time.Instant

@Document("movements")
data class Movement(
    @Id
    val id: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    val declarationTime: Instant,
    val type: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssX", timezone = "UTC")
    val movementTime: Instant,
    val declaredBy: String,
    val product: Product,
    val customsStatus: String,
    val comingFromCode: String,
    val comingFromLabel: String,
    val goingToCode: String,
    val goingToLabel: String,
    val customsDocumentType: String?,
    val customsDocumentRef: String?
) {
    fun toXML(): String {
        return if (this.type == "ENTREE") this.toIncomingXML() else this.toOutgoingXML()
    }

    private fun toIncomingXML(): String {
        val sb: StringBuilder = StringBuilder()
        sb.append("<CargoMessage type=\"WarehouseMovement-In\">\n")
            .append("\t<Header from=\"RAPIDCARGO\" to=\"CARGOINFO\" messageTime=\"" + this.declarationTime + "\" messageID=\"" + this.id + "\"/>\n")
            .append("\t<WarehouseMovementIn>\n")
            .append("\t\t<movementTime>" + this.movementTime + "</movementTime>\n")
            .append("\t\t<declaredIn code=\"" + this.goingToCode + "\" label=\"" + this.goingToLabel + "\"/>\n")
            .append("\t\t<from code=\"" + this.comingFromCode + "\" label=\"" + this.comingFromLabel + "\"/>\n")
            .append("\t\t<goods>\n")
            .append("\t\t\t<ref type=\"" + this.product.refType + "\" code=\"" + this.product.refNumber + "\"/>\n")
            .append("\t\t\t<amount quantity=\"" + this.product.quantity + "\" weight=\"" + this.product.weight + "\"/>\n")
            .append("\t\t\t<description>" + this.product.description + "</description>\n")
            .append("\t\t\t<totalRefAmount quantity=\"" + this.product.totalQuantity + "\" weight=\"" + this.product.totalWeight + "\"/>\n")
            .append("\t\t</goods>\n")
            .append("\t\t<customsStatus>" + this.customsStatus + "</customsStatus>\n")
            .append("\t</WarehouseMovementIn>\n")
            .append("</CargoMessage>\n")

        return sb.toString()
    }

    private fun toOutgoingXML(): String {
        val sb: StringBuilder = StringBuilder()
        sb.append("<CargoMessage type=\"WarehouseMovement-Out\">\n")
            .append("\t<Header from=\"RAPIDCARGO\" to=\"CARGOINFO\" messageTime=\"" + this.declarationTime + "\" messageID=\"" + this.id + "\"/>\n")
            .append("\t<WarehouseMovementOut>\n")
            .append("\t\t<movementTime>" + this.movementTime + "</movementTime>\n")
            .append("\t\t<declaredIn code=\"" + this.comingFromCode + "\" label=\"" + this.comingFromLabel + "\"/>\n")
            .append("\t\t<to code=\"" + this.goingToCode + "\" label=\"" + this.goingToLabel + "\"/>\n")
            .append("\t\t<goods>\n")
            .append("\t\t\t<ref type=\"" + this.product.refType + "\" code=\"" + this.product.refNumber + "\"/>\n")
            .append("\t\t\t<amount quantity=\"" + this.product.quantity + "\" weight=\"" + this.product.weight + "\"/>\n")
            .append("\t\t\t<description>" + this.product.description + "</description>\n")
            .append("\t\t\t<totalRefAmount quantity=\"" + this.product.totalQuantity + "\" weight=\"" + this.product.totalWeight + "\"/>\n")
            .append("\t\t</goods>\n")
            .append("\t\t<customsStatus>" + this.customsStatus + "</customsStatus>\n")
            .append("\t\t<customsDocument type=\"" + this.customsDocumentType + "\" ref=\"" + this.customsDocumentRef + "\"/>\n")
            .append("\t</WarehouseMovementIn>\n")
            .append("</CargoMessage>\n")

        return sb.toString()
    }
}

