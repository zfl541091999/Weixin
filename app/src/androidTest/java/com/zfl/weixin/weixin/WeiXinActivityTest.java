package com.zfl.weixin.weixin;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Administrator on 2016/11/15.
 */
public class WeiXinActivityTest extends ActivityInstrumentationTestCase2<WeiXinActivity>{
    //测试的操作类
    private Solo mSolo;

    public WeiXinActivityTest( ) {
        super(WeiXinActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSolo = new Solo(getInstrumentation(), getActivity());
    }

    public void testSimpleClick() {
        mSolo.unlockScreen();
        mSolo.scrollToSide(mSolo.LEFT);
        mSolo.scrollDown();
    }


}
