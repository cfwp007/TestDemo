package com.xunixianshi.filelibs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hch.myutils.interfaces.BatteryChangeListener;
import com.hch.myutils.utils.MLog;
import com.hch.myutils.utils.MobileUtil;
import com.hch.myutils.utils.SimpleSharedPreferences;
import com.hch.myutils.utils.WifiAdminUtil;
import com.xunixianshi.filelibs.work.ACache;
import com.xunixianshi.filelibs.work.BitMapUtils;
import com.xunixianshi.filelibs.work.Config;
import com.xunixianshi.filelibs.work.FileUtil;
import com.xunixianshi.filelibs.work.TaskService;
import com.xunixianshi.filelibs.work.ThreadUtilWork;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * 1.获取本地视频
 * 2.获取视频第一帧并保存至本地
 * 3.获取对应数据JSON串
 */
public class ScannerSdk {
    private static ScannerSdk mScannerSdk = null;


    public ScannerSdk() {
    }
    public static ScannerSdk getInstans(){

        if (mScannerSdk ==null){
            mScannerSdk = new ScannerSdk();
        }
        return mScannerSdk;
    }


    public void connectScannerSdk(Context context){
        context.startService(new Intent(context, TaskService.class));
    }


    /**
    * @method  
    * @description 获取视频缩略图
    * @date: 2021/6/28 16:15
    * @author: wangp
    * @param videoPath 视频url
    * @param times 第几秒
    * @return
    */
    public Bitmap getVideoAlbumImg(String videoPath,long times) {
        Bitmap b = null;
        //FFmpegMediaMetadataRetriever
        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();

        try {
            retriever.setDataSource(videoPath);
            //获取2秒处的一帧图片（这里的2000000是微秒！！！2000000）
            b = retriever.getFrameAtTime(times * 1000 * 1000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
                return null;
            }
        }
        return b;
    }
    
    /**
    * @method
    * @description 重新保存指定图片
    * @date: 2021/6/29 10:40
    * @author: wangp
    * @param 
    * @return 
    */
    public void updataScannerImg(Context context, Bitmap bmp, String imgpath){
        String[] imgs  = imgpath.split("/");
        com.hch.myutils.utils.FileUtil.deldeteFileByPath(imgpath);
        BitMapUtils.saveToSystemGallery(context, bmp, imgs[imgs.length-1]);
    }

    /**
    * @method
    * @description 重新获取视频缩略图并更新图片数据源
    * @date: 2021/6/28 16:53
    * @author: wangp
     * @param videoPath 视频url
     * @param times 第几秒
     * @param imgpath 图片绝对路径
    * @return
    */
    public void reacQuireImg(Context context,String videoPath,long times,String imgpath){
       Bitmap bitmap = getVideoAlbumImg(videoPath,times);
       if (bitmap!=null){
           updataScannerImg(context,bitmap,imgpath);
       }
    }

    /**
     * 获取本地视频数据
     * @param context
     * @return
     * @throws IOException
     */
    public String getVideoData(Context context) throws IOException {
//        return ACache.get(context).getAsString(Config.VIDEO_JSON);
        return FileUtil.readTxtFile(new File(Config.getVideoPath(context) + "/json"));
    }


    public void getUnityData(Context context){
        Intent intent = new Intent();
        intent.setAction(Config.ACTION_UNITY_DATA);
        context.sendBroadcast(intent);
    }





}
