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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.adapter.CourseAdapter;
import com.education.online.adapter.SelectorRightAdapter;
import com.education.online.bean.CategoryBean;
import com.education.online.bean.SubjectBean;
import com.education.online.fragment.BaseFragment;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class SelectorPage extends BaseFragment {

    private ListView menuLeft;
    private int pressPos=0;
    private MenuLeftAdapter menuAdapter;
    private ArrayList<SubjectBean> cates=new ArrayList<>();
    private RecyclerView recyclerList;
    private CourseSelector callback;
    private HttpHandler handler;
    private String lastId="";
    private boolean showLast=false;
    private SelectorRightAdapter subjectList;
    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(callback!=null){
                SubjectBean subjectBean=(SubjectBean) view.getTag();
                callback.onSelected(subjectBean);
                lastId=subjectBean.getSubject_id();
            }
        }
    };

    private void initHandler() {
        handler = new HttpHandler(getActivity(), new CallBack(getActivity()) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                cates= JSON.parseObject(jsonData, new TypeReference<ArrayList<SubjectBean>>(){});
                setListData();
            }
        });
    }

    private void setListData() {
        pressPos=0;
        for(int i=0;i<cates.size();i++){
            SubjectBean cate=cates.get(i);
            for(int j=0;j<cate.getChild_subject().size();j++){
                SubjectBean subcate=cate.getChild_subject().get(j);
                for(int k=0;k<subcate.getChild_subject_details().size();k++){
                    SubjectBean subject=subcate.getChild_subject_details().get(k);
                    if(subject.getSubject_id().equals(lastId))
                        pressPos=i;
                }
            }
        }
        menuAdapter=new MenuLeftAdapter();
        menuLeft.setAdapter(menuAdapter);
        menuLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position,
                                    long arg3) {
                pressPos=position;
                menuAdapter.notifyDataSetChanged();
                subjectList = new SelectorRightAdapter(getActivity(), cates.get(position).getChild_subject(), listener, showLast, lastId);
                recyclerList.setAdapter(subjectList);
            }
        });
        subjectList = new SelectorRightAdapter(getActivity(), cates.get(pressPos).getChild_subject(), listener, showLast, lastId);
        recyclerList.setAdapter(subjectList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selector_page,container,false);

        initView(view);
        if(cates.size()==0) {
            initHandler();
            handler.getSubjectList();
        }else{
            setListData();
        }
        return view;
    }

    public void setData(CourseSelector cb){
        callback=cb;
    }

    public void setCateInfo(ArrayList<SubjectBean> cates){
        showLast=true;
        ArrayList<SubjectBean> beans=new ArrayList<>();
        SubjectBean subject=new SubjectBean();
        subject.setSubject_name("全部");
        beans.add(subject);

        ArrayList<SubjectBean> cateSub=new ArrayList<>();
        SubjectBean item=new SubjectBean();
        item.setSubject_name("");
        item.setChild_subject_details(beans);
        cateSub.add(item);

        SubjectBean firstItemAll=new SubjectBean();
        firstItemAll.setSubject_name("全部");
        firstItemAll.setChild_subject(cateSub);
        this.cates.clear();
        this.cates.add(firstItemAll);
        this.cates.addAll(cates);
    }

    public void setLastSelection(String lastId){
        this.lastId=lastId;
        showLast=true;
    }

    private void initView(View v) {
        recyclerList=(RecyclerView)v.findViewById(R.id.recyclerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(layoutManager);
        menuLeft= (ListView) v.findViewById(R.id.menuLeft);

    }

    public class MenuLeftAdapter extends BaseAdapter {

        LayoutInflater inflater;


        public MenuLeftAdapter() {
            inflater=LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return cates.size();
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
            if(position==0&&!showLast){
                holder.title.setTextColor(Color.RED);
            }
            holder.title.setText(cates.get(position).getSubject_name());

            return convertView;
        }

        class Holder{
            TextView title;
        }
    }

    public interface CourseSelector{
        void onSelected(SubjectBean subject);
    }
}
