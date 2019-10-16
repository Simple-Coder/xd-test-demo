package com.ruimin.ifs.micro.msg;

import java.math.BigDecimal;
import java.util.Date;

import com.ruimin.ifs.microswitch.test.bean.Merchant;
import com.ruimin.ifs.microswitch.test.bean.Purchase;
import com.ruimin.ifs.microswitch.test.bean.TestBean;
import com.ruimin.ifs.service.micro.common.MicroSwitchCfgUtil;
import com.ruimin.ifs.service.micro.msgprocess.MessageProcess;
import com.ruimin.ifs.service.micro.msgprocess.MsgConstants.MSG_TYPE;
import com.ruimin.ifs.service.micro.msgprocess.factory.BufferFactory;
/**
 * 报文测试
 * @author ningpeng
 *
 */
public class MessageProcTest {

	
	public static void main(String[] args) {
		try {
		MicroSwitchCfgUtil.init();
		BufferFactory.getInstance().init();
		TestBean testBean = getTestBean();
		
		MessageProcess mp = new MessageProcess(MSG_TYPE.FIXSTR.code);
		String beanToMessage = mp.beanToMessage("TestFixString", testBean, false);
		System.out.println(beanToMessage);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static TestBean getTestBean() {
		TestBean inputBean = new TestBean();
		inputBean.setVersion("10");
		inputBean.setXtype("10");
		inputBean.setXclass("0");
		inputBean.setPan("1111111111111111119");
		inputBean.setTestAttr("attr");
		inputBean.setXdate(new Date());
		inputBean.setNumberFmtTest(11);
		Merchant m = new Merchant();
		m.setAcqBIN("1");
		m.setMerID("1");
		m.setPassword("11111118");
		m.setName("222");
		m.setCountry("CNY");
		inputBean.setMerchant(m);
		Purchase p = new Purchase();
		p.setCurrency("RMB");
		p.setPurchAmount(new BigDecimal("100000"));
		p.setExponent("1");
		p.setTrsFeeAmount(new BigDecimal(100));
		Purchase p2 = new Purchase();
		p2.setCurrency("CNY");
		p2.setPurchAmount(new BigDecimal("200000"));
		p2.setExponent("2");
		p2.setTrsFeeAmount(new BigDecimal(200));
		inputBean.getArray().add(p);
		inputBean.getArray().add(p2);
		return inputBean;
	}

}
