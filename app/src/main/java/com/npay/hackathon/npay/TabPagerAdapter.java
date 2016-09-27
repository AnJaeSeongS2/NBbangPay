package com.npay.hackathon.npay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Vector;

/**
 * Created by Administrator on 2016-09-08.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private int mTabCount; // 탭의 수

    Fragment mFragment1;
    Fragment mFragment2;
    Fragment mFragment3;

    /**
     * 생성자
     * @param fm
     * @param numberOfTabs 현재 tab의 수
     */
    public TabPagerAdapter(FragmentManager fm, int numberOfTabs, CustomTabLayout customTabLayout) {
        super(fm);
        this.mTabCount = numberOfTabs;
        mFragment1 = new Tab1();
        mFragment2 = new Tab2();
        mFragment3 = new Tab3();
    }

    /**
     * Tab 이동시 호출할 fragment
     * @param position 현재 tab의 위치
     * @return 호출할 fragment
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return mFragment1;
            case 1:
                return mFragment2;
            case 2:
                return mFragment3;
            default:
                return null;
        }
    }

    /**
     * tab의 수 반환
     * @return tab의 수
     */
    @Override
    public int getCount() {
        return mTabCount;
    }

}
