package com.ruimin.ifs.batch.bean.msg.response;

import com.ruimin.ifs.batch.bean.msg._ResponseBean;

public class SwitchDayResponseBean extends _ResponseBean{
	
	public SwitchDayResponseBean(String jsonStr) {
		this.parseJson(jsonStr);
	}

}
