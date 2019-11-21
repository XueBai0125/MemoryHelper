package com.group.memoryhelper.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.group.memoryhelper.R;
import com.group.memoryhelper.fragment.CategoryFragment;
import com.group.memoryhelper.fragment.FamousFragment;
import com.group.memoryhelper.fragment.HomeFragment;
import com.group.memoryhelper.fragment.MoreFragment;

import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout_first;
    LinearLayout layout_second;
    LinearLayout layout_four;
    LinearLayout layout_five;
    private LinearLayout[] mLayouts;
    private static final String KEY_FRAGMENT_TAG = "fragment_tag";
    private static final String FRAGMENT_FIRST = "fragment_first";
    private static final String FRAGMENT_SECOND = "fragment_second";
    private static final String FRAGMENT_FOUR = "fragment_four";
    private static final String FRAGMENT_FIVE = "fragment_five";
    private String[] mFragmentTags = new String[]{ FRAGMENT_FIRST,FRAGMENT_SECOND,FRAGMENT_FOUR,FRAGMENT_FIVE};
    private String mFragmentCurrentTag = FRAGMENT_FIRST;
    private HomeFragment mProductFragment;
    private MoreFragment mMoreFragment;
    private CategoryFragment mCategoryFragment;
    private FamousFragment mFamousFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            restoreFragments();
            mFragmentCurrentTag = savedInstanceState.getString(KEY_FRAGMENT_TAG);
        }
        setContentView(R.layout.activity_home);
        String data = getIntent().getStringExtra("data");
        if (!TextUtils.isEmpty(data)){
            mFragmentCurrentTag = FRAGMENT_FIVE;
        }
        initView();
        initData();
        setListener();

    }

    private void initView() {
        layout_first = findViewById(R.id.layout_first);
        layout_second = findViewById(R.id.layout_second);
        layout_four = findViewById(R.id.layout_four);
        layout_five = findViewById(R.id.layout_five);

    }

    private void initData() {
        mLayouts = new LinearLayout[]{ layout_first, layout_second,layout_four,layout_five};
    }
    private void setListener() {
        for (int i = 0; i < mLayouts.length; i++) {
            mLayouts[i].setOnClickListener(this);
        }

       if (TextUtils.equals(FRAGMENT_FIRST, mFragmentCurrentTag)) {
           layout_first.performClick();
        }else if (TextUtils.equals(FRAGMENT_SECOND, mFragmentCurrentTag)) {
           layout_second.performClick();
        }else if (TextUtils.equals(FRAGMENT_FOUR, mFragmentCurrentTag)) {
           layout_four.performClick();
       }else if (TextUtils.equals(FRAGMENT_FIVE, mFragmentCurrentTag)) {
           layout_five.performClick();
       }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_FRAGMENT_TAG, mFragmentCurrentTag);
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onClick(View v) {
        onTabSelect((LinearLayout) v);
    }
    /**
     * 切换tab页
     * @param itemLayout
     */
    public void onTabSelect(LinearLayout itemLayout) {
        int id = itemLayout.getId();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(manager, transaction);

        for (int i = 0; i < mLayouts.length; i++) {
            mLayouts[i].setSelected(false);
        }
        itemLayout.setSelected(true);

      if (id == R.id.layout_first) {
            selectedFragment(transaction, mProductFragment, HomeFragment.class, FRAGMENT_FIRST);
      } else if (id == R.id.layout_second) {
          selectedFragment(transaction, mCategoryFragment, CategoryFragment.class, FRAGMENT_SECOND);
      }  else if (id == R.id.layout_four) {
          selectedFragment(transaction, mFamousFragment, FamousFragment.class, FRAGMENT_FOUR);
      } else if (id == R.id.layout_five) {
          selectedFragment(transaction, mMoreFragment, MoreFragment.class, FRAGMENT_FIVE);
      }
        transaction.commit();
    }
    private void selectedFragment(FragmentTransaction transaction, Fragment fragment, Class<?> clazz, String tag) {
        mFragmentCurrentTag = tag;
        if (fragment == null) {
            try {
                Method newInstanceMethod = clazz.getDeclaredMethod("newInstance");
                fragment = (Fragment) newInstanceMethod.invoke(null);
                if (fragment instanceof HomeFragment) {
                    mProductFragment = (HomeFragment)fragment;
                } else if (fragment instanceof CategoryFragment) {
                    mCategoryFragment = (CategoryFragment)fragment;
                }else if (fragment instanceof FamousFragment) {
                    mFamousFragment = (FamousFragment)fragment;
                }else if (fragment instanceof MoreFragment) {
                    mMoreFragment = (MoreFragment)fragment;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            transaction.add(R.id.fragment_container, fragment, tag);
        }
        transaction.show(fragment);
    }
    /**
     * 先全部隐藏
     * @param fragmentManager
     * @param transaction
     */
    private void hideFragments(FragmentManager fragmentManager, FragmentTransaction transaction) {
        for (int i = 0; i < mFragmentTags.length; i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(mFragmentTags[i]);
            if (fragment != null && fragment.isVisible()) {
                transaction.hide(fragment);
            }
        }
    }
    /**
     * 恢复fragment
     */
    private void restoreFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (int i = 0; i < mFragmentTags.length; i++) {
            Fragment fragment = manager.findFragmentByTag(mFragmentTags[i]);
            if (fragment instanceof HomeFragment) {
                mProductFragment = (HomeFragment)fragment;
            } else if (fragment instanceof CategoryFragment) {
                mCategoryFragment = (CategoryFragment)fragment;
            }else if (fragment instanceof FamousFragment) {
                mFamousFragment = (FamousFragment)fragment;
            }else if (fragment instanceof MoreFragment) {
                mMoreFragment = (MoreFragment)fragment;
            }
            transaction.hide(fragment);
        }
        transaction.commit();
    }
}
