/**
 * Copyright (C) 2015  Haiyang Yu Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xunixianshi.filelibs.work;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.blankj.utilcode.util.ImageUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author hechuang
 * @ClassName: GlideUtils
 * @Description: TODO 网络图片加载的封装
 * @date 2018/11/13 10:29
 */
public class BitMapUtils {


    public static String saveToSystemGallery(Context context, Bitmap bmp, String saveName) {
        String PATH = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            PATH = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "vrshow";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH = context.getFilesDir().getAbsolutePath()
                    + File.separator + "vrshow";
        }
        // 首先保存图片
        File fileDir = new File(PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, saveName);
        if (file.exists()){
            return "";
        }
        if (bmp == null) {
            return "";
        }
        ImageUtils.save(bmp,file,Bitmap.CompressFormat.PNG);
        return PATH + File.separator + saveName;
    }


    public static String saveToSystemGallery(Context context, Bitmap bmp, String saveName,String imgpath) {
        String PATH = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            PATH = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "vrshow";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH = context.getFilesDir().getAbsolutePath()
                    + File.separator + "vrshow";
        }
        // 首先保存图片
        File fileDir = new File(PATH);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(fileDir, saveName);
        if (file.exists()){
            return imgpath;
        }
        if (bmp == null) {
            return imgpath;
        }

        ImageUtils.save(bmp,file,Bitmap.CompressFormat.PNG);
        return PATH + File.separator + saveName;
    }


    //鲁班压缩工具-异步
//        Luban.with(context)
//                .ignoreBy(100)
//                .setTargetDir(PATH)
//                .filter(new CompressionPredicate() {
//                    @Override
//                    public boolean apply(String path) {
//                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                    }
//                })
//                .setCompressListener(new OnCompressListener() {
//                    @Override
//                    public void onStart() {
//                        MLog.d("BitMapUtils--------onStart");
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        MLog.d("BitMapUtils--------onSuccess");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        MLog.d("BitMapUtils--------onError");
//                    }
//                }).launch();



    public static void fileDeleteWhile(Context context) {
        String PATH = "";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            PATH = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "vrshow";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH = context.getFilesDir().getAbsolutePath()
                    + File.separator + "vrshow";
        }

        File file = new File(PATH);
        File[] files = file.listFiles();
        if (files == null) return;
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }

    /**
     * 把batmap 转file
     * @param bitmap
     * @param filepath
     */
    public static File saveBitmapFile(Bitmap bitmap, String filepath){
        File file=new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据路径 转bitmap
     * @param urlpath
     * @return
     */
    public static Bitmap getBitMBitmap(String urlpath) {

        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * uri 转 File
     * @param uri
     * @return
     */
    public File uriTurnFile(Uri uri, Activity activity){

        if(uri == null){
            return null;
        }

        File file = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
    }

}
