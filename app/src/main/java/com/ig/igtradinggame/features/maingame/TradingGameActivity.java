package com.ig.igtradinggame.features.maingame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ig.igtradinggame.R;
import com.ig.igtradinggame.features.maingame.about.MyAccountFragment;
import com.ig.igtradinggame.features.maingame.trade.buy.BuyFragment;
import com.ig.igtradinggame.features.maingame.trade.sell.SellFragment;
import com.ig.igtradinggame.ui.BaseActivity;

import butterknife.BindView;

public final class TradingGameActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_game);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.game_content_view, new MyAccountFragment());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_me) {
            fragment = new MyAccountFragment();
        } else if (id == R.id.nav_buy) {
            fragment = new BuyFragment();
        } else if (id == R.id.nav_openPosition) {
            fragment = new SellFragment();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.game_content_view, fragment)
                .commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
