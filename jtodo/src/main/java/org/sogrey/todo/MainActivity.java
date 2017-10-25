package org.sogrey.todo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.litesuits.orm.db.annotation.Table;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import org.sogrey.sogreyframe.adapter.base.CommonRecycleViewAdapter;
import org.sogrey.sogreyframe.adapter.base.CommonRecycleViewHolder;
import org.sogrey.sogreyframe.db.DBHelper;
import org.sogrey.sogreyframe.ui.base.BaseActivity;
import org.sogrey.todo.base.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

public class MainActivity extends BaseAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.spaceNavigationView)
    protected SpaceNavigationView mSpaceNavigationView;
    @BindViews({R.id.rv_quadrant_1, R.id.rv_quadrant_2, R.id.rv_quadrant_3, R.id.rv_quadrant_4})
    protected List<RecyclerView> mRecyclerViews;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initSpaceNavigationView(savedInstanceState);
        initDatas();
    }

    @SuppressWarnings("unchecked")
    private void initDatas() {
        List<String> news = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            news.add("item news " + i);
        }

        CommonRecycleViewAdapter mAdapter =  new CommonRecycleViewAdapter(mContext, news, R.layout.item_quadtant_rv) {
            @Override
            protected void convert(CommonRecycleViewHolder holder, Object item, int position) {
                holder.setText(R.id.txt_item_title, String.valueOf(item));
            }
        };
        mAdapter.setOnItemClickListener(new CommonRecycleViewAdapter.OnItemClickListener() {

            @Override
            public <T> void onItemClick(View view, T item, int position) {
                mToast.showToast((String)item);
            }
        });
        LinearLayoutManager layoutManager0 = new LinearLayoutManager(this);
        mRecyclerViews.get(0).setLayoutManager(layoutManager0);
        mRecyclerViews.get(0).setAdapter(mAdapter);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        mRecyclerViews.get(1).setLayoutManager(layoutManager1);
        mRecyclerViews.get(1).setAdapter(mAdapter);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        mRecyclerViews.get(2).setLayoutManager(layoutManager2);
        mRecyclerViews.get(2).setAdapter(mAdapter);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        mRecyclerViews.get(3).setLayoutManager(layoutManager3);
        mRecyclerViews.get(3).setAdapter(mAdapter);
    }

    @Override
    public boolean isShowExitDialog() {
        return true;
    }

    private void initSpaceNavigationView(Bundle savedInstanceState) {
        mSpaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        mSpaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.txt_tab_time_management), R.drawable.ic_timer));
        mSpaceNavigationView.addSpaceItem(new SpaceItem(getString(R.string.txt_tab_vid), R.drawable.ic_vid));
        mSpaceNavigationView.shouldShowFullBadgeText(true);
        mSpaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        mSpaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Log.d("onCentreButtonClick ", "onCentreButtonClick");
                mSpaceNavigationView.shouldShowFullBadgeText(true);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Log.d("onItemClick ", "" + itemIndex + " " + itemName);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.d("onItemReselected ", "" + itemIndex + " " + itemName);
            }
        });
        mSpaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            @Override
            public void onCentreButtonLongClick() {
                Toast.makeText(MainActivity.this, "onCentreButtonLongClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, String.valueOf(itemIndex) + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
