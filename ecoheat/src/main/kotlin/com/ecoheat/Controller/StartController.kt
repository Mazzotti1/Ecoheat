package com.ecoheat.Controller

import com.ecoheat.Service.Impl.StartServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/start")
class StartController {
    @Autowired
    private val startService: StartServiceImpl? = null

    @GetMapping
    fun getStartMessage (requestedMessage: String?): String? {
        val message = startService!!.getStartMessage(requestedMessage)
        return message
    }
}