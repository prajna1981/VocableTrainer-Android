package vocabletrainer.heinecke.aron.vocabletrainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vocabletrainer.heinecke.aron.vocabletrainer.R;
import vocabletrainer.heinecke.aron.vocabletrainer.fragment.ListPickerFragment;
import vocabletrainer.heinecke.aron.vocabletrainer.fragment.TrainerSettingsFragment;
import vocabletrainer.heinecke.aron.vocabletrainer.lib.Storage.TrainerSettings;
import vocabletrainer.heinecke.aron.vocabletrainer.lib.Storage.VList;

import static vocabletrainer.heinecke.aron.vocabletrainer.activity.TrainerActivity.PARAM_TABLES;
import static vocabletrainer.heinecke.aron.vocabletrainer.activity.TrainerActivity.PARAM_TRAINER_SETTINGS;

/**
 * Trainer settings activity
 */
public class TrainerSettingsActivity extends FragmentActivity implements TrainerSettingsFragment.FinishHandler {

    private static final String TAG = "TrainerSettings";

    ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ListPickerFragment listPicker;
    private TrainerSettingsFragment settingsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_settings);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        initViewPager();

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Init ViewPager
     */
    private void initViewPager(){
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        listPicker = ListPickerFragment.newInstance(true,false,null,null,false);
        viewPagerAdapter.addFragment(listPicker,R.string.TSettings_Tab_List);
        settingsFragment = TrainerSettingsFragment.newInstance(this);
        viewPagerAdapter.addFragment(settingsFragment,R.string.TSettings_Tab_Settings);

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void handleFinish(TrainerSettings settings) {
        ArrayList<VList> picked = listPicker.getSelectedItems();
        if(picked.size() > 0) {
            Intent intent = new Intent(this, TrainerActivity.class);
            intent.putExtra(PARAM_TRAINER_SETTINGS, settings);
            intent.putExtra(PARAM_TABLES, picked);
            startActivity(intent);
        } else {
            //TODO: inform about not enough picked items
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /**
         * Add Fragment to viewpager
         * @param fragment Fragment
         * @param title Tab-Title string resource
         */
        public void addFragment(Fragment fragment,@StringRes int title){
            addFragment(fragment,getString(title));
        }

        /**
         * Add Fragment to viewpager
         * @param fragment Fragment
         * @param title Title
         */
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
