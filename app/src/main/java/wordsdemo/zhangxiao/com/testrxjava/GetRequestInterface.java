package wordsdemo.zhangxiao.com.testrxjava;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author ZhangXiao
 * @date 2018/2/7
 */

public interface GetRequestInterface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Observable<Translation> getCall();


    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<Translation1> getCall1();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation2> getCall2();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<TranslationZip1> getCallZip1();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20china")
    Observable<TranslationZip2> getCallZip2();
}
