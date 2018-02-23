package wordsdemo.zhangxiao.com.testrxjava;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;

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
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
//        initRange();
//        initRxjavaMap();
//        initRxjavaFlatMap();
//        initConcatMap();
//        initBuffer();
//        initConcat();
//        initMerge();
//        initMergeDelayError();
//        initZip();
//        initCombineLatest();
//        initReduce();
//        initCollect();
//        initStartWidth();
        initCount();

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
                Log.d(TAG, "开始采用subscribe连接接");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "对next " + integer + "事件作出响应");
                if (integer == 2) {
                    disposable.dispose();
                    Log.d(TAG, "对next " + 2 + "进行截断");
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对onError事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对onComplete事件作出响应");
            }
        });
    }

    private void initRxJust() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "just开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "just对next " + integer + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "just对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "just对onComplete事件作出响应");
                    }
                });
    }

    private void initRxFromArray() {
        Integer[] items = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 12, 123, 43, 77};
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "from开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "from对next " + integer + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "from对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "from对onComplete事件作出响应");
                    }
                });
    }

    private void initRxFromIterable() {
        List<String> dataList = new ArrayList<>();
        dataList.add("test1");
        dataList.add("test2");
        dataList.add("test3");
        Observable.fromIterable(dataList)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "FromIterable开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "FromIterable对next " + s + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "FromIterable对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "FromIterable对onComplete事件作出响应");
                    }
                });
    }

    private Integer i = 10;

    private void initRxDefer() {

        Observable<? extends Integer> observable = Observable.defer(() -> Observable.just(i));
        i = 15;

        observable.subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Defer开始采用subscribe连接接");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Defer对next " + integer + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Defer对onError事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Defer对onComplete事件作出响应");
            }
        });
    }

    private void initRxTimer() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "timer开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "timer对next " + aLong + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "timer对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "timer对onComplete事件作出响应");
                    }
                });
    }

    private void initInterval() {
        Observable.interval(3, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "interval开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "interval对next " + aLong + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "interval对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "interval对onComplete事件作出响应");
                    }
                });
    }

    private void initIntervalRange() {
        Observable.intervalRange(3, 10, 3, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "intervalRange开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "intervalRange对next " + aLong + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "intervalRange对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "intervalRange对onComplete事件作出响应");
                    }
                });
    }

    private void initRange() {
        Observable.range(3, 10)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "range开始采用subscribe连接接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "range对next " + integer + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "range对onError事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "range对onComplete事件作出响应");
                    }
                });
    }

    private void testRxjavaRetrofit() {
        Observable.interval(2, 1, TimeUnit.SECONDS)
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
                                    Log.d(TAG, "req error");
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "req开始采用subscribe连接接");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "req对next " + aLong + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "req对onError事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "req对onComplete事件作出响应");
            }
        });
    }

    private void initRxjavaMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer + "的参数从 整型" + integer + " 变换成 字符串类型" + integer;
            }
        }).subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    private void initRxjavaFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> flatList = new ArrayList<>();

                for (int i = 0; i < 3; i++) {
                    flatList.add("我是事件 " + integer + "拆分后的子事件" + i);
                }


                return Observable.fromIterable(flatList);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    private void initConcatMap() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
        }).concatMap(integer -> {
            List<String> flatList = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                flatList.add("我是事件 " + integer + "concat拆分后的子事件" + i);
            }
            return Observable.fromIterable(flatList);
        }).subscribe(consumer ->
                Log.d(TAG, consumer)
        );
    }

    private void initBuffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG, " 缓存区里的事件数量 = " + integers.size());
                        for (Integer value : integers) {
                            Log.d(TAG, " 事件 = " + value);
                        }
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


    private void initConcat() {
        Observable.concat(Observable.just(1, 2, 3)
                , Observable.just(4, 5, 6)
                , Observable.just(7, 8, 9)
                , Observable.just(10, 11, 12))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
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

        Observable.concatArray(Observable.just(1, 2, 3)
                , Observable.just(4, 5, 6)
                , Observable.just(7, 8, 9)
                , Observable.just(10, 11, 12)
                , Observable.just(13, 14, 15))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "array接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "array对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "array对Complete事件作出响应");
                    }
                });
    }

    private void initMerge() {
        Observable.merge(Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS)
                , Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "接收到了事件" + aLong);
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

    private void initMergeDelayError() {

        Observable.concat(
                Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                    emitter.onError(new NullPointerException());
                    emitter.onComplete();
                })
                , Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
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


        Observable.concatArrayDelayError(
                Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                    emitter.onError(new NullPointerException());
                    emitter.onComplete();
                })
                , Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "concatArrayDelayError接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "concatArrayDelayError对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "concatArrayDelayError对Complete事件作出响应");
                    }
                });
    }

    private void initZip() {

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "被观察者1发送了事件1");
                emitter.onNext(1);
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "被观察者1发送了事件2");
                emitter.onNext(2);
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "被观察者1发送了事件3");
                emitter.onNext(3);
                try {

                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "被观察者2发送了事件A");
                emitter.onNext("A");
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "被观察者2发送了事件B");
                emitter.onNext("B");
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "被观察者2发送了事件C");
                emitter.onNext("C");
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "被观察者2发送了事件D");
                emitter.onNext("D");
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "最终接收到的事件 =  " + s);
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

    }

    private void initCombineLatest() {
        Observable.combineLatest(Observable.just(1L, 2L, 3L)
                , Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS)
                , new BiFunction<Long, Long, Long>() {
                    @Override
                    public Long apply(Long aLong, Long aLong2) throws Exception {
                        Log.e(TAG, "合并的数据是： " + aLong + " " + aLong2);
                        return aLong + aLong2;
                    }
                }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                Log.e(TAG, "合并的结果是： "+aLong);
            }
        });
    }

    private void initReduce(){
        Observable.just(1,2,3,4)
                .reduce((integer, integer2) -> {
                    Log.e(TAG, "本次计算的数据是： "+integer +" 乘 "+ integer2);
                    return integer*integer2;
                }).subscribe(integer -> Log.e(TAG, "最终计算的结果是： "+integer));
    }

    private void initCollect(){
        Observable.just(1,2,3,4,5)
                .collect(new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                        integers.add(integer);
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                Log.e(TAG, "本次发送的数据是： "+integers);
            }
        });
    }


    private void initStartWidth(){
        Observable.just(4,5,6)
                .startWith(0)
                .startWithArray(1,2,3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件"+ integer  );
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


        Observable.just(4,5,6)
                .startWith(Observable.just(1,2,3))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "2接收到了事件"+ integer  );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "2对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "2对Complete事件作出响应");
                    }
                });
    }

    private void initCount(){
        Observable.just(1,2,3,4)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "发送的事件数量 =  "+aLong);
                    }
                });
    }
}
