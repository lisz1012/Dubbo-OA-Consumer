package com.lisz.controller;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lisz.entity.WeChatConfig;

import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.bean.message.templatemessage.TemplateMessageResult;
import weixin.popular.support.TokenManager;
import weixin.popular.util.XMLConverUtil;

@RestController
@RequestMapping("/message")
public class TemplateMessageController {
	
	@Autowired
	private WeChatConfig weChatConfig;
	
	@RequestMapping("")
	public TemplateMessageResult list(Model model, @RequestParam Map<String, String> param, HttpServletRequest request) throws Exception {
		/*InputStream inputStream = request.getInputStream();
		EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);*/
		TemplateMessage templateMessage = new TemplateMessage();
		templateMessage.setUrl(weChatConfig.getBaseDomain() + "/profile/my");
		templateMessage.setTemplate_id(weChatConfig.getTemplateId());
		templateMessage.setTouser(weChatConfig.getUsername());
		
		TemplateMessageItem item = new TemplateMessageItem("李老师的系统架构设计课要开始啦！", "#173177");
		LinkedHashMap<String, TemplateMessageItem> map = new LinkedHashMap<>();
		map.put("course", item);
		templateMessage.setData(map);
		TemplateMessageResult result = MessageAPI.messageTemplateSend(TokenManager.getDefaultToken(), templateMessage);
		
		return result;
	}
}
