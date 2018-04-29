package com.richard.record.entity;

import java.io.File;
import java.io.Serializable;

import com.richard.record.utils.FileUtils;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Table;

@Table(value="video")
public class VideoEntity extends Model implements Serializable{

	  @Column(value="city")
	  public String city;

	  @Column(value="endTime")
	  public long endTime;

	  @AutoIncrement
	  @Column(value="id")
	  public long id;

	  @Column(value="latitude")
	  public double latitude;

	  @Column(value="lontitude")
	  public double lontitude;

	  @Column(value="path")
	  public String path;

	  @Column(value="picture")
	  public String picture;

	  @Column(value="startTime")
	  public long startTime;

	  @Column(value="street")
	  public String street;
	  
	  @Column(value="end_latitude")
	  public double end_latitude;

	  @Column(value="end_lontitude")
	  public double end_lontitude;
	  
	  @Column(value="end_street")
	  public String end_street;
	  
	  @Column(value="route")
	  public String route;
	  
	  @Column("video_transpose")
	  public int video_transpose =-1;
	  
	  public String toString()
	  {
	    return "VideoPo{id=" + this.id + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", picture='" + this.picture + '\'' + ", path='" + this.path + '\'' + ", city='" + this.city + '\'' + ", street='" + this.street + '\'' + ", latitude=" + this.latitude + ", lontitude=" + this.lontitude 
	    		+", end_latitude="+ this.end_latitude +", end_lontitude="+ this.end_lontitude + ", end_street= "+ this.end_street +", route=" + this.route 
	    		+ '}';
	  }
	  public static void delete(VideoEntity video){
			if(FileUtils.isFileExist(video.path)){
				new File(video.path).delete();
			}
			
			if(FileUtils.isFileExist(video.route)){
				new File(video.route).delete();
			}
			
			if(FileUtils.isFileExist(video.picture)){
				new File(video.picture).delete();
			}
			video.delete();
	  }
	  
	  
}
