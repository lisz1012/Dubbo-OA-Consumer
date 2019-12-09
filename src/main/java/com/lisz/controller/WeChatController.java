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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lisz.entity.WeChatConfig;

import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.TokenManager;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

@Controller
@RequestMapping("/WeChat")
public class WeChatController {
	@Autowired
	private WeChatConfig weChatConfig;
	
	private static final Log LOGGER = LogFactory.getLog(WeChatController.class);
	
	private static ExpireKey expireKey = new DefaultExpireKey();
	
	@RequestMapping("signature")
	@ResponseBody
	public void signature(@RequestParam Map<String, String> params, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Touched");
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
			String key = eventMessage.getFromUserName() + "__" + eventMessage.getToUserName() + "__" + eventMessage.getMsgId() + "__" + eventMessage.getCreateTime();
			
			// 微信不确定发的消息是否被接受，所以发好几次，这里我们用key避免重复处理
			if (expireKey.exists(key)) {
				LOGGER.warn("重复通知，不作处理。");
				return;
			} else {
				expireKey.add(key);
			}
			
			// 这里注意：回复消息的FromUserName和ToUserNmae正好跟发进来的eventMessage是相反的，所以先是eventMessage.getFromUserName(),再写eventMessage.getToUserName()
			XMLMessage xmlMessage = new XMLTextMessage(eventMessage.getFromUserName(), eventMessage.getToUserName(), "Hello！");
			// 回复
			if (null != xmlMessage) {
				xmlMessage.outputStreamWrite(outputStream);
			}
		}
	}
// {"toUserName":"gh_03f7e1f8c5e2","fromUserName":"oQ0kGw_fQV55_TIDrCxMFNJqtGKE","createTime":1575874311,"msgType":"event","event":"CLICK","eventKey":"V1001_TODAY_MUSIC","msgId":null,"content":null,"picUrl":null,"mediaId":null,"format":null,"recognition":null,"thumbMediaId":null,"location_X":null,"location_Y":null,"scale":null,"label":null,"title":null,"description":null,"url":null,"ticket":null,"latitude":null,"longitude":null,"precision":null,"status":null,"totalCount":null,"filterCount":null,"sentCount":null,"errorCount":null,"copyrightCheckResult":null,"expiredTime":null,"failTime":null,"failReason":null,"uniqId":null,"poiId":null,"result":null,"msg":null,"chosenBeacon":null,"aroundBeacons":null,"lotteryId":null,"money":null,"bindTime":null,"connectTime":null,"expireTime":null,"vendorId":null,"shopId":null,"deviceNo":null,"keyStandard":null,"keyStr":null,"country":null,"province":null,"city":null,"sex":null,"scene":null,"regionCode":null,"reasonMsg":null,"otherElements":null}
	@RequestMapping("createMenu")
	@ResponseBody
	public BaseResult createMenu() {
		String menuString = "{\n" + 
				"     \"button\":[\n" + 
				"     {	\n" + 
				"          \"type\":\"click\",\n" + 
				"          \"name\":\"今日歌曲\",\n" + 
				"          \"key\":\"V1001_TODAY_MUSIC\"\n" + 
				"      },\n" + 
				"      {\n" + 
				"           \"name\":\"菜单\",\n" + 
				"           \"sub_button\":[\n" + 
				"           {	\n" + 
				"               \"type\":\"view\",\n" + 
				"               \"name\":\"搜索\",\n" + 
				"               \"url\":\"http://www.soso.com/\"\n" + 
				"            },\n" + 
				"            {\n" + 
				"               \"type\":\"click\",\n" + 
				"               \"name\":\"赞一下我们\",\n" + 
				"               \"key\":\"V1001_GOOD\"\n" + 
				"            }]\n" + 
				"       }]\n" + 
				" }";
		BaseResult result = MenuAPI.menuCreate(TokenManager.getDefaultToken(), menuString);
		return result;
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
