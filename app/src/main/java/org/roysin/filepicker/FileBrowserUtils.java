package org.roysin.filepicker;

import android.util.Log;

/**
 * Created by Administrator on 2015/8/21.
 */
public class FileBrowserUtils {

    private static final String TAG ="FileBrowserUtils";

    public static  boolean checkPathIllegal(String dirPath) {

        if(dirPath==null){
            return true;
        }
        Log.d(TAG,"checkPathIllegal path = "+dirPath);
        if (dirPath.contains("+")||dirPath.contains("-")||
                dirPath.contains(",")||dirPath.contains("=")){
            return true;
        }
        return false;
    }

}
