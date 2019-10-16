package com.ruimin.ifs.microswitch.test.process;

import java.util.ArrayList;
import java.util.List;

import com.ruimin.ifs.service.micro.msgprocess.check.IRangeDdicProc;


public class TestRangeDdicProc implements IRangeDdicProc {

	private static TestRangeDdicProc proc = null;

	public synchronized static TestRangeDdicProc getProc() {
		if (proc == null) {
			proc = new TestRangeDdicProc();
		}
		return proc;
	}

	private TestRangeDdicProc() {
	}

	@Override
	public List<String> getRangeDdicList(String ddicNo) {
		List<String> retList = new ArrayList<>();
		retList.add("0");
		retList.add("B");
		return retList;
	}
}
