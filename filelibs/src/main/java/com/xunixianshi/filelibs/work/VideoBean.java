package com.xunixianshi.filelibs.work;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by markIron on 2016/9/7.
 */
public class VideoBean implements Serializable {

    private String firstImg;
    /**
     * 变量描述
     */
    private static final long serialVersionUID = 1L;
    /**
     * 视频id
     */
    public String videoId;
    /**
     * 视频路径
     */
    public String videoPath;
    /**
     * 视频位图
     */
    private Bitmap videoBitmap;
    /**
     * 视频标题
     */
    public String videoTitle;
    /**
     * 视频大小
     */
    public long videoSize;

    /**
     * 视频时间
     */
    public String time;

    /**
     * 已上传进度
     */
    public double percent;

    /**
     * 已上传大小
     */
    public int hasUploadSize;

    /**
     * 已经上传的进度
     */
    public int hasUploadProgress;

    public long lastTime;

    public int videoType; //
    public int viewportCount;

    // 1表示普通视频，2表示左右格式3D，3，表示上下格式3D 4表示单画面全景，5表示上下格式全景
//    int VIDEO_TYPE_UNKNOWN = -1;
//    int VIDEO_TYPE_NORMAL  = 1;
//    int VIDEO_TYPE_L_R_3D  = 2;
//    int VIDEO_TYPE_ONE_GV  = 4;
//    int VIDEO_TYPE_U_D_3D  = 3;
//    int VIDEO_TYPE_U_D_GV  = 5;



    /**
     * 视频百分比
     */

    public VideoBean() {
    }

    public int getViewportCount() {
        return viewportCount;
    }

    public void setViewportCount(int viewportCount) {
        this.viewportCount = viewportCount;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Bitmap getVideoBitmap() {
        return videoBitmap;
    }

    public void setVideoBitmap(Bitmap videoBitmap) {
        this.videoBitmap = videoBitmap;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(long videoSize) {
        this.videoSize = videoSize;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getHasUploadSize() {
        return hasUploadSize;
    }

    public void setHasUploadSize(int hasUploadSize) {
        this.hasUploadSize = hasUploadSize;
    }

    public int getHasUploadProgress() {
        return hasUploadProgress;
    }

    public void setHasUploadProgress(int hasUploadProgress) {
        this.hasUploadProgress = hasUploadProgress;
    }
}
