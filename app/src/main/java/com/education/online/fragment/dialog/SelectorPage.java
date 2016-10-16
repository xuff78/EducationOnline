package com.education.online.fragment.dialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.education.online.R;
import com.education.online.adapter.CourseAdapter;
import com.education.online.adapter.SelectorRightAdapter;
import com.education.online.bean.CategoryBean;
import com.education.online.fragment.BaseFragment;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/15.
 */
public class SelectorPage extends BaseFragment {

    private ListView menuLeft;
    private int pressPos=0;
    private MenuLeftAdapter menuAdapter;
    private ArrayList<CategoryBean> cates=new ArrayList<>();
    private RecyclerView recyclerList;
    private CourseSelector callback;
    private HttpHandler handler;
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(callback!=null){
                callback.onSelected();
            }
        }
    };

    private void initHandler() {
        handler = new HttpHandler(getActivity(), new CallBack(getActivity()) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selector_page,container,false);

        initView(view);
        initHandler();
        handler.getSubjectList();
        return view;
    }

    public void setData(CourseSelector cb){
        callback=cb;
    }

    private void initView(View v) {
        recyclerList=(RecyclerView)v.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);

        menuLeft= (ListView) v.findViewById(R.id.menuLeft);
        cates.clear();
        CategoryBean cate=new CategoryBean();
        cates.add(cate);
        cates.add(cate);
        cates.add(cate);
        cates.add(cate);
        cates.add(cate);
        cates.add(cate);
        recyclerList.setAdapter(new SelectorRightAdapter(getActivity(), cates, listener));
        menuAdapter=new MenuLeftAdapter();
        menuLeft.setAdapter(menuAdapter);
        menuLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,
                                    long arg3) {
                pressPos=position;
                menuAdapter.notifyDataSetChanged();
            }
        });
    }

    public class MenuLeftAdapter extends BaseAdapter {

        LayoutInflater inflater;


        public MenuLeftAdapter() {
            inflater=LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return cates.size()+1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Holder holder;
            if(convertView==null){
                holder=new Holder();
                convertView =inflater.inflate(R.layout.menu_left_item, null);
                holder.title = (TextView) convertView.findViewById(R.id.menuTitle);
                convertView.setTag(holder);
            }else
                holder=(Holder) convertView.getTag();

            if(pressPos==position){
                holder.title.setTextColor(getResources().getColor(R.color.normal_blue));
                convertView.setBackgroundResource(android.R.color.white);
            }else{
                holder.title.setTextColor(getResources().getColor(R.color.normal_gray));
                convertView.setBackgroundResource(R.color.whitesmoke);
            }
            if(position==0){
                holder.title.setTextColor(Color.RED);
                holder.title.setText("热门推荐");
            }else
                holder.title.setText(cates.get(position-1).getName());

            return convertView;
        }

        class Holder{
            TextView title;
        }
    }

    public interface CourseSelector{
        void onSelected();
    }
}
