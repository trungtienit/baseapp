package com.example.trantien.appreview.base.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.example.trantien.appreview.R;
import com.example.trantien.appreview.base.BaseActivity;
import com.example.trantien.appreview.base.drawer.adapter.MenuAdapter;
import com.example.trantien.appreview.base.drawer.dto.BodyDTO;
import com.example.trantien.appreview.base.drawer.dto.HeaderDTO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

abstract public class DrawerActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @BindView(R.id.rv_menu)
    public RecyclerView rvMenu;

    MenuAdapter mAdapter;

    private List<Object> mList;

    abstract protected int getLayoutId();

//    abstract protected int getNavId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        mList.add(
                new HeaderDTO("MyApp",null,false)
        );
//        for(int i=0;i<10;i++)
//            mList.add(new BodyDTO(R.drawable.ic_launcher_background,"Task "+i,false));
        mAdapter= new MenuAdapter(this,mList);
        rvMenu.setAdapter(mAdapter);
        rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        mAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onHeaderClicked(boolean isSelected) {

            }

            @Override
            public void onBodyClick(int position) {
                showToast(((BodyDTO)mList.get(position)).title);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(Gravity.START);
        }
    }
    protected boolean isDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(Gravity.START);
    }
    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(Gravity.START);
        }
    }
    protected void setInfor(String url,String name) {
        HeaderDTO mHeaderDTO= (HeaderDTO) mList.get(0);
        mHeaderDTO.setUrl(url);
        mHeaderDTO.setName(name);
        mAdapter.notifyItemChanged(0);
    }


}
