package com.appstud.testappstud;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.appstud.testappstud.adapter.IListener;
import com.appstud.testappstud.adapter.MyAdapter;
import com.appstud.testappstud.database.DaoSession;
import com.appstud.testappstud.database.MyContact;
import com.appstud.testappstud.database.MyContactDao;
import com.appstud.testappstud.network.Contacts;
import com.appstud.testappstud.network.INetwork;
import com.appstud.testappstud.network.NetworkCore;

import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refresh();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refresh() {
        final ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loading), true);
        NetworkCore core = NetworkCore.getInstance(this);
        INetwork network = core.getRestAdapter().create(INetwork.class);
        // call service contacts
        dialog.show();
        network.contacts(new Callback<Contacts>() {

            @Override
            public void success(Contacts contacts, Response response) {
                dialog.dismiss();
                if (contacts != null) {
                    //
                    mAdapter = new MyAdapter(ContactActivity.this, contacts.contacts, new IListener()  {

                        @Override
                        public void onClick(MyContact contact, int position) {
                            // get Dao
                            MyApplication app = (MyApplication) getApplication();
                            DaoSession session = app.getDaoSession();
                            MyContactDao dao = session.getMyContactDao();
                            // update or insert contact
                            List<MyContact> list = dao.loadAll();
                            if (list != null && list.size() >= 1) {
                                for (int i = 0 ; i < list.size(); ++i) {
                                    if (list.get(i).getName().equalsIgnoreCase(contact.getName())) {
                                        contact.setId(list.get(i).getId());
                                        break;
                                    }
                                }
                            }
                            dao.insertOrReplace(contact);
                            Toast.makeText(ContactActivity.this, R.string.add_contact, Toast.LENGTH_LONG).show();
                        }

                    });
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ContactActivity.this));
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(ContactActivity.this, R.string.error_empty, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Toast.makeText(ContactActivity.this, R.string.error_ws, Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * Init View
     */
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_contact_list);

        refresh();

    }
}
