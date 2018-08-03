package licola.demo.com.huabandemo.Module.Welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.sdk.InMobiSdk;

import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import licola.demo.com.huabandemo.API.HttpsAPI.TokenAPI;
import licola.demo.com.huabandemo.Base.BaseActivity;
import licola.demo.com.huabandemo.HttpUtils.RetrofitClient;
import licola.demo.com.huabandemo.Module.Login.TokenBean;
import licola.demo.com.huabandemo.R;
import licola.demo.com.huabandemo.Util.Base64;
import licola.demo.com.huabandemo.Util.Constant;
import licola.demo.com.huabandemo.Util.Logger;
import licola.demo.com.huabandemo.Util.SPBuild;
import licola.demo.com.huabandemo.Util.SPUtils;
import licola.demo.com.huabandemo.Util.TimeUtils;
import rx.Observable;

/**
 * Created by LiYi on 2016/04/11  14:26
 * 欢迎页 进行登录判断 和联网重获token
 */

public class WelcomeActivity extends BaseActivity {
    //登录的报文需要
    private static final String PASSWORD = "password";
    private static final int mTimeDifference = TimeUtils.HOUR;
//    private static final int mTimeDifference = 0;

    @BindString(R.string.text_auto_login_fail)
    String mMessageFail;
    @BindView(R.id.img_welcome)
    RelativeLayout mImageView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected String getTAG() {
        return this.toString();
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, WelcomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogin = (Boolean) SPUtils.get(getApplicationContext(), Constant.ISLOGIN, isLogin);
//        AppConnect.getInstance("APP_ID","a560fa01b97211639cd281b1899d2fbf",this);
        InMobiSdk.init(this, "e6f8bb4700d84a609cd4a7aa68a33280");
        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
        InMobiInterstitial interstitialAd = new InMobiInterstitial(this, 1534553070697L, new InMobiInterstitial.InterstitialAdListener2() {
            @Override
            public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
                 Logger.i("onAdLoadFailed");
            }

            @Override
            public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
                Logger.i("onAdReceived");
            }

            @Override
            public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
                Logger.i("onAdLoadSucceeded");
            }

            @Override
            public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                Logger.i("onAdRewardActionCompleted");
            }

            @Override
            public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
                Logger.i("onAdDisplayFailed");
            }

            @Override
            public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
                Logger.i("onAdWillDisplay");
            }

            @Override
            public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
                Logger.i("onAdDisplayed");
            }

            @Override
            public void onAdInteraction(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                Logger.i("onAdInteraction");
            }

            @Override
            public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
                Logger.i("onAdDismissed");
            }

            @Override
            public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {
                Logger.i("onUserLeftApplication");
            }
        });
        interstitialAd.load();
        if(interstitialAd.isReady()){
            interstitialAd.show();
            Logger.i("isReady");
        }else {
            Logger.i("unReady");
        }

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd.isReady()){
                    interstitialAd.show();
                    Logger.i("isReady");
                }else {
                    Logger.i("unReady");
                }
            }
        });

    }
    

    @Override
    protected void onResume() {
        super.onResume();
//        Animator animation = AnimatorInflater.loadAnimator(mContext, R.animator.welcome_animator);
//        //observeOn() 指定的是它之后的操作所在的线程
//        //subscribeOn() 作用于Observable对象
//        //onCompleted() 和 onError() 二者是互斥的 调用一个就不会再调用另一个
//        animation.setTarget(mImageView);
//        MyRxObservable.add(animation)
//                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())//指定订阅的Observable对象的call方法运行在ui线程中
//                .filter(new Func1<Void, Boolean>() {
//                    @Override
//                    public Boolean call(Void aVoid) {
//                        Logger.d("isLogin=" + isLogin);
//                        return isLogin;
////                        return true;
////                        return false;
//                    }
//                })
//                .filter(new Func1<Void, Boolean>() {
//                    @Override
//                    public Boolean call(Void aVoid) {
//                        Long lastTime = (Long) SPUtils.get(getApplicationContext(), Constant.LOGINTIME, 0L);
//                        long dTime = System.currentTimeMillis() - lastTime;
//                        Logger.d("dTime=" + dTime + " default" + mTimeDifference);
//                        return dTime > mTimeDifference;
//                    }
//                })
//                .flatMap(new Func1<Void, Observable<TokenBean>>() {
//                    @Override
//                    public Observable<TokenBean> call(Void aVoid) {
//                        Logger.d("flatMap");
//                        String userAccount = (String) SPUtils.get(getApplicationContext(), Constant.USERACCOUNT, "");
//                        String userPassword = (String) SPUtils.get(getApplicationContext(), Constant.USERPASSWORD, "");
//                        return getUserToken(userAccount, userPassword);
//                    }
//                })
////                .retryWhen(new RetryWithConnectivityIncremental(WelcomeActivity.this, 4, 15, TimeUnit.SECONDS))
//                .observeOn(AndroidSchedulers.mainThread())//最后统一回到UI线程中处理
//                .subscribe(new Subscriber<TokenBean>() {
//                    @Override
//                    public void onCompleted() {
//                        Logger.d();
//                        MainActivity.launch(WelcomeActivity.this);
//                        finish();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d(e.toString());
////                        LoginActivity.launch(WelcomeActivity.this, mMessageFail);
//                        finish();
//                    }
//
//                    @Override
//                    public void onNext(TokenBean tokenBean) {
//                        Logger.d("https success");
//                        saveToken(tokenBean);
//                    }
//                });

    }

    private void saveToken(TokenBean tokenBean) {
        new SPBuild(getApplicationContext())
                .addData(Constant.LOGINTIME, System.currentTimeMillis())
                .addData(Constant.TOKENACCESS, tokenBean.getAccess_token())
                .addData(Constant.TOKENTYPE, tokenBean.getToken_type())
                .build();
    }

    private Observable<TokenBean> getUserToken(String username, String password) {
        return RetrofitClient.createService(TokenAPI.class)
                .httpsGetTokenRx(Base64.mClientInto, PASSWORD, username, password);
    }

}
