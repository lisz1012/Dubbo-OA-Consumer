package com.lisz.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lisz.entity.WeChatConfig;

import weixin.popular.bean.message.EventMessage;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

@Controller
@RequestMapping("/WeChat")
public class WeChatController {
	@Autowired
	private WeChatConfig weChatConfig;
	
	@GetMapping("signature")
	@ResponseBody
	public void signature(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO加入验证
		ServletInputStream inputStream = request.getInputStream();
		ServletOutputStream outputStream = response.getOutputStream();

		// 算出来的签名
		String signature = params.get("signature");
		String echostr = params.get("echostr");
		String timestamp = params.get("timestamp");
		String nonce = params.get("nonce");
		
		// 对称加密 本地
		String token = weChatConfig.getTokenString();
		
		if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp)) {
			OutputStreamWriter(outputStream, "fail request");
			return;
		}
		
		if (echostr != null) {
			OutputStreamWriter(outputStream, echostr);
			return;
		}
		
		// 验证请求签名
		if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
			System.out.println("The request signature is invalid");
			return;
		}
		
		if (inputStream != null) {
			EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
			System.out.println("ToStringBuilder: " + ToStringBuilder.reflectionToString(eventMessage, ToStringStyle.JSON_STYLE));
			String keyString = eventMessage.getFromUserName() + "__" + eventMessage.getEventKey();
		}
	}

	private boolean OutputStreamWriter(OutputStream outputStream, String text) {
		try {
			outputStream.write(text.getBytes("utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
