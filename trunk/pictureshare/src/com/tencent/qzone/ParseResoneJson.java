package com.tencent.qzone;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseResoneJson
{
  public static JSONObject parseJson(String response)
    throws JSONException, NumberFormatException, CommonException
  {
    if (response.equals("false")) {
      throw new CommonException("request failed");
    }
    if (response.equals("true")) {
      response = "{value : true}";
    }

    if (response.endsWith(");")) {
      response = response.replaceAll("([a-z]*)\\(([^\\)]*)\\);", "$2");
      response = response.trim();
    }

    JSONObject json = new JSONObject(response);

    return json;
  }
}