package com.kocla.preparationtools.utils;

import in.srain.cube.util.CLog;

/**
 * Created by admin on 2015/7/5.
 */
public class FileSizeUtil {
    /**
     * @param size
     * @return
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * @param sizeStr
     * @return
     */
    public static String convertFileSize(String sizeStr) {
        CLog.i("FileSizeUtil","文件大小：",sizeStr);
        if(sizeStr == null||sizeStr.equals("")||sizeStr.equals("0")||sizeStr.equals("null")){
            return "0 kb";
        }
        double size;
        try {
            size = Double.parseDouble(sizeStr);
        }catch (Exception e){
            return  "未知大小";
        }
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f G",  size / gb);//GB
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f M" : "%.1f M", f);//MB
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f kb" : "%.1f kb", f);//GB
        } else
            return String.format("%s b", size);
    }
}
