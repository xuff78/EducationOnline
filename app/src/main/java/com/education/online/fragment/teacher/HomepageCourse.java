package com.education.online.fragment.teacher;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.education.online.R;
import com.education.online.adapter.TeacherImgAdapter;
import com.education.online.bean.CourseBean;
import com.education.online.bean.CourseFilter;
import com.education.online.bean.SubjectBean;
import com.education.online.bean.TeacherBean;
import com.education.online.bean.VideoImgItem;
import com.education.online.fragment.BaseFragment;
import com.education.online.http.CallBack;
import com.education.online.http.HttpHandler;
import com.education.online.http.Method;
import com.education.online.inter.AdapterCallback;
import com.education.online.util.ActUtil;
import com.education.online.util.Constant;
import com.education.online.util.DialogUtil;
import com.education.online.util.ImageUtil;
import com.education.online.util.JsonUtil;
import com.education.online.util.LogUtil;
import com.education.online.util.SharedPreferencesUtil;
import com.education.online.util.ToastUtils;
import com.education.online.view.draglist.DragSortListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class HomepageCourse extends BaseFragment {

    private DragSortListView dragList;
    private ArrayList<CourseBean> items=new ArrayList<>();
    private ArrayList<CourseBean> tempitems=new ArrayList<>();
    private DragListAdapter adapter;
    private boolean edit=false;
    private CourseBean delitem;
    private ImageLoader imageLoader;
    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        CourseBean item = adapter.getItem(from);
                        adapter.remove(from);
                        adapter.insert(item, to);
                    }
                }
            };

    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(final int which) {

                    CourseBean delitem = adapter.getItem(which);
                    adapter.remove(which);
                    handler.deleteCourse(delitem.getCourse_id());
//                    DialogUtil.showConfirmDialog(getActivity(), "提示", "确认删除该项?", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
                }
            };
    private HttpHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.draglist_layout, container, false);

        imageLoader=ImageLoader.getInstance();
        initView(view);
        edit=false;
        if(items.size()==0) {
            initHandler();
            CourseFilter filter=new CourseFilter();
            filter.setCourse_type(null);
            filter.setUsercode(SharedPreferencesUtil.getUsercode(getActivity()));
            filter.setSort("sort_order");
            handler.getCourseList(filter);
        }else{
            items.clear();
            items.addAll(tempitems);
            adapter=new DragListAdapter(items);
            dragList.setAdapter(adapter);
        }
        return view;
    }

    public void setEdit(){
        if(edit){
            String ids="";
            for (int i=0;i<adapter.getCount();i++){
                CourseBean bean=adapter.getItem(i);
                ids=ids+bean.getCourse_id()+"_"+i+",";
            }
            if(ids.length()>0)
                handler.updateSortList(ids.substring(0, ids.length()-1));
//            else
//                handler.updateSortList(ids);
            LogUtil.i("test", "finishEdit!!");
        }else {
            edit=true;
            adapter.notifyDataSetChanged();
        }
    }

    private void initView(View v) {
        dragList = (DragSortListView) v.findViewById(R.id.dragList);
        dragList.setDropListener(onDrop);
        dragList.setRemoveListener(onRemove);
        dragList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!edit){
                    setEdit();
                }
                return false;
            }
        });
    }

    private void initHandler() {
        handler = new HttpHandler(getActivity(), new CallBack(getActivity()) {
            @Override
            public void doSuccess(String method, String jsonData) throws JSONException {
                super.doSuccess(method, jsonData);
                if(method.equals(Method.getCourseList)){
                    items= JSON.parseObject(JsonUtil.getString(jsonData, "course_info"),
                            new TypeReference<ArrayList<CourseBean>>(){});
                    adapter=new DragListAdapter(items);
                    dragList.setAdapter(adapter);
                    tempitems.clear();
                    tempitems.addAll(items);
                }else if(method.equals(Method.updateSortList)){
                    edit=false;
                    tempitems.clear();
                    tempitems.addAll(items);
                    adapter.notifyDataSetChanged();
                    DialogUtil.showInfoDailog(getActivity(), "提示", "修改成功");
                }else if(method.equals(Method.updateSortList)){
                    ToastUtils.displayTextShort(getActivity(), "删除成功");
                }
            }
        });
    }

    public class DragListAdapter extends BaseAdapter {

        LayoutInflater inflater;
        ArrayList<CourseBean> courses;

        public DragListAdapter(ArrayList<CourseBean> courses) {
            this.courses=courses;
            inflater=LayoutInflater.from(getActivity());
        }

        public void changeStatus(boolean status){
            if(edit!=status){
                setEdit();
            }
        }

        public void remove(int arg0) {//删除指定位置的item
            courses.remove(arg0);
            this.notifyDataSetChanged();//不要忘记更改适配器对象的数据源
        }

        public void insert(CourseBean item, int arg0) {
            courses.add(arg0, item);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return courses.size();
        }

        @Override
        public CourseBean getItem(int i) {
            return courses.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v =inflater.inflate(R.layout.course_homepage_item, null);

            CourseBean course=getItem(position);

            TextView courseType = (TextView) v.findViewById(R.id.courseType);
            ActUtil.getCourseTypeTxt(course.getCourse_type(), courseType);
            TextView CourseName = (TextView) v.findViewById(R.id.CourseName);
            ImageView delBtn=(ImageView)v.findViewById(R.id.delBtn);
            ImageView CourseImage=(ImageView)v.findViewById(R.id.CourseImage);
            imageLoader.displayImage(ImageUtil.getImageUrl(course.getImg()),CourseImage);
            TextView CourseTime = (TextView) v.findViewById(R.id.CourseTime);
            CourseTime.setText(course.getUser_name());
            TextView CoursePrice = (TextView) v.findViewById(R.id.CoursePrice);
            CoursePrice.setText(ActUtil.getPrice(course.getPrice()));
            TextView NumApplicant = (TextView) v.findViewById(R.id.NumApplicant);
            NumApplicant.setText(course.getFollow()+"人已报名");
            CourseName.setText(CourseName.getText().toString()+course.getSubject_name());
            if(edit){
                delBtn.setVisibility(View.VISIBLE);
            }else{
                delBtn.setVisibility(View.GONE);
            }
            return v;
        }

    }
}
