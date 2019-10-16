package com.ruimin.ifs.batch.bean.msg.response;

import com.ruimin.ifs.batch.bean.msg._ResponseBean;

public class StartOnlineResponseBean extends _ResponseBean{
	
	public StartOnlineResponseBean(String jsonStr) {
		this.parseJson(jsonStr);
	}

}
