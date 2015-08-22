package org.roysin.filepicker;


import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2015/8/17.
 */
public class FileItemData {

    private final String TAG="FileItemData";
    public static final int TYPE_DIR=0x10;
    public static final int TYPE_FILE=0x11;
    public static final int TYPE_UNKNOWN=0X12;

    private String mPath;
    private long mSize;
    private String mSizeWithUnit;
    private int mType;


    public FileItemData(String path){
        mPath=path;
        if(mPath==null){
            mPath=new String("/");
        }

        fillData(mPath);
    }


    public FileItemData(File file){
        this(file.getPath());
    }

    private void fillData(String path){

        if(path == null) {
            Log.d(TAG,"fillData returned because file path is null");
            return ;
        }

        if(FileBrowserUtils.checkPathIllegal(path)) {
            Log.d(TAG,"fillData returned because path is illegal");
            return ;
        }

        File file =new File(path);
        if(!file.exists()) {
            Log.d(TAG,"fillData returned because file does not exists");
            return;
        }
        if(file.isDirectory()){

            mType=TYPE_DIR;
            File[] filesArray = file.listFiles();
            if(filesArray!=null) {
                mSize = filesArray.length;
            }else {
                mSize=0;
            }
            Log.d(TAG,"fill Data"+" directorySize ="+mSize);
            filesArray=null;

        }else {
            mType=TYPE_FILE;
            mSize=file.length();
            mSizeWithUnit=makeSizeWithUnit(mSize);
        }


    }

    private String makeSizeWithUnit(long size) {

        float tmpSize=size;
        String unit="B";

        if(size>=1024){
            tmpSize=size / 1024.f;
            unit="KB";
        }

        if(tmpSize>=1024){
            tmpSize/=1024.f;
            unit="MB";
        }

        if(tmpSize>1024){
            if(tmpSize>=1024){
                tmpSize/=1024.f;
                unit="GB";
            }
        }
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");
        Log.d(TAG,"sizeWithUnit= "+df.format(tmpSize)+" "+unit);
        return df.format(tmpSize)+" "+unit;
    }


    /**
     * get the pathList. if the data has not been loaded then return null
     * @return ArrayList<String> , null if fillData() wasn't called.
     */
    public String getPath(){
        return mPath;
    }

    public long getSize(){
        return mSize;
    }

    public int getType(){
        return mType;
    }

    public String getSizeWithUnit(){
        return mSizeWithUnit;
    }
}