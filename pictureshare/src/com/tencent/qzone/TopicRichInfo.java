package com.tencent.qzone;

public class TopicRichInfo
{
  private int mRtype;
  private String mUrl2;
  private String mUrl3;
  private int mWho;

  public TopicRichInfo(int rtype, String url2, String url3, int who)
  {
    this.mRtype = rtype;
    this.mUrl2 = url2;
    this.mUrl3 = url3;
    this.mWho = who;
  }
  public int getRtype() {
    return this.mRtype;
  }
  public void setRtype(int rtype) {
    this.mRtype = rtype;
  }
  public String getUrl2() {
    return this.mUrl2;
  }
  public void setUrl2(String url2) {
    this.mUrl2 = url2;
  }
  public String getUrl3() {
    return this.mUrl3;
  }
  public void setUrl3(String url3) {
    this.mUrl3 = url3;
  }
  public int getWho() {
    return this.mWho;
  }
  public void setWho(int who) {
    this.mWho = who;
  }

  public String toString()
  {
    return "rtype: " + this.mRtype + "\nurl2: " + this.mUrl2 + "\nurl3: " + this.mUrl3 + "\nwho: " + this.mWho + "\n";
  }
}