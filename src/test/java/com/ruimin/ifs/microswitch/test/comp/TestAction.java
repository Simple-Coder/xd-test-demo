package com.ruimin.ifs.microswitch.test.comp;

import com.ruimin.ifs.core.annotation.Action;
import com.ruimin.ifs.core.annotation.ActionResource;
import com.ruimin.ifs.core.annotation.SnowDoc;
import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.core.iface.action.SnowAction;
import com.ruimin.ifs.core.log.SnowLog;
import com.ruimin.ifs.core.util.constant.SnowEnum.Propagation;
import com.ruimin.ifs.microswitch.test.bean.Purchase;
import com.ruimin.ifs.microswitch.test.bean.TestBean;
import com.ruimin.ifs.microswitch.test.bean.TestRspBean;
import com.ruimin.ifs.service.micro.msgprocess.MessageProcess;
import com.ruimin.ifs.service.micro.msgprocess.MsgConstants.MSG_TYPE;

/**
 * 微交换调用服务
 * 项目名称  ifs-service-micro-switch       
 * 类描述  
 * 类名称    com.ruimin.ifs.microswitch.test.comp.TestAction       
 * 创建人    ningpeng
 * 创建时间  Feb 11, 2019 10:34:37 AM     
 * 修改人  	ningpeng
 * 修改时间  Feb 11, 2019 10:34:37 AM     
 * 修改备注    
 * @version  1.0
 */
@ActionResource
public class TestAction extends SnowAction{

	@Action(propagation=Propagation.REQUIRED)
	@SnowDoc(desc="测试")
	public TestRspBean test(TestBean testbean) throws SnowException{
		
		SnowLog.getServerLog().info("***********************************");
		//报文处理类
		MessageProcess mp = new MessageProcess(MSG_TYPE.XML.code, "UTF-8");
		String msg = mp.beanToMessage("TestXml", testbean, false);
		System.out.println(msg);
		
		
		TestRspBean rsp = new TestRspBean();
		rsp.setRspcode("0000");
		rsp.setRspmsg("成功");
		
		Purchase p1 = new Purchase();
		p1.setCurrency("CNY");
		rsp.getArray().add(p1);
		return rsp;
	}
	
	
}
