package com.appstud.testappstud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.appstud.testappstud.adapter.IListener;
import com.appstud.testappstud.adapter.MyAdapter;
import com.appstud.testappstud.database.DaoSession;
import com.appstud.testappstud.database.MyContact;
import com.appstud.testappstud.database.MyContactDao;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        initView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_favorites_list);
        // get Dao
        MyApplication app = (MyApplication) getApplication();
        DaoSession session = app.getDaoSession();
        final MyContactDao dao = session.getMyContactDao();
        // create object myContact
        List<MyContact> contacts = dao.loadAll();


        // Database
        mAdapter = new MyAdapter(FavoritesActivity.this, contacts, false, new IListener() {
            @Override
            public void onClick(MyContact contact, int position) {
                // delete
                dao.delete(contact);
                mAdapter.deleteItem(position);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
