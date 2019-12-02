package com.lisz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/WeChat")
public class WeChatController {
	
	@GetMapping("signature")
	@ResponseBody
	public String signature() {
		return "Hello, WeChat";
	}
}
