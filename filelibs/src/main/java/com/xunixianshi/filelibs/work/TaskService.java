package com.xunixianshi.filelibs.work;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.gson.reflect.TypeToken;
import com.video.detect.utils.BitmapUtils;
import com.xunixianshi.colorartlibs.ColorArt;
import com.xunixianshi.filelibs.MLog;
import com.xunixianshi.filelibs.ScannerSdk;
import com.xunixianshi.filelibs.SimpleSharedPreferences;
import com.xunixianshi.filelibs.parse.ResultPaser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;
import wseemann.media.FFmpegMediaMetadataRetriever;

import static com.video.detect.utils.DetectUtil.compareBitmap;
import static com.video.detect.utils.DetectUtil.getFrameAtTime;
import static com.video.detect.utils.DetectUtil.getMediaDuration;
import static com.video.detect.utils.DetectUtil.getMediaMetadataRetriever;
import static com.video.detect.utils.DetectUtil.is360;

public class TaskService extends Service {
    private String resultJson = "";
    private String PATH = "";
    private TelephonyManager mTelephonyManager;
    private PhoneStatListener mPhoneStatListener;
    private SignalStrength signal;
    private NetWorkBroadCastReciver mNetWorkBroadCastReciver;
    private int SignalStrength;
    private Context mContext;
    private String AUTH_JSON = "";
    private int phonepPower = -1;
    private int max,current;
    private CompositeDisposable mDisposable;

    private List<VideoBean> sourdataList  = new ArrayList<>();

    private List<String> pathList = new ArrayList<>();

//    private ScannerThread mScannerThread;

    private Handler handler =  new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                resultJson = (String) msg.obj;
                MLog.d("获取到数据了：" + TextUtils.isEmpty(resultJson) + "times:" + ScannerThread.getFormatNowDate());
                 removeMessages(1);

                String RESULT_JSON = SimpleSharedPreferences.getString(com.xunixianshi.filelibs.work.Config.RESULT_JSON, mContext,"");
                 if (!TextUtils.isEmpty(resultJson)){

                     if (!TextUtils.isEmpty(RESULT_JSON)){
                         List<VideoBean> newJson = ResultPaser.paserCollection(resultJson,new TypeToken<List<VideoBean>>(){}.getType());
                         List<VideoBean> oldJson = ResultPaser.paserCollection(RESULT_JSON,new TypeToken<List<VideoBean>>(){}.getType());

                         if (newJson.size()!=oldJson.size()){
                             MLog.d("获取到数据了,数据有更新，开始执行获取视频识别逻辑。");
                             handler.obtainMessage(3,resultJson).sendToTarget();
                         }else {
                             MLog.d("获取到数据了,数据无更新，不做视频识别逻辑");
                         }
                     }else {
                         handler.obtainMessage(3,resultJson).sendToTarget();
                     }


                 }else {
                     String PATH = "";
                     if (Environment.getExternalStorageState().equals(
                             Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
                         PATH = Environment.getExternalStorageDirectory()
                                 .getAbsolutePath() + File.separator + "vrshow/";
                     } else {// 如果SD卡不存在，就保存到本应用的目录下
                         PATH = getFilesDir().getAbsolutePath()
                                 + File.separator + "vrshow/";
                     }
                     FileUtil.deldeteFileByPath(PATH);
                 }


            }else if (msg.what == 2 ){
                MLog.d("出现异常， 检测...");

            }else if (msg.what == 3 ){
                removeMessages(3);

                MLog.d("获取到数据，开始获取视频类型111。。。");

                resultJson = (String) msg.obj;

                ThreadUtilWork.getInstance().execute(new Runnable() {
                    @Override
                    public void run() {

                        sourdataList = ResultPaser.paserCollection(resultJson,new TypeToken<List<VideoBean>>(){}.getType());

                        MLog.d("获取到数据，开始获取视频类型22222。。。");
                       int detectVideo;

                      for (VideoBean mVideoBean:sourdataList){
                              detectVideo =  detectVideo(TaskService.this,mVideoBean);
                              if (detectVideo == 6){
//                            result = IMediaPlayerVRControl.VIDEO_TYPE_L_R_3D
                                  detectVideo = 2;
                              }
                              mVideoBean.setVideoType(detectVideo);
                      }

                        MLog.d("获取到数据，开始压缩视频封面。。。");
                        for (VideoBean videoBean :sourdataList){
                          withRx(videoBean.getFirstImg());
                      }


                    }
                });


            }

        }
    };

    /**
    * @method
    * @description
    * @date: 2021/7/1 15:07
    * @author: wangp
    * @param 
    * @return 
    */
    private  void withRx(final String path) {
        //同步处理
//        Flowable.just(path)
//                .observeOn(Schedulers.io())
//                .map(new Function<String, Object>() {
//                    @Override
//                    public Object apply(String path) throws Exception {
//
//                        return Luban.with(mContext).load(path)
//                                .setTargetDir(getPath())
//                                .setRenameListener(new OnRenameListener() {
//                            @Override
//                            public String rename(String filePath) {
//                                String[] imgs  = filePath.split("/");
//                                MLog.d("rename----------->" + filePath);
//                                return imgs[imgs.length-1];
//                            }
//                        }).get();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();

//异步处理
        Luban.with(this)
                .load(path)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        String[] imgs  = filePath.split("/");
                        MLog.d("rename----------->" + filePath);
                        return imgs[imgs.length-1];
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() { }

                    @Override
                    public void onSuccess(File file) {
                        String newPathstr  = file.getAbsolutePath();
                        sendSdcardData(newPathstr);
                    }

                    @Override
                    public void onError(Throwable e) {

                        MLog.d("OnCompressListener onError----------->" + e.getMessage());
                    }
                }).launch();

    }


    private void sendSdcardData(String newPathstr) {

        MLog.d("获取到数据,sendSdcardData newPathstr--->" + newPathstr);

        MLog.d("获取到数据，sendSdcardData 压缩完毕，开始更新数据源");

        String[] imgss = newPathstr.split("/");
        String imgname = imgss[imgss.length - 1];

        for (VideoBean videoBean : sourdataList) {
            if (videoBean.getFirstImg().contains(imgname)) {
                MLog.d("获取到数据，压缩完毕，开始更新数据源11111111111:" + imgname);

                if (newPathstr.contains("localImg")){
                    FileUtil.deldeteFileByPath(videoBean.getFirstImg());
                }

                videoBean.setFirstImg(newPathstr);


            }
        }

        //获取最终数据
        String result = ResultPaser.JsonParse(sourdataList);
        SimpleSharedPreferences.putString(Config.RESULT_JSON, result, TaskService.this);
        try {
            File fileDir = new File(Config.getVideoPath(TaskService.this));
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            File file = new File(fileDir, "json");
            FileUtil.writeTxtFile(result, file);
            MLog.d("获取到数据，压缩完毕 更新数据源完毕。。。");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "vrshow/localImg/";

        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    

    /**
    * @method
    * @description 解析视频格式
    * @date: 2021/6/29 10:27
    * @author: wangp
    * @param
    * @return 
    */
    public static int detectVideo(Context context,VideoBean mVideoBean) {
        FFmpegMediaMetadataRetriever media = getMediaMetadataRetriever(mVideoBean.getVideoPath());
        long duration = getMediaDuration(media);
        long start = duration * 2L / 7L;
        long first = start;
        long step = start / 5L;
        int type = 0;

        boolean isget = false;

        boolean is360;
        Bitmap src;

        for(is360 = false; start < first + step * 3L && start < duration; start += step) {
            src = getFrameAtTime(media, start);

            if (src != null) {
                if (!isget){
                    isget =true;

                    String imgpath = mVideoBean.getFirstImg();
                    Bitmap bitmap =  BitmapFactory.decodeFile(imgpath);
                    if (bitmap !=null){
                        ColorArt mColorArt = new ColorArt(bitmap);
                        if (mColorArt.getPrimaryColor() == -1  && mColorArt.getSecondaryColor() == -1 && mColorArt.getDetailColor() == -1){
                            MLog.d("detectVideo-----此时解析到封面为黑图，开始重新获取---");
                            ScannerSdk.getInstans().updataScannerImg(context,src,imgpath);
                        }
                    }

                }
                try {
                    type = compareBitmap(src);
                    if (type != 0) {
                        int width = src.getWidth();
                        int height = src.getHeight();
                        if (type == 1) {
                            is360 = is360(src, false);
                        } else {
                            Rect rect;
                            Bitmap left;
                            if (type == 101) {
                                rect = new Rect();
                                rect.left = 0;
                                rect.top = 0;
                                rect.right = width;
                                rect.bottom = height / 2;
                                left = BitmapUtils.getRectBitmap(src, rect);
                                is360 = is360(left, true);
//                                left.recycle();
                            } else if (type == 202) {
                                rect = new Rect();
                                rect.left = 0;
                                rect.top = 0;
                                rect.right = width / 2;
                                rect.bottom = height;
                                left = BitmapUtils.getRectBitmap(src, rect);
                                is360 = is360(left, true);
//                                left.recycle();
                            }
                        }
                        break;
                    }
                } catch (IOException var17) {
                    var17.printStackTrace();
                }
//                if (!src.isRecycled()){
//                    src.recycle();
//                }
            }
        }

        if (type == 0) {
            src = getFrameAtTime(media, start);
            if (src != null) {
                is360 = is360(src, false);
//                if (!src.isRecycled()){
//                    src.recycle();
//                }
            }
        }

        media.release();
        if (type == 1) {
            return is360 ? 4 : 1;
        } else if (type == 202) {
            return is360 ? 6 : 2;
        } else if (type == 101) {
            return is360 ? 5 : 3;
        } else {
            return 4;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        sourdataList.clear();
        pathList.clear();
        mDisposable = new CompositeDisposable();

        MLog.d("TaskService----Create----");

        ThreadUtilWork.getInstance().execute(new ScannerThread(this,handler));

//        mScannerThread = new ScannerThread(this,handler);
//        mScannerThread.start();


//获取telephonyManager
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

//开始监听
        mPhoneStatListener = new PhoneStatListener();
        mTelephonyManager.listen(mPhoneStatListener, PhoneStatListener.LISTEN_SIGNAL_STRENGTHS);
        mNetWorkBroadCastReciver = new NetWorkBroadCastReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(mNetWorkBroadCastReciver, intentFilter);
    }

    /**
     * 观察手机带SIM卡时的信号强度
     */
    private class PhoneStatListener extends PhoneStateListener {
        //获取信号强度

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);

            signal=signalStrength;
            //网络信号改变时，获取网络信息
           MLog.d("PhoneStatListener：" + signalStrength.getLevel() + " signal:" + signal);
            getNetWorkInfo();
        }
    }
    //接收网络状态改变的广播
    class NetWorkBroadCastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
          String action =   intent.getAction();
          if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)
                  || action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)
                  || action.equals(WifiManager.RSSI_CHANGED_ACTION)
                  || action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){

              getNetWorkInfo();
          }

        }
    }


    /**
     * 获取网络的信息
     */
    private void getNetWorkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            switch (info.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    //wifi
                    WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    WifiInfo connectionInfo = manager.getConnectionInfo();
                    int rssi = Math.abs(connectionInfo.getRssi());

                    if (rssi < 50 && rssi > 0) {
                        SignalStrength = 1;
                    } else if (rssi < 70 && rssi >= 50) {
                        SignalStrength = 2;
                    } else if (rssi < 80 && rssi >= 70) {
                        SignalStrength = 3;
                    } else if ((rssi <= 100 && rssi >= 80) || rssi > 100) {
                        SignalStrength = 4;
                    }
                    SimpleSharedPreferences.putInt("SignalStrength",SignalStrength,this);

                    MLog.d("当前为wifi网络，信号强度=" + rssi + "  SignalStrength:" + SignalStrength);
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    //移动网络,可以通过TelephonyManager来获取具体细化的网络类型

                    getMobileDbm();
                    break;
            }
        } else {
            MLog.e("没有可用网络"  );
        }
    }

    public void getMobileDbm()
    {
        int dbm = -1;
        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        List<CellInfo> cellInfoList = tm.getAllCellInfo();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            if (null != cellInfoList)
            {
                for (CellInfo cellInfo : cellInfoList)
                {
                    if (cellInfo instanceof CellInfoGsm)
                    {
                        CellSignalStrengthGsm cellSignalStrengthGsm = ((CellInfoGsm)cellInfo).getCellSignalStrength();
                        dbm = Math.abs(cellSignalStrengthGsm.getDbm());
                        MLog.e( "cellSignalStrengthGsm" + cellSignalStrengthGsm.toString());
                    }
                    else if (cellInfo instanceof CellInfoCdma)
                    {
                        CellSignalStrengthCdma cellSignalStrengthCdma = ((CellInfoCdma)cellInfo).getCellSignalStrength();
                        dbm = cellSignalStrengthCdma.getDbm();
                        MLog.e( "cellSignalStrengthCdma" + cellSignalStrengthCdma.toString() );
                    }
                    else if (cellInfo instanceof CellInfoWcdma)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                        {
                            CellSignalStrengthWcdma cellSignalStrengthWcdma = ((CellInfoWcdma)cellInfo).getCellSignalStrength();
                            dbm = Math.abs(cellSignalStrengthWcdma.getDbm());
                            MLog.e( "cellSignalStrengthWcdma" + cellSignalStrengthWcdma.toString() );
                        }
                    }
                    else if (cellInfo instanceof CellInfoLte)
                    {
                        CellSignalStrengthLte cellSignalStrengthLte = ((CellInfoLte)cellInfo).getCellSignalStrength();
                        dbm = Math.abs(cellSignalStrengthLte.getDbm());
//                        MLog.e( "cellSignalStrengthLte.getAsuLevel()\t" + cellSignalStrengthLte.getAsuLevel() );
//                        MLog.e( "cellSignalStrengthLte.getCqi()\t" + cellSignalStrengthLte.getCqi() );

//                        MLog.e( "cellSignalStrengthLte.getLevel()\t " + cellSignalStrengthLte.getLevel() );
//                        MLog.e( "cellSignalStrengthLte.getRsrp()\t " + cellSignalStrengthLte.getRsrp() );
//                        MLog.e( "cellSignalStrengthLte.getRsrq()\t " + cellSignalStrengthLte.getRsrq() );
//                        MLog.e( "cellSignalStrengthLte.getRssnr()\t " + cellSignalStrengthLte.getRssnr() );
//                        MLog.e( "cellSignalStrengthLte.getTimingAdvance()\t " + cellSignalStrengthLte.getTimingAdvance() );
                    }
                }

                if (dbm < 50 && dbm > 0) {
                    SignalStrength = 1;
                } else if (dbm < 70 && dbm >= 50) {
                    SignalStrength = 2;
                } else if (dbm < 80 && dbm >= 70) {
                    SignalStrength = 3;
                } else if ((dbm <= 100 && dbm >= 80) || dbm > 100) {
                    SignalStrength = 4;
                }

                SimpleSharedPreferences.putInt("SignalStrength",SignalStrength,this);
                MLog.e("信号强度=" + SignalStrength  + "  dbm:" + dbm);

            }
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
//        BitMapUtils.fileDeleteWhile(this);
        stopSelf();
        unregisterReceiver(mNetWorkBroadCastReciver);
        ThreadUtilWork.getInstance().destroy();

    }
}
