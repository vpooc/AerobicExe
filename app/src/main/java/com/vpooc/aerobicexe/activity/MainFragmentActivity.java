package com.vpooc.aerobicexe.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vpooc.aerobicexe.R;
import com.vpooc.aerobicexe.fragment.DiscoverFragment;
import com.vpooc.aerobicexe.fragment.MeFragment;
import com.vpooc.aerobicexe.fragment.SportFragment;

/**
 * Created by Administrator on 2016/5/31.
 */
public class MainFragmentActivity extends FragmentActivity {
    SportFragment sportFragment;
    //0 sport  1 discover 2 me
    int currentFragmentIndex=0;
    //0:sport 1:discover 2 me
    int clickButtonIndex=0;
    Button[] btnArray=new Button[3];
    Fragment[] fragmentArray=new Fragment[3];

    @Override
    protected void onCreate(Bundle arg0) {
        Log.d("MainFragmentActivity","onCreate");
        super.onCreate(arg0);
        try {
            setContentView(R.layout.fragment_main);
            setViews();
            addListener();
            //显示第一个fragment
            sportFragment=new SportFragment();
            FragmentManager manager=
                    getSupportFragmentManager();

            FragmentTransaction transaction=
                    manager.beginTransaction();
            //fragment_containe是一个linearlayout
            transaction.add(R.id.fragment_container
                    , sportFragment);
            transaction.commit();//提交

            fragmentArray[0]=sportFragment;
            fragmentArray[1]=new DiscoverFragment();
            fragmentArray[2]=new MeFragment();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        Log.d("MainFragmentActivity","onStart");
        super.onStart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("MainFragmentActivity  ", newConfig.toString());
        super.onConfigurationChanged(newConfig);
    }

    private void addListener() {

        MyClickListener myClickListener=new MyClickListener();
        for (Button btn:btnArray)
        {
            btn.setOnClickListener(myClickListener);
        }
    }

    private void setViews() {
        // TODO Auto-generated method stub
        btnArray[0]=(Button)
                findViewById
                        (R.id.btn_main_fragment_sport);
        btnArray[1]=(Button)
                findViewById
                        (R.id.btn_main_fragment_discover);
        btnArray[2]=(Button)
                findViewById
                        (R.id.btn_main_fragment_me);
    }
    class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            //判断单击按钮
            switch (v.getId()) {
                case R.id.btn_main_fragment_sport:
                    clickButtonIndex=0;
                    break;
                case R.id.btn_main_fragment_discover:
                    clickButtonIndex=1;
                    break;
                case R.id.btn_main_fragment_me:
                    clickButtonIndex=2;
                    break;
            }
            //判断单击的按钮是不是当前的按钮
            if (clickButtonIndex!=currentFragmentIndex)
            {
                FragmentManager manager=
                        getSupportFragmentManager();
                FragmentTransaction transaction=
                        manager.beginTransaction();
                //以前的fragment hide
                transaction.hide
                        (fragmentArray[currentFragmentIndex]);
                //显示对应的fragment
                Fragment showFragment=
                        fragmentArray[clickButtonIndex];
                if (!showFragment.isAdded())
                {
                    transaction.add(R.id.fragment_container,
                            showFragment);
                }
                transaction.show(showFragment);
                //transaction 事务
                transaction.commit();

            }
            currentFragmentIndex=clickButtonIndex;
        }



    }
}
