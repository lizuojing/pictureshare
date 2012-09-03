package com.tencent.qzone;

public class UserProfile
{
  private String mRealName;
  private int mGender;
  private String mIcon_30;
  private String mIcon_50;
  private String mIcon_100;

  public UserProfile(String realName, int gender, String icon_30, String icon_50, String icon_100)
  {
    this.mRealName = realName;
    this.mGender = gender;
    this.mIcon_30 = icon_30;
    this.mIcon_50 = icon_50;
    this.mIcon_100 = icon_100;
  }

  public String getRealName()
  {
    return this.mRealName;
  }
  public void setNickName(String realName) {
    this.mRealName = realName;
  }

  public String getIcon_30()
  {
    return this.mIcon_30;
  }
  public void setIcon_30(String icon_30) {
    this.mIcon_30 = icon_30;
  }

  public String getIcon_50()
  {
    return this.mIcon_50;
  }
  public void setIcon_50(String icon_50) {
    this.mIcon_50 = icon_50;
  }

  public String getIcon_100()
  {
    return this.mIcon_100;
  }
  public void setIcon_100(String icon_100) {
    this.mIcon_100 = icon_100;
  }

  public int getGender()
  {
    return this.mGender;
  }
  public void setGender(int gender) {
    this.mGender = gender;
  }

  public String toString()
  {
    return "realName: " + this.mRealName + "\ngender: " + (this.mGender == 0 ? "女" : "男") + "\nicon_30: " + this.mIcon_30 + "\nicon_50: " + this.mIcon_50 + "\nicon_100: " + this.mIcon_100 + "\n";
  }
}