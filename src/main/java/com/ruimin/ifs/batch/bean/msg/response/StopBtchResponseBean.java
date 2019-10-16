package com.ruimin.ifs.batch.bean.msg.response;

import com.ruimin.ifs.batch.bean.msg._ResponseBean;

public class StopBtchResponseBean extends _ResponseBean {
	
	public StopBtchResponseBean(String jsonStr) {
		this.parseJson(jsonStr);
	}

}
