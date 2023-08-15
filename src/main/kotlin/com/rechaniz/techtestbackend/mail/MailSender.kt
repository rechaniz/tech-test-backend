package com.rechaniz.techtestbackend.mail

import com.rechaniz.techtestbackend.models.Movement
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.nio.file.Files

@Service
class MailSender (
    private val mailSender: JavaMailSender,
    private val mailTemplate: SimpleMailMessage
){
    fun sendMail(destination: String, movement: Movement) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        val tempXMLfile = Files.createTempFile(null, null).toFile()
        tempXMLfile.writeText(movement.toXML(), Charsets.UTF_8)

        helper.setTo(destination)
        helper.setSubject(mailTemplate.subject!!)
        helper.setText(mailTemplate.text!!)
        helper.addAttachment("movement.xml", tempXMLfile)


        mailSender.send(message)
    }


}