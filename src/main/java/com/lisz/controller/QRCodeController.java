package com.lisz.controller;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import weixin.popular.api.QrcodeAPI;
import weixin.popular.bean.qrcode.QrcodeTicket;
import weixin.popular.support.TokenManager;

@RestController
@RequestMapping("/QRCode")
public class QRCodeController {
	
	@RequestMapping("/create")
	public void list(Model model, @RequestParam Map<String, String> param, HttpServletRequest request, HttpServletResponse response) throws Exception {
		QrcodeTicket ticket = QrcodeAPI.qrcodeCreateTemp(TokenManager.getDefaultToken(), 3600, 123L);
		System.out.println(ToStringBuilder.reflectionToString(ticket, ToStringStyle.JSON_STYLE));
		BufferedImage bufferedImage = QrcodeAPI.showqrcode(ticket.getTicket());
		ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
	}
}
