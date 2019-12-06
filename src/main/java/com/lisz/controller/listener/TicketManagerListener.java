package com.lisz.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lisz.entity.WeChatConfig;

import weixin.popular.support.TicketManager;

public class TicketManagerListener implements ServletContextListener {
	
	private static final Log LOGGER = LogFactory.getLog(TicketManagerListener.class);
	
	@Autowired
	private WeChatConfig weChatConfig;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("----- TicketManagerListener contextInitialized -----");
		TicketManager.init(weChatConfig.getAppID(), 15, 60 * 119);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info("----- TicketManagerListener contextDestroyed -----");
		TicketManager.destroyed();
	}
	
}
