package com.ecoheat.Service.Impl

import com.ecoheat.Service.IStartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.*

@Service
class StartServiceImpl @Autowired constructor(private val messageSource: MessageSource): IStartService {
    override fun getStartMessage(requestedMessage: String?): String? {
        val locale = Locale("pt")
        val message = messageSource.getMessage("startup.message", null, locale)
        return message
    }
}

