package mp3download.musicman.com.simplemp3download;

import java.io.File;
import java.util.Locale;

import android.app.ListFragment;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.splash.SplashConfig;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener, ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private StartAppAd startAppAd = new StartAppAd(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "211183952", true);
        /** Create Splash Ad **/
        StartAppAd.showSplash(this, savedInstanceState,
                new SplashConfig()
                        .setTheme(SplashConfig.Theme.GLOOMY)
                        .setLogo(R.drawable.ic_launcher)
                        .setAppName(getString(R.string.app_name))
        );
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
		
		// Uncomment this if you want to switch back to dropdown list
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        //Prepare Pre-Requisites
        String path= Environment.getExternalStorageDirectory()+"/music/"+getString(R.string.app_name);
        boolean exists = (new File(path)).exists();
        if (!exists){
            new File(path).mkdirs();
        }

		// ToDo: http://stackoverflow.com/questions/11768313/is-it-possible-to-use-dropdown-and-tabs-as-navigation-in-the-action-bar
		// Make a Tabbar dude

        /*
        if(savedInstanceState == null) {
            // Initialize Leadbolt SDK with your api key
            AppTracker.startSession(getApplicationContext(),getString(R.string.leadbolt_key));
        }
        // cache Leadbolt Ad without showing it
        AppTracker.loadModuleToCache(getApplicationContext(), "inapp");
        */

		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Tab tab = actionBar.newTab();
        tab.setText(R.string.fragment_one);
        tab.setTabListener(this);

        Tab tab2 = actionBar.newTab();
        tab2.setText(R.string.fragment_two);
        tab2.setTabListener(this);


        actionBar.addTab(tab);
        actionBar.addTab(tab2);

        

		// Uncomment this if you want to switch back
		/*
        actionBar.setListNavigationCallbacks(
                new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, new String[]{
                        getString(R.string.fragment_one),
                        getString(R.string.fragment_two),
                        getString(R.string.fragment_three),}), this);
		*/
    }

    @Override
    protected void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
        startAppAd.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        startAppAd.onBackPressed();
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        startAppAd.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        Fragment fragment = null;

        switch(i){
            case 0:
                fragment = new SearchFragment();
                break;
            case 1:
                fragment = new DownloadFragment();
                break;
            case 2:
                fragment = new AppWallFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();

        return true;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        Fragment fragment = null;
        switch (tab.getPosition()){
            case 0:
                fragment = new SearchFragment();
                break;

            case 1:
                fragment = new DownloadFragment();
                break;

            case 2:
                fragment = new AppWallFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();

    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search, container, false);
            return rootView;
        }
    }

}
