package com.ruimin.ifs.batch.bean.msg.response;

import com.ruimin.ifs.batch.bean.msg._ResponseBean;

public class StartSingleJobResponseBean extends _ResponseBean {
	
	public StartSingleJobResponseBean(String jsonStr) {
		this.parseJson(jsonStr);
	}

}
