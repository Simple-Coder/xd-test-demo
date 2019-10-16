package com.ruimin.ifs.micro.client;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.microswitch.test.bean.ErrorBean;
import com.ruimin.ifs.microswitch.test.bean.Merchant;
import com.ruimin.ifs.microswitch.test.bean.Purchase;
import com.ruimin.ifs.microswitch.test.bean.TestBean;
import com.ruimin.ifs.microswitch.test.bean.TestRspBean;
import com.ruimin.ifs.service.micro.client.ClientProcess;
import com.ruimin.ifs.service.micro.client.factory.ClientChannelFactory;
import com.ruimin.ifs.service.micro.common.MicroSwitchCfgUtil;
import com.ruimin.ifs.service.micro.msgprocess.factory.BufferFactory;

/**
 * 测试客户端 项目名称 ifs-service-micro-switch 类描述 类名称
 * com.ruimin.ifs.microswitch.test.process.ClientTest 创建人 ningpeng 创建时间 Feb 13,
 * 2019 5:20:57 PM 修改人 ningpeng 修改时间 Feb 13, 2019 5:20:57 PM 修改备注
 * 
 * @version 1.0
 */
public class ClientTest {

	public void init() throws Exception {
		MicroSwitchCfgUtil.init();
		BufferFactory.getInstance().init();
		ClientChannelFactory instance = ClientChannelFactory.getInstance();
		instance.init();
	}

	public void testSocketXml() {
		TestBean inputBean = new TestBean();
		inputBean.setVersion("10");
		inputBean.setXtype("10");
		inputBean.setXclass("0");
		inputBean.setPan("11111");
		inputBean.setTestAttr("1111");
		inputBean.setXdate(new Date());
		inputBean.setNumberFmtTest(11);
		Merchant m = new Merchant();
		m.setAcqBIN("1");
		m.setMerID("1");
		m.setPassword("11111118");
		m.setName(StringUtils.replaceEach("测试<>值", new String[] { "<", ">" }, new String[] { "&#60;", "&#62;" }));
		m.setCountry("CNY");
		inputBean.setMerchant(m);
		Purchase p = new Purchase();
		p.setCurrency("RMB");
		p.setPurchAmount(new BigDecimal("100000"));
		p.setExponent("1");
		p.setPurchAmount(new BigDecimal("200000"));
		inputBean.getArray().add(p);
		try {
			ClientProcess cp = new ClientProcess("SocketXMLTcpClient");
			Object rsp = cp.doProcess("TestXml", inputBean, "TestXmlRsp", TestRspBean.class, "Error", ErrorBean.class);
			if (cp.isSuccess()) {
				TestRspBean trsp = (TestRspBean) rsp;
				System.out.println(trsp.getRspcode());
			} else {
				ErrorBean eb = (ErrorBean) rsp;
				System.out.println(eb.getRspcode());
			}

		} catch (SnowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void testSocketFixStr() {
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
		try {
			ClientProcess cp = new ClientProcess("SocketTcpClient");
			Object rsp = cp.doProcess("TestFixString", inputBean, "TestFixStringRsp", TestRspBean.class, "ErrorFixString", ErrorBean.class);
			if (cp.isSuccess()) {
				TestRspBean trsp = (TestRspBean) rsp;
				System.out.println(trsp.getRspcode());
			} else {
				ErrorBean eb = (ErrorBean) rsp;
				System.out.println(eb.getRspcode());
			}

		} catch (SnowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void testSendString() {
		//字符串报文，根据服务端决定格式（json、xml等）
		String reqestMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TestXml number=\"+11\" testAttr=\"1111\" xtype=\"10:A\"><version vertest=\"10\" testAttr-2=\"10\">10</version><xtype xtype=\"10\"></xtype><xclass>0</xclass><pan>11111</pan><xdate>2019-02-14</xdate><Merchant testAttr-b=\"11111\"><acqBIN>1</acqBIN><merID>00000000001</merID><password>11111118</password><name>测试值</name><country>CNY</country></Merchant><array testAttr-a=\"00000000001\"><Purchase testAttr-ab=\"1\"><purchAmount>200,000.00</purchAmount><currency>RMB</currency><exponent>1</exponent><trsFeeAmount>1.00</trsFeeAmount></Purchase></array></TestXml>";
		ClientProcess cp = new ClientProcess("SocketXMLTcpClientSendStr");
		try {
			String rspMsg = cp.doProcess(reqestMsg,"UTF-8");
			System.out.println(rspMsg);
		} catch (SnowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testHttpXml() {
		TestBean inputBean = new TestBean();
		inputBean.setVersion("10");
		inputBean.setXtype("10");
		inputBean.setXclass("0");
		inputBean.setPan("11111");
		inputBean.setTestAttr("1111");
		inputBean.setXdate(new Date());
		inputBean.setNumberFmtTest(11);
		Merchant m = new Merchant();
		m.setAcqBIN("1");
		m.setMerID("1");
		m.setPassword("11111118");
		m.setName(StringUtils.replaceEach("测试<>值", new String[] { "<", ">" }, new String[] { "&#60;", "&#62;" }));
		m.setCountry("CNY");
		inputBean.setMerchant(m);
		Purchase p = new Purchase();
		p.setCurrency("RMB");
		p.setPurchAmount(new BigDecimal("100000"));
		p.setExponent("1");
		p.setPurchAmount(new BigDecimal("200000"));
		inputBean.getArray().add(p);
		try {
			ClientProcess cp = new ClientProcess("HttpXMLTcpClient");
			Object rsp = cp.doProcess("TestXml", inputBean, "TestXmlRsp", TestRspBean.class, "Error", ErrorBean.class);
			if (cp.isSuccess()) {
				TestRspBean trsp = (TestRspBean) rsp;
				System.out.println(trsp.getRspcode());
			} else {
				ErrorBean eb = (ErrorBean) rsp;
				System.out.println(eb.getRspcode());
			}

		} catch (SnowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		ClientTest test = new ClientTest();
		try {
			test.init();
			// socket+xml测试
			//test.testSocketXml();
			// 发送报文按字符串处理
			//test.testSendString();
			//socket+fixstr 测试
			//test.testSocketFixStr();
			//test.testHttpXml();
			//https测试
			ClientProcess cps = new ClientProcess("HttpsXMLTcpClient");
			String doProcess = cps.doProcess("1112233", "UTF-8");
			System.out.println(doProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
