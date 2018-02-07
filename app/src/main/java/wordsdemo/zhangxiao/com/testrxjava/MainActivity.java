package wordsdemo.zhangxiao.com.testrxjava;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "testrxjava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initRxjava();
//        initRxJust();
//        initRxFromArray();
//        initRxFromIterable();
//        initRxDefer();
//        initRxTimer();
//        initInterval();
//        initIntervalRange();
        initRange();
    }

    private void initRxjava() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Log.d(TAG,"开始采用subscribe连接接");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG,"对next "+integer+"事件作出响应");
                if(integer==2){
                    disposable.dispose();
                    Log.d(TAG,"对next "+2+"进行截断");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"对onError事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"对onComplete事件作出响应");
            }
        });
    }

    private void initRxJust(){
        Observable.just(1,2,3,4,5,6,7,8,9)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"just开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG,"just对next "+integer+"作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"just对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"just对onComplete事件作出响应");
                    }
                });
    }

    private void initRxFromArray(){
        Integer [] items = {1,2,3,4,5,6,7,8,9,0,12,123,43,77};
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"from开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG,"from对next "+integer+"作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"from对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"from对onComplete事件作出响应");
                    }
                });
    }

    private void initRxFromIterable(){
        List<String> dataList = new ArrayList<>();
        dataList.add("test1");
        dataList.add("test2");
        dataList.add("test3");
        Observable.fromIterable(dataList)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"FromIterable开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG,"FromIterable对next "+s+"作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"FromIterable对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"FromIterable对onComplete事件作出响应");
                    }
                });
    }

    private Integer i = 10;
    private void initRxDefer(){

        Observable<? extends Integer> observable = Observable.defer(() -> Observable.just(i));
        i = 15;

        observable.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"Defer开始采用subscribe连接接");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG,"Defer对next "+integer+"作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"Defer对onError事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Defer对onComplete事件作出响应");
            }
        });
    }

    private void initRxTimer(){
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"timer开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG,"timer对next "+aLong+"作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"timer对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"timer对onComplete事件作出响应");
                    }
                });
    }

    private void initInterval(){
        Observable.interval(3,1,TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"interval开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG,"interval对next "+aLong+"作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"interval对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"interval对onComplete事件作出响应");
                    }
                });
    }

    private void initIntervalRange(){
        Observable.intervalRange(3,10,3,1,TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"intervalRange开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG,"intervalRange对next "+aLong+"作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"intervalRange对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"intervalRange对onComplete事件作出响应");
                    }
                });
    }

    private void initRange(){
        Observable.range(3,10)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"range开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG,"range对next "+integer+"作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"range对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"range对onComplete事件作出响应");
                    }
                });
    }

    private void testRxjavaRetrofit(){
        Observable.interval(2,1,TimeUnit.SECONDS)
                .doOnNext(aLong -> {
                    ServiceGenerator.createService(GetRequestInterface.class).getCall()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Translation>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Translation translation) {
                                    translation.show();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG,"req error");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"req开始采用subscribe连接接");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG,"req对next "+aLong+"作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"req对onError事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"req对onComplete事件作出响应");
            }
        });
    }
}
