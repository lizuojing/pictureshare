package com.android.app.entity;

import org.json.JSONException;
import org.json.JSONObject;

import com.renren.Util;

public class User {

	public String email;
	public String username;
	public String address;
	public String sex;
	public String tel;
	public String birthday;
	public String password;

	public String ToRegeditString() {

		JSONObject jsonParmas = new JSONObject();
		try {

			JSONObject jsonAll = new JSONObject();
			jsonAll.put(JsonTitle.EMAIL, email);
			jsonAll.put(JsonTitle.USERNAME, username);
			jsonAll.put(JsonTitle.SEX, sex);
			jsonAll.put(JsonTitle.BIRTHDAY, birthday);
			jsonAll.put(JsonTitle.ADDRESS, address);
			jsonAll.put(JsonTitle.TEL, tel);
			jsonAll.put(JsonTitle.PASSWD, Util.md5(password));
			jsonParmas.put(JsonTitle.PARAMS, jsonAll);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonParmas.toString();
	}

	public String toLoginString() {

		JSONObject jsonParmas = new JSONObject();
		try {
			JSONObject jsonAll = new JSONObject();
			jsonAll.put(JsonTitle.EMAIL, email);
			jsonAll.put(JsonTitle.PASSWD, Util.md5(password));
			jsonParmas.put(JsonTitle.PARAMS, jsonAll);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonParmas.toString();

	}

	public String toReNameString() {

		JSONObject jsonParmas = new JSONObject();
		try {
			JSONObject jsonAll = new JSONObject();
			jsonAll.put(JsonTitle.EMAIL, email);
			jsonAll.put(JsonTitle.USERNAME, username);
			jsonParmas.put(JsonTitle.PARAMS, jsonAll);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonParmas.toString();

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
