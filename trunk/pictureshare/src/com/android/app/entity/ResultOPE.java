package com.android.app.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultOPE {
	public static final String OPERESULT = "opeResult";
	public static final String OPECODE = "opeCode";
	public static final String OPEDESC = "opeDesc";

	public String opeResult = "opeResult";
	public String opeCode = "opeCode";
	public String opeDesc = "opeDesc";

	public String getOpeResult() {
		return opeResult;
	}

	public void setOpeResult(String opeResult) {
		this.opeResult = opeResult;
	}

	public String getOpeCode() {
		return opeCode;
	}

	public void setOpeCode(String opeCode) {
		this.opeCode = opeCode;
	}

	public String getOpeDesc() {
		return opeDesc;
	}

	public void setOpeDesc(String opeDesc) {
		this.opeDesc = opeDesc;
	}

	public ResultOPE proessApiResult(JSONObject jsonObject) {
		try {
			opeResult = (String) jsonObject.get(OPERESULT);
			opeCode = (String) jsonObject.get(OPECODE);
			opeDesc = (String) jsonObject.get(OPEDESC);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}
}
