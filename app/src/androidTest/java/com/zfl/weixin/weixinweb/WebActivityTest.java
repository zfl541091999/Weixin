package com.zfl.weixin.weixinweb;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;
import com.zfl.weixin.R;

/**
 * Created by Administrator on 2016/11/15.
 */
public class WebActivityTest extends ActivityInstrumentationTestCase2<WebActivity>{

    //测试的操作类
    private Solo mSolo;

    public WebActivityTest() {
        super(WebActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSolo = new Solo(getInstrumentation(), getActivity());
    }

    public void testFavorButtonClickTest() {
        mSolo.clickOnView(mSolo.getView(R.id.weixin_favor));
        mSolo.sleep(5000);
    }

    public void otestScrollToBottom() {
        if (mSolo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return mSolo.waitForLogMessage("WebView is Done!", 10000);
            }
        }, 10000)) {
            mSolo.scrollToBottom();
        }
        mSolo.sleep(5000);
    }


}
