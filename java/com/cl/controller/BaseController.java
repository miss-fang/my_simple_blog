package com.cl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
	@RequestMapping("/")
	public String toArticleIndex() {
		return "redirect:/article/index";
	}
}
