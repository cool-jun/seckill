package com.hj.seckill.controller;

import com.hj.seckill.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @RequestMapping("/toList")
    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String ticket) {
        if (ticket.isEmpty()) {
            return "login";
        }

        User user = (User) session.getAttribute(ticket);
        if (user == null) {
            return "login";
        }

        model.addAttribute("user", user);
        return "goodsList";
    }
}
