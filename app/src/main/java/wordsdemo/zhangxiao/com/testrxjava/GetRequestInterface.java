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
}
