package com.xunixianshi.filelibs.work;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.xunixianshi.filelibs.MLog;
import com.xunixianshi.filelibs.SimpleSharedPreferences;
import com.xunixianshi.filelibs.parse.ResultPaser;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class ScannerThread implements Runnable  {
    private List<VideoBean> mVideoBeanList = new ArrayList<VideoBean>();

    private Handler handler;
    private Context context;

    public ScannerThread(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;

    }

    @Override
    public void run() {
//        BitMapUtils.fileDeleteWhile(context);
//        mVideoBeanList=getVideoFile(mVideoBeanList, Environment.getExternalStorageDirectory());
        mVideoBeanList.clear();
        MLog.d("ScannerThread----run");
        getLocalVideo();

    }

    /**
     * 获取视频文件
     *
     * @param list
     * @param file
     * @return
     */
    private List<VideoBean> getVideoFile(final List<VideoBean> list, File file) {

        file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {

                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4")
                            || name.equalsIgnoreCase(".3gp")
                            || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts")
                            || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov")
                            || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi")
                            || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp")
                            || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv")
                            || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx")
                            || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm")
                            || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram")
                            || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8")
                            || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v")
                            || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra")
                            || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid")) {
                        VideoBean video = new VideoBean();
                        file.getUsableSpace();
                        video.setVideoTitle(file.getName());
                        video.setVideoPath(file.getAbsolutePath());
                        list.add(video);
                        return true;
                    }
                    //判断是不是目录
                } else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });

        return list;
    }


    private void getLocalVideo() {
        MLog.d(" getLocalVideo  times:" + getFormatNowDate());
        try {
            String[] VIDEO_COLUMN = {
                    MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.SIZE, MediaStore.Audio.Media.DURATION,
                    MediaStore.Video.Media.DATE_ADDED}; //DATE_ADDED :媒体添加时间

            Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    VIDEO_COLUMN, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor
                            .getColumnIndex(VIDEO_COLUMN[0]));
                    String path = cursor.getString(cursor
                            .getColumnIndex(VIDEO_COLUMN[1]));
                    String size = cursor.getString(cursor
                            .getColumnIndex(VIDEO_COLUMN[2]));
                    String duration = cursor.getString(cursor
                            .getColumnIndex(VIDEO_COLUMN[3]));

                    String lastTime = cursor.getString(cursor
                            .getColumnIndex(VIDEO_COLUMN[4]));
                    VideoBean videoBean = new VideoBean();
                    MLog.d("getLocalVideo path--->" + path);

                    String imgName = "Vrshow_" + name.replace(".mp4","") + ".png";

                    String PATH = "";
                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
                        PATH = Environment.getExternalStorageDirectory()
                                .getAbsolutePath() + File.separator + "vrshow/";
                    } else {// 如果SD卡不存在，就保存到本应用的目录下
                        PATH = context.getFilesDir().getAbsolutePath()
                                + File.separator + "vrshow/";
                    }

                   String errorPath = SimpleSharedPreferences.getString("path",context,"");
                    if (!TextUtils.isEmpty(errorPath)){
                        FileUtil.deldeteFileByPath(errorPath);
                        FileUtil.updateMedia(context,errorPath);
                        SimpleSharedPreferences.remove("path",context);

                        getLocalVideo();
                        return;
                    }

                     String imgPath = PATH + imgName;

                    File fileDir = new File(imgPath);
                    String firstImgpath = null;
                    if (!fileDir.exists()) {
                        Bitmap b = null;

                        //FFmpegMediaMetadataRetriever
//                        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();

                        try {
                            b = getVideoAlbum(context.getContentResolver(),path);
//                            if (b ==null){
//                                MLog.d("getVideoAlbum----=====null");
//                                b = BitmapFactory.decodeResource(context.getResources(), R.mipmap.vrshow_null_default);
//                            }
//                            retriever.setDataSource(path);
//                            //获取2秒处的一帧图片（这里的2000000是微秒！！！2000000）
//                            b = retriever.getFrameAtTime(4 * 1000 * 1000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);

                            firstImgpath = BitMapUtils.saveToSystemGallery(context, b, imgName);

                        } catch (Exception e) {
                            e.printStackTrace();
                            cursor.close();

                            SimpleSharedPreferences.putString("path",path,context);

                            MLog.d("mVideoBeanList--->" + mVideoBeanList.size() + " error path:" + path );
                                FileUtil.deldeteFileByPath(path);
                                FileUtil.updateMedia(context,path);
                                getLocalVideo();


                           /* if (mVideoBeanList.size()>0 && mVideoBeanList!=null){
                                String resultJson = ResultPaser.JsonParse(mVideoBeanList);
                                MLog.d("videoInfos:" + resultJson);
                                Message message = new Message();
                                message.obj = resultJson;
                                message.what = 1;
                                handler.sendMessage(message);
                            }*/
                            return;
                        } finally {
//                            retriever.release();
                            try {
                                if ( b!=null){
                                    b.recycle();
                                }
                            } catch (RuntimeException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                        MLog.d("saveToSystemGallery filterVideo--->" + firstImgpath);

                        if (!TextUtils.isEmpty(firstImgpath) && firstImgpath!=null){
                            videoBean.setFirstImg(firstImgpath);
                        }else {
                            videoBean.setFirstImg(imgPath);
                        }
                        MLog.d("saveToSystemGallery imgPath--->" + imgPath);

                        videoBean.setVideoTitle(name);
                        videoBean.setVideoPath(path);
                        videoBean.setVideoSize(Long.valueOf(size));
                        videoBean.setTime(duration);
                        videoBean.setVideoType(4);
                        videoBean.setLastTime(Long.valueOf(lastTime));
                        mVideoBeanList.add(videoBean);
                }
                while (cursor.moveToNext());
                cursor.close();
                if (mVideoBeanList.size()>0 && mVideoBeanList!=null){
                    String resultJson = ResultPaser.JsonParse(mVideoBeanList);
                    MLog.d("videoInfos:" + resultJson);
                    Message message = new Message();
                    message.obj = resultJson;
                    message.what = 1;
                    handler.sendMessage(message);
                }


            }else {
                MLog.d("videoInfos 无数据");

                Message message = new Message();
                message.obj = "";
                message.what = 1;
                handler.sendMessage(message);
            }
        }catch (Exception e){
            e.printStackTrace();
            MLog.d("Exception:" + e.getCause());

            if (mVideoBeanList.size()>0 && mVideoBeanList!=null){
                String resultJson = ResultPaser.JsonParse(mVideoBeanList);
                MLog.d("videoInfos:" + resultJson);
                Message message = new Message();
                message.obj = resultJson;
                message.what = 1;
                handler.sendMessage(message);
            }
        }


    }
    /**
    * @method
    * @description  查询本地视频媒体库获取缩略图
    * @date: 2021/6/28 9:28
    * @author: wangp
    * @param 
    * @return 
    */
    private Bitmap getVideoAlbum(ContentResolver cr, String fileName) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//select condition.
        String whereClause = MediaStore.Video.Media.DATA + "='" + fileName + "'";
        MLog.d( "getVideoAlbum where"+ whereClause);
//colection of results.
        Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Video.Media._ID }, whereClause,
                null, null);

        MLog.d( "getVideoAlbum cursor = "+ cursor);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
//image id in image table.
        String videoId = cursor.getString(cursor
                .getColumnIndex(MediaStore.Video.Media._ID));
        MLog.d( "getVideoAlbum videoId = "+ videoId);
        if (videoId == null) {
            return null;
        }
        cursor.close();
        long videoIdLong = Long.parseLong(videoId);
//via imageid get the bimap type thumbnail in thumbnail table.
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoIdLong,
                MediaStore.Images.Thumbnails.MICRO_KIND, options);
        if (bitmap==null){
            return null;
        }
        MLog.d( "getVideoAlbum bitmap = "+ bitmap);
        return bitmap;

    }

    public static String getFormatNowDate() {
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retStrFormatNowDate = sdFormatter.format(nowTime);

        return retStrFormatNowDate;
    }

    /**
     * 获取视频的缩略图
     * 提供了一个统一的接口用于从一个输入媒体文件中取得帧和元数据。
     *
     * @param path 视频的路径
     * @return 缩略图
     */
    public static Bitmap createVideoThumbnail(String path/*, int width, int height*/) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        try {
            retriever.setDataSource(path);
            bitmap = retriever.getFrameAtTime(200); //取得指定时间的Bitmap，即可以实现抓图（缩略图）功能
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }

        if (bitmap == null) {
            return null;
        }

//        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        bitmap = Bitmap.createBitmap(bitmap);
        return bitmap;

    }
}
