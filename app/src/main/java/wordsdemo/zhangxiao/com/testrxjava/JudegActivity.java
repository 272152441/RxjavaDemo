package wordsdemo.zhangxiao.com.testrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

public class JudegActivity extends AppCompatActivity {


    private static final String TAG = JudegActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_judge);

        EditText name = findViewById(R.id.name);
        EditText age = findViewById(R.id.age);
        EditText job = findViewById(R.id.job);
        Button list = findViewById(R.id.list);

        Observable<CharSequence> nameObservable = RxTextView.textChanges(name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(age).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(job).skip(1);

        Observable.combineLatest(nameObservable, ageObservable, jobObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
                boolean isNameValid = !TextUtils.isEmpty(charSequence);
                boolean isAgeValid = !TextUtils.isEmpty(charSequence2);
                boolean isJobValid = !TextUtils.isEmpty(charSequence3);

                return isNameValid&&isAgeValid&&isJobValid;
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.e(TAG, "提交按钮是否可点击： "+aBoolean);
                list.setEnabled(aBoolean);
            }
        });

    }
}
