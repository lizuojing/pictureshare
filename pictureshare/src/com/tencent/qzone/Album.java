package com.tencent.qzone;

public class Album
{
  private String mAlbumid;
  private int mClassid;
  private long mCreatetime;
  private String mDesc;
  private String mName;
  private long mPicnum;
  private int mPriv;

  public Album(String albumid, int classid, long createtime, String desc, String name, long picnum, int priv)
  {
    this.mAlbumid = albumid;
    this.mClassid = classid;
    this.mCreatetime = createtime;
    this.mDesc = desc;
    this.mName = name;
    this.mPicnum = picnum;
    this.mPriv = priv;
  }

  public String getAlbumid()
  {
    return this.mAlbumid;
  }
  public void setAlbumid(String albumid) {
    this.mAlbumid = albumid;
  }
  public int getClassid() {
    return this.mClassid;
  }
  public void setClassid(int classid) {
    this.mClassid = classid;
  }

  public long getCreatetime()
  {
    return this.mCreatetime;
  }
  public void setCreatetime(long createtime) {
    this.mCreatetime = createtime;
  }

  public String getDesc()
  {
    return this.mDesc;
  }
  public void setDesc(String desc) {
    this.mDesc = desc;
  }

  public String getName()
  {
    return this.mName;
  }
  public void setName(String name) {
    this.mName = name;
  }

  public long getPicnum()
  {
    return this.mPicnum;
  }
  public void setPicnum(long picnum) {
    this.mPicnum = picnum;
  }

  public int getPriv()
  {
    return this.mPriv;
  }
  public void setPriv(int priv) {
    this.mPriv = priv;
  }

  public String toString()
  {
    return "albumid: " + this.mAlbumid + 
      "\nclassid: " + this.mClassid + 
      "\ncreatetime: " + this.mCreatetime + 
      "\ndesc: " + this.mDesc + 
      "\nname: " + this.mName + 
      "\npicnum: " + this.mPicnum + 
      "\npriv: " + this.mPriv + 
      "\n";
  }
}