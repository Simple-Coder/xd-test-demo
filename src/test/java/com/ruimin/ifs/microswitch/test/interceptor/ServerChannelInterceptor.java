package com.ruimin.ifs.microswitch.test.interceptor;

import com.ruimin.ifs.core.exception.SnowException;
import com.ruimin.ifs.service.micro.common.Context;
import com.ruimin.ifs.service.micro.common.base.IInterceptor;
import com.ruimin.ifs.service.micro.common.base._Entry;

/*
 * 报头块的格式如下表： 相对位移 长度 属性 名称 值域 说明 1． 0 6 n 报文长度 整个报文的长度含这六位 6 4 4 交易码
 * 不同的报文用不同的交易码 10 16 16 报文MAC 跳过报头部分全包算MAC 26 4 4 MAC机器号 30 1 1 结束标识 #
 */
public class ServerChannelInterceptor extends _Entry<IInterceptor> implements IInterceptor {

	@Override
	public IInterceptor getIntance() {
		return this;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDesc(String desc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttrValue(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAttrValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAttrValue(String name, String defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAttrKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doPreRequest(Context ctx) throws SnowException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPostRequest(Context ctx) throws SnowException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPreRespone(Context ctx) throws SnowException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPostRespone(Context ctx) throws SnowException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPreException(Context ctx) throws SnowException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPostException(Context ctx) throws SnowException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}


}
