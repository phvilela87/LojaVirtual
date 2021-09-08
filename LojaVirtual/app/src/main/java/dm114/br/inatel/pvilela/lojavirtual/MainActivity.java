package dm114.br.inatel.pvilela.lojavirtual;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import dm114.br.inatel.pvilela.lojavirtual.fragments.GCMFragment;
import dm114.br.inatel.pvilela.lojavirtual.fragments.HomeFragment;
import dm114.br.inatel.pvilela.lojavirtual.fragments.OrderFragment;
import dm114.br.inatel.pvilela.lojavirtual.fragments.OrderInfoNotificationFragment;
import dm114.br.inatel.pvilela.lojavirtual.fragments.ProductFragment;
import dm114.br.inatel.pvilela.lojavirtual.fragments.ProductInterestNotificationFragment;
import dm114.br.inatel.pvilela.lojavirtual.fragments.ProductManagerFragment;
import dm114.br.inatel.pvilela.lojavirtual.fragments.RegisterProductFragment;
import dm114.br.inatel.pvilela.lojavirtual.models.OrderInfo;
import dm114.br.inatel.pvilela.lojavirtual.models.ProductInterest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private ProductInterest productInterest;
    private OrderInfo orderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = this.getIntent();
        if (intent.hasExtra("productInterest")) {
            productInterest = (ProductInterest) intent.
                    getSerializableExtra("productInterest");
            if (productInterest != null) {
                showProductNotificationFragment(productInterest);
            }
        }
        else if(intent.hasExtra("orderInfo")) {
            orderInfo = (OrderInfo) intent.getSerializableExtra("orderInfo");
            if(orderInfo != null) {
                showOrderNotificationFragment(orderInfo);
            }
        }
        else if (savedInstanceState == null) {
            displayFragment(R.id.nav_home);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayFragment(item.getItemId());
        return true;
    }

    private void displayFragment(int fragmentId) {
        Class fragmentClass;
        Fragment fragment = null;
        int backStackEntryCount;
        backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        for (int j = 0; j < backStackEntryCount; j++) {
            getFragmentManager().popBackStack();
        }
        try {
            switch (fragmentId) {
                case R.id.nav_home:
                    fragmentClass = HomeFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_register_new_product:
                    fragmentClass = RegisterProductFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_list_orders:
                    fragmentClass = OrderFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_list_products:
                    fragmentClass = ProductFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_product_manager:
                    fragmentClass = ProductManagerFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_gcm:
                    fragmentClass = GCMFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
                case R.id.nav_logout:
                    SharedPreferences sharedSettings =
                            PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedSettings.edit();
                    editor.clear();
                    editor.commit();
                    finish();
                    break;
                default:
                    fragmentClass = HomeFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,
                    fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.hasExtra("productInterest")) {
            productInterest = (ProductInterest) intent.
                    getSerializableExtra("productInterest");
            if (productInterest != null) {
                showProductNotificationFragment(productInterest);
            }
        }
        else if(intent.hasExtra("orderInfo")) {
            orderInfo = (OrderInfo) intent.getSerializableExtra("orderInfo");
            if(orderInfo != null) {
                showOrderNotificationFragment(orderInfo);
            }
        }
        super.onNewIntent(intent);
    }

    private void showProductNotificationFragment(ProductInterest productInterest) {
        try {
            int backStackEntryCount;
            backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            for (int j = 0; j < backStackEntryCount; j++) {
                getFragmentManager().popBackStack();
            }

            Class fragmentClass = ProductInterestNotificationFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();

            Bundle args = new Bundle();
            args.putSerializable("productInterestNotification", productInterest);
            fragment.setArguments(args);

            if(fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        } catch (Exception e) {
            Toast.makeText(this,
                       "Falha",
                            Toast.LENGTH_SHORT).show();
        }

    }

    private void showOrderNotificationFragment(OrderInfo orderInfo) {
        try {
            int backStackEntryCount;
            backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            for (int j = 0; j < backStackEntryCount; j++) {
                getFragmentManager().popBackStack();
            }

            Class fragmentClass = OrderInfoNotificationFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();

            Bundle args = new Bundle();
            args.putSerializable("orderInfo", orderInfo);
            fragment.setArguments(args);

            if(fragment != null) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                        "Falha",
                        Toast.LENGTH_SHORT).show();
        }

    }

}