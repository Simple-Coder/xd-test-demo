package com.ruimin.ifs.batch.bean.msg.response;

import com.ruimin.ifs.batch.bean.msg._ResponseBean;

public class StartJobResponseBean extends _ResponseBean {
	
	public StartJobResponseBean(String jsonStr) {
		this.parseJson(jsonStr);
	}

}
