package com.example.a20254863.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button mNobutton;
    private List<DataBean> mDatas;
    private MyAdapter mAdapter;
    private PullToRefreshListView mPullRefreshListView;
    private int Sum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        mNobutton = (Button) findViewById(R.id.button4);
        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.listView);
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        // Add an end-of-list listener
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                //   Toast.makeText(PullToRefreshListActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
            }
        });
        mPullRefreshListView.setMode(Mode.BOTH);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            DataBean dataBean = new DataBean("" + i, "步步高", "教育电子" + i + ",good");
            mDatas.add(dataBean);
            Sum++;
        }
        mAdapter = new MyAdapter(this, mDatas);
        mPullRefreshListView.setAdapter(mAdapter);

    }

    private class GetDataTask extends AsyncTask<Void, Void, List<DataBean>> {

        @Override
        protected List<DataBean> doInBackground(Void... params) {
            return mDatas;
        }

        @Override
        protected void onPostExecute(List<DataBean> result) {
            if (mPullRefreshListView.getCurrentMode() == Mode.PULL_FROM_END) {
                for (int i = 0; i < 3; i++) {
                    DataBean dataBean = new DataBean("tanglanting", "唐蓝挺" + (Sum++), "教育电子的同学");
                    mDatas.add(dataBean);
                }
            }
            mAdapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    public void btnNoEditList(View view) {
        mAdapter.flage = !mAdapter.flage;
        button.setVisibility(View.VISIBLE);
        mNobutton.setVisibility(View.INVISIBLE);
        mAdapter.notifyDataSetChanged();
    }
    public void btnEditList(View view) {
        mAdapter.flage = !mAdapter.flage;
        button.setVisibility(View.INVISIBLE);
        mNobutton.setVisibility(View.VISIBLE);
        mAdapter.notifyDataSetChanged();
    }

    public void btnSelectAllList(View view) {
        if (mAdapter.flage) {
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = true;
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public void btnNoList(View view) {
        if (mAdapter.flage) {
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = false;
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public void btnfanxuanList(View view) {
        if (mAdapter.flage) {
            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isCheck) {
                    mDatas.get(i).isCheck = false;
                } else {
                    mDatas.get(i).isCheck = true;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public void btnOperateList(View view) {
        List<String> ids = new ArrayList<>();
        if (mAdapter.flage) {

            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).isCheck) {
                    ids.add(mDatas.get(i).id);
                }
            }
            Log.e("TAG", ids.toString());
        }
    }

}
