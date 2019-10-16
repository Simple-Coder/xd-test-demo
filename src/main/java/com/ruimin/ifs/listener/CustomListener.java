package com.ruimin.ifs.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ruimin.ifs.core.common.core.SnowApi;
import com.ruimin.ifs.framework.utils.SysParamUtil;
import com.ruimin.ifs.login.comp.LoginAction;
import com.ruimin.ifs.service.micro.MicroSwitchCtl;
import com.ruimin.ifs.spring.util.ApplicationContextUtils;

public class CustomListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		ApplicationContextUtils.close();
		//微交换卸载
		int msflg = SysParamUtil.getInstance().getInt("SYS.MICRO_SWITCH_RUNNING", 0);
		if (msflg==1) {
			try {
				MicroSwitchCtl.stop();
				MicroSwitchCtl.destory();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		try{
			ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
			ApplicationContextUtils.setContext(ctx);
			//设置用户为登出状态
			SnowApi.getInstance().execAction(LoginAction.class, "setAllUserLogOut", null);
			//微交换启动
			int msflg = SysParamUtil.getInstance().getInt("SYS.MICRO_SWITCH_RUNNING", 0);
			if (msflg==1) {
				MicroSwitchCtl.init();
				MicroSwitchCtl.start();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}
