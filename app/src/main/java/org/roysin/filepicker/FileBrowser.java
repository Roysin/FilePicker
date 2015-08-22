package org.roysin.filepicker;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/8/17.
 */
public class FileBrowser {

    private  Context mContext;
    private List mOnLoadDirListenerList;
    private static  FileBrowser instance;

    private FileBrowser(Context context){

        this.mContext=context;
    }

    public static FileBrowser  getInstance(Context context){
        if(instance==null){
            instance=new FileBrowser(context);
        }
        return instance;
    }

    public List<FileItemData> loadDir(String dirPath){

        if(FileBrowserUtils.checkPathIllegal(dirPath)){
            Toast.makeText(mContext,"LoadDir error: Directory " +
                    "path is illegal",Toast.LENGTH_SHORT).show();
            onLoadError();
            return null;
        }
        File file = new File(dirPath);
        if(!file.exists()){
            Toast.makeText(mContext,"loadDir error: file " +
                    "does not exits",Toast.LENGTH_SHORT).show();
            onLoadError();
            return null;
        }

        if(file.isDirectory()){
            File [] dirArray = file.listFiles();
            List<FileItemData> fileList =new ArrayList<FileItemData>();
            for(File f : dirArray){
                FileItemData fileItemData =new FileItemData(f);
                fileList.add(fileItemData);
            }
            onLoadComplete();
            return fileList;
        }

        Toast.makeText(mContext,"loadDir error: path refers " +
                "to a file instead of a directory",Toast.LENGTH_SHORT).show();
        onLoadError();
        return null;

    }

    private void onLoadError() {
        List onLoadDirListenerList=getonLoadDirListenerList();
        if(onLoadDirListenerList!=null){

            Iterator i=onLoadDirListenerList.iterator();

            while(i.hasNext()){
                ((onLoadDirListener) i.next()).onLoadDirError();
            }
        }

    }


    private void onLoadComplete() {
        List onLoadDirListenerList=getonLoadDirListenerList();
        if(onLoadDirListenerList!=null){

            Iterator i=onLoadDirListenerList.iterator();

            while(i.hasNext()){
                ((onLoadDirListener) i.next()).onLoadDirComplete();
            }
        }
    }

    public void addOnLoadDirListener(onLoadDirListener listener){
        if(mOnLoadDirListenerList==null){
            mOnLoadDirListenerList=new ArrayList<onLoadDirListener>();
        }
        mOnLoadDirListenerList.add(listener);

    }

    private List<onLoadDirListener> getonLoadDirListenerList() {
        return mOnLoadDirListenerList;
    }



    public interface onLoadDirListener{
       void  onLoadDirComplete();
       void  onLoadDirError();
    }





}
