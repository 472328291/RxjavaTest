package com.example.rxjavatest.until;


import android.util.Log;

import com.example.rxjavatest.StringLogcatUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxjavaUntil {
    private static String TAG="RxjavaUntil";
     public static void demo01(){
        //创建一个上游 被观察者
       Observable.create(new ObservableOnSubscribe<Integer>() {
             @Override
             public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                 Log.d(TAG, "emitter1");
                 emitter.onNext(1);
                 Log.d(TAG, "emitter2");
                 emitter.onNext(2);
                 Log.d(TAG, "emitter3");
                 emitter.onNext(3);
                 Log.d(TAG, "emitter4");
                 emitter.onNext(4);
                 Log.d(TAG, "emitter5");
                 emitter.onNext(5);
                 Log.d(TAG, "emitter6");
                 emitter.onNext(6);
                 emitter.onComplete();
             }
         }).subscribe(new Observer<Integer>() {
           private Disposable mDisposable;
           private int i;
           @Override
           public void onSubscribe(Disposable d) {
               Log.d(TAG, "onSubscribe");
               mDisposable=d;
           }

           @Override
           public void onNext(Integer integer) {
               Log.d(TAG, integer+"");
               i++;
               if (i==2){
                   mDisposable.dispose();
                   Log.d(TAG, "isDisposed : " + mDisposable.isDisposed());
               }
           }

           @Override
           public void onError(Throwable e) {
               Log.d(TAG, "onError");
           }

           @Override
           public void onComplete() {
               Log.d(TAG, "onComplete");
           }
       });
         //创建一个下游 观察者
//       new Observer<Integer>() {
//             @Override
//             public void onSubscribe(Disposable d) {
//                 Log.d(TAG, "onSubscribe");
//             }
//
//             @Override
//             public void onNext(Integer integer) {
//                 Log.d(TAG, "onNext"+integer);
//             }
//
//             @Override
//             public void onError(Throwable e) {
//                 Log.d(TAG, "onError");
//             }
//
//             @Override
//             public void onComplete() {
//                 Log.d(TAG, "onComplete");
//             }
//         };
//         //建立连接
//          observable.subscribe(observer);

     }
    public static void demo02(){
         Observable<Integer> observable=Observable.create(new ObservableOnSubscribe<Integer>() {
             @Override
             public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                 Log.d(TAG, Thread.currentThread().getName());
                 emitter.onNext(1);
             }
         });
        Consumer<Integer> consumer=new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, Thread.currentThread().getName());
            }
        };
          observable.subscribeOn(Schedulers.newThread())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(consumer);
    }
    public static void demo03(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
             @Override
             public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                 emitter.onNext(1);
                 emitter.onNext(2);
                 emitter.onNext(5);
             }
         }).flatMap(new Function<Integer, ObservableSource<String >>() {
             @Override
             public ObservableSource<String> apply(Integer integer) throws Exception {
                 final List<String> list = new ArrayList<>();
                 for (int i = 0; i < 3; i++) {
                     list.add("I am value " + integer);
                 }
                 return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
             }
         }).subscribe(new Consumer<String>() {
             @Override
             public void accept(String s) throws Exception {

             }
         });


    }
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> m=new HashMap<>();
        int[] res=new int[2];
        for (int i=0;i<nums.length;i++){
            m.put(nums[i],i);
        }
        for (int i=0;i<nums.length;i++){
            int n= target-nums[i];
            if (m.containsKey(n)&&m.get(n)!=i){
                res[0]=i;
                res[1]=m.get(n);
                break;
            }
        }
        return res;

    }
}
