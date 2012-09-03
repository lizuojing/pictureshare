package com.tencent.qzone;

public class Pic
{
  private String mAlbumId;
  private String mLloc;
  private String mSloc;
  private int mWidth;
  private int mHeight;

  public Pic(String albumId, String lloc, String sloc, int width, int height)
  {
    this.mAlbumId = albumId;
    this.mLloc = lloc;
    this.mSloc = sloc;
    this.mWidth = width;
    this.mHeight = height;
  }

  public String getAlbumId()
  {
    return this.mAlbumId;
  }
  public void setAlbumId(String albumId) {
    this.mAlbumId = albumId;
  }

  public String getLloc()
  {
    return this.mLloc;
  }
  public void setLloc(String lloc) {
    this.mLloc = lloc;
  }

  public String getSloc()
  {
    return this.mSloc;
  }
  public void setSloc(String sloc) {
    this.mSloc = sloc;
  }

  public int getWidth()
  {
    return this.mWidth;
  }
  public void setWidth(int width) {
    this.mWidth = width;
  }

  public int getHeight()
  {
    return this.mHeight;
  }
  public void setHeight(int height) {
    this.mHeight = height;
  }

  public String toString()
  {
    return "albumid :" + this.mAlbumId + 
      "\nlloc: " + this.mLloc + 
      "\nsloc: " + this.mSloc + 
      "\nheight: " + this.mHeight + 
      "\nwidth: " + this.mWidth + 
      "\n";
  }
}