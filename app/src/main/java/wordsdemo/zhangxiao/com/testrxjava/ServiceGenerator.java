package wordsdemo.zhangxiao.com.testrxjava;

import android.os.Environment;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 张枭
 * @version V1.0
 *          date 2016/8/4
 */
public class ServiceGenerator {

    private static final String TAG = "ServiceGenerator";
    private static final String API_BASE_URL = "http://fy.iciba.com/";
    private static OkHttpClient okHttpClient;

    private static final int REFRESH_TOKEN_MAX_COUNT = 2;

    private static final int REFRESH_TOKEN_TIME_INTERVAL = 30 * 1000;

    private static String AUTH_CODE;

    private static long LAST_VALID_REFRESH_TOKEN_TIME = System.currentTimeMillis();


    static {
        // 初始化http请求配置
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // 连接超时时间
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        // 连接写入超时时间
        httpClient.writeTimeout(20, TimeUnit.SECONDS);
        // 链接读取超时时间
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        // http缓存大小
        httpClient.cache(new Cache(Environment.getDataDirectory(), 8 * 10 * 1000 * 1000));
        // 设置http的header拦截器
        if (BuildConfig.DEBUG) {

            // facebook的stetho拦截器
//            httpClient.addNetworkInterceptor(new StethoInterceptor());
            // 记录http请求日志拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            httpClient.addInterceptor(httpLoggingInterceptor);
        }

        okHttpClient = httpClient.build();

    }

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    private static Retrofit retrofit = builder.client(okHttpClient).build();

    public static <S> S createService(Class<S> serviceClass) {

        return retrofit.create(serviceClass);
    }

}
