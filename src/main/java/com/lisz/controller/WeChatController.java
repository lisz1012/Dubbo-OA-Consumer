package com.lisz.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/WeChat")
public class WeChatController {
	
	@GetMapping("signature")
	@ResponseBody
	public String signature(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) {
		// TODO加入验证
		return "Hello, WeChat";
	}
}
