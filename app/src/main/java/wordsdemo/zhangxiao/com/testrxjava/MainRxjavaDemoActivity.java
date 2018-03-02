package wordsdemo.zhangxiao.com.testrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainRxjavaDemoActivity extends AppCompatActivity {

    private static final String TAG = "testrxjava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.main_text);
        textView.setText("rxjavademo");

//        initRxDemoInterval();
//        initRxDemoRepeatWhen();

//        initRxjavaDemoMergeReq();
//        initRxjavaDemoZipReq();
        initRxjavaMemory();
    }

    private void initRxDemoInterval() {
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "第 " + aLong + " 次轮询");

                        GetRequestInterface service = ServiceGenerator.createService(GetRequestInterface.class);
                        Observable<Translation> reqTranslation = service.getCall();

                        reqTranslation.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Translation translation) {
                                        // e.接收服务器返回的数据
                                        translation.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(TAG, "请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "对next事件作出响应" + aLong);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    private int repeatCount = 0;

    private void initRxDemoRepeatWhen() {

        GetRequestInterface service = ServiceGenerator.createService(GetRequestInterface.class);
        Observable<Translation> reqTranslation = service.getCall();

        reqTranslation.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                Log.d(TAG, "第 " + repeatCount + " 次重复");
                                if (repeatCount > 3) {
                                    return Observable.error(new Throwable("轮询结束"));
                                }

                                return Observable.just(1).delay(2, TimeUnit.SECONDS);
                            }
                        });
                    }
                }).subscribe(new Observer<Translation>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Translation translation) {
                Log.d(TAG, "对next事件作出响应");
                translation.show();
                repeatCount++;
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    private void initRxjavaDemoSequence() {
        GetRequestInterface service = ServiceGenerator.createService(GetRequestInterface.class);
        Observable<Translation1> reqTranslation1 = service.getCall1();
        Observable<Translation2> reqTranslation2 = service.getCall2();

        reqTranslation1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Translation1>() {
                    @Override
                    public void accept(Translation1 translation) throws Exception {
                        Log.d(TAG, "第1次网络请求成功");
                        translation.show();
                        // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                }).observeOn(Schedulers.io())
                .flatMap(new Function<Translation1, ObservableSource<Translation2>>() {
                    @Override
                    public ObservableSource<Translation2> apply(Translation1 translation1) throws Exception {
                        return reqTranslation2;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Translation2>() {
                    @Override
                    public void accept(Translation2 translation2) throws Exception {
                        Log.d(TAG, "第2次网络请求成功");
                        translation2.show();
                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("登录失败");
                    }
                });

    }

    private String result = "数据源来自 =";

    private void initRxjavaDemoMergeReq() {


        Observable<String> network = Observable.just("网络");
        Observable<String> file = Observable.just("本地");

        Observable.merge(network, file)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "数据源有： " + s);
                        result += s + "+";
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    // 接收合并事件后，统一展示
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "获取数据完成");
                        Log.d(TAG, result);
                    }
                });
    }

    private void initRxjavaDemoZipReq() {
        GetRequestInterface service = ServiceGenerator.createService(GetRequestInterface.class);
        Observable<TranslationZip1> observable1 = service.getCallZip1().subscribeOn(Schedulers.io());
        Observable<TranslationZip2> observable2 = service.getCallZip2().subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<TranslationZip1, TranslationZip2, String>() {
            @Override
            public String apply(TranslationZip1 translationZip1, TranslationZip2 translationZip2) throws Exception {
                return translationZip1.show() + " & " + translationZip2.show();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        // 结合显示2个网络请求的数据结果
                        Log.d(TAG, "最终接收到的数据是：" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("登录失败");
                    }
                });
    }

    private void initRxjavaMemory() {
        String memoryCache = null;
//        String diskCache = "从磁盘缓存中获取数据";
        String diskCache = null;

        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "subscribe: memory");
                if (memoryCache != null) {
                    emitter.onNext(memoryCache);
                } else {
                    emitter.onComplete();
                }
            }
        });

        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "subscribe: disk");
                if (diskCache != null) {
                    emitter.onNext(diskCache);
                } else {
                    emitter.onComplete();
                }
            }
        });

        Observable<String> net = Observable.just("从网络进行获取");

        Observable.concat(memory,disk,net)
                .firstElement()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG,"最终获取的数据来源 =  "+ s);
                    }
                });
    }

    private void initRxJavaDemo(){


    }

}
