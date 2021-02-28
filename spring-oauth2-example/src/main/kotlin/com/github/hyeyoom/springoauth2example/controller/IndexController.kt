package com.github.hyeyoom.springoauth2example.controller

import com.github.hyeyoom.springoauth2example.entity.dto.SessionUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpSession

@Controller
@RequestMapping("/")
class IndexController(
    private val session: HttpSession
) {

    @GetMapping
    fun index(model: Model): String {
        val user = session.getAttribute("user") as SessionUser?
        model.addAttribute("user", user)
        return "index"
    }

}