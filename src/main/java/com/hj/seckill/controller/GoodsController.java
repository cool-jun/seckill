package com.hj.seckill.controller;

import com.hj.seckill.pojo.User;
import com.hj.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private UserService userService;

    @RequestMapping("/toList")
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket) {
        if (ticket.isEmpty()) {
            return "login";
        }

//        User user = (User) session.getAttribute(ticket);
        User user = userService.getUserByCookie(ticket, request, response);
        if (user == null) {
            return "login";
        }

        model.addAttribute("user", user);
        return "goodsList";
    }
}
