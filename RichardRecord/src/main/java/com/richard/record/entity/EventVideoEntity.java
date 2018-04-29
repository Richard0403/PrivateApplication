package com.richard.record.entity;

import java.io.File;
import java.io.Serializable;

import com.richard.record.constants.AppSetting;
import com.richard.record.utils.FileUtils;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Table;

@Table(value = "event_video")
public class EventVideoEntity extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SOUND_THRESHOLD_VALUE = 100;
	public static final int UPLOAD_STATUS_FINISH = 2;
	public static final int UPLOAD_STATUS_UPLOADING = 1;
	public static final int UPLOAD_STATUS_WAITING = 0;

	@Column(value = "city")
	public String city;

	@Column(value = "savedFileNameInServer")
	public String fileNameInServer;

	@AutoIncrement
	@Column(value = "id")
	public long id;

	@Column(value = "is_shared")
	public boolean isShared;

	@Column(value = "latitude")
	public double latitude;

	@Column(value = "video_length")
	public int length;

	@Column(value = "lontitude")
	public double lontitude;

	@Column(value = "originalVideoPath")
	public String originalVideoPath;

	@Column(value = "picture")
	public String picture;

	@Column(value = "play_url")
	public String playURL;

	@Column(value = "savedPath")
	public String savedPath;

	@Column(value = "share_url")
	public String shareURL;

	@Column(value = "start_position_of_original_video")
	public int startPositionOfOriginalVideo;

	@Column(value = "status")
	public int status;

	@Column(value = "statusDes")
	public String statusDes;

	@Column(value = "street")
	public String street;

	@Column(value = "time")
	public long time;

	@Column(value = "type")
	public int type;

	@Column(value = "typeDes")
	public String typeDes;

	@Column(value = "upload_car_type")
	public String uploadCarType;

	@Column(value = "upload_cartnumber")
	public String uploadCartnumber;

	@Column(value = "upload_status")
	public int uploadStatus;

	@Column(value = "upload_tag")
	public String uploadTag;

	@Column(value = "upload_title")
	public String uploadTitle;
	
	@Column(value = "upload_car_shot_pic")
	public String uploadCarShotPic;
	
	@Column(value = "upload_weizhang_reason")
	public int uploadWZReason;
	
	@Column(value = "upload_add_info")
	public String uploadAddInfo;
	
	@Column(value = "upload_vid")
	public String uploadVId;
	
	@Column(value = "curr_rogress")
	public int cProgress;

	@Column(value = "value")
	public int value;

	@Column(value = "video_id")
	public long videoId;
	
	@Column(value = "video_more_pic")
	public String videoMorePic;
	
	@Column(value = "video_merge_pic")
	public String videoMergePic;
	
    @Column("savedFileNameIn7Niu")
    public String fileNameIn7Niu;// 在7 牛保存文件名
    
    @Column("video_transpose")
	public int video_transpose =-1;

	public String toString() {
		return "EventStatusPo{id=" + this.id + ", type=" + this.type
				+ ", typeDes='" + this.typeDes + '\'' + ", status="
				+ this.status + ", statusDes='" + this.statusDes + '\''
				+ ", city='" + this.city + '\'' + ", street='" + this.street
				+ '\'' + ", value=" + this.value + ", time=" + this.time
				+ ", picture='" + this.picture + '\'' + ", videoId="
				+ this.videoId+", fileNameIn7Niu="+ this.fileNameIn7Niu + '}';
	}

	private void setSavedPath() {
		this.savedPath = (this.originalVideoPath.replace(".mp4", "") + "/"
				+ this.startPositionOfOriginalVideo + "-" + (this.startPositionOfOriginalVideo + this.length) + ".mp4");
		this.picture = this.savedPath;
		this.picture = this.picture.replace(".mp4", ".jpg");
		
		File localFile = new File(this.savedPath).getParentFile();
		if ((!localFile.exists()) && (!localFile.mkdirs()))
			;
	}

	public void setVideoParams(VideoEntity video) {
		long start_pos = 0L;
		long video_length = 1000L * ((this.time - video.startTime) / 1000L);  //捕获视频与原视频的起始时间之间的长度
		long limit_length = 1000 * AppSetting.CAPTURE_TIME_LENGTH; // 用户设置的抓拍时间长度
		long start_pos_tmp = 1000L + (video_length - limit_length);
		if (start_pos_tmp >= start_pos){ //如果视频长度大于总长度，则计算起点为非0
			start_pos = start_pos_tmp;
		}
		this.originalVideoPath = video.path;
		this.startPositionOfOriginalVideo = ((int) start_pos / 1000);
		if(video_length<limit_length){
			this.length = (int) (video_length/1000L);
		}else{
			this.length = (int)( limit_length/1000L);
		}
		setSavedPath();
		
		
		
	}
	
	  public static void delete(EventVideoEntity video){
			if(FileUtils.isFileExist(video.savedPath)){
				new File(video.savedPath).delete();
			}
			
			if(FileUtils.isFileExist(video.picture)){
				new File(video.picture).delete();
			}
			video.delete();
	  }
	
}
