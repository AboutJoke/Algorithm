package com.sylvan.algorithm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sylvan.algorithm.bean.ListItemBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mList;
    private Thread thread;
    private Handler handler = new Handler(Looper.getMainLooper());
    private ListAdapter adapter;
    private List<ListItemBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(this));
        initData();
    }

    private void initData() {
        adapter = new ListAdapter(list, this);
        mList.setAdapter(adapter);
        thread = new Thread(runnable);
        thread.start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                parseData();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void parseData() throws IOException, JSONException {
        InputStream open = getAssets().open("data.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(open));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject object = new JSONObject(sb.toString());
        final List<ListItemBean> list = ListItemBean.parse(object);
        thread.interrupt();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private List<ListItemBean> list = new ArrayList<>();
        private Context mContext;

        public ListAdapter(List<ListItemBean> list, Context mContext) {
            this.list = list;
            this.mContext = mContext;
        }

        public void setList(List<ListItemBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            final ListItemBean itemBean = list.get(position);
            if (itemBean == null)
                return;
            if (!TextUtils.isEmpty(itemBean.getTitle())) {
                holder.mTitleTxt.setText(itemBean.getTitle());
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewAct.openAct(getApplicationContext(), itemBean.getTitle());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTitleTxt;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTitleTxt = (TextView) itemView.findViewById(R.id.title_txt);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        thread.interrupt();
    }
}
