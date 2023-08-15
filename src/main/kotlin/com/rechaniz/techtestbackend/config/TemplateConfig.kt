package com.rechaniz.techtestbackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.SimpleMailMessage

@Configuration
class TemplateConfig {
    @Bean
    fun movementDeclaredMailTemplate(): SimpleMailMessage {
        val template = SimpleMailMessage()

        template.subject = "Nouveau mouvement déclaré"
        template.text = """
                Bonjour,
                
                Un nouveau mouvement de marchandises a été déclaré.
            """.trimIndent()

        return template
    }
}