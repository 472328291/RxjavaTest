package com.example.rxjavatest;

import android.util.Log;

public class StringLogcatUtil {
    public  static  void StringSubstring(String bodyMsg){
//        我们采取分段打印日志的方法：当长度超过4000时，我们就来分段截取打印
        //剩余的字符串长度如果大于4000
        if (bodyMsg.length() > 4000) {
            for (int i = 0; i < bodyMsg.length(); i += 4000) {
                //当前截取的长度<总长度则继续截取最大的长度来打印
                if (i + 4000 < bodyMsg.length()) {
                    Log.i("bodyMsg_" , bodyMsg.substring(i, i + 4000));
                } else {
                    //当前截取的长度已经超过了总长度，则打印出剩下的全部信息
                    Log.i("bodyMsg_" , bodyMsg.substring(i, bodyMsg.length()));
                }
            }
        } else {
            //直接打印
            Log.i("bodyMsg_", bodyMsg);
        }

    }
}
