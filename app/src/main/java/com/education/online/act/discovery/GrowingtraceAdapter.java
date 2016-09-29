package com.education.online.act.discovery;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.education.online.R;

/**
 * Created by Administrator on 2016/9/28.
 */
public class GrowingtraceAdapter extends RecyclerView.Adapter {
    private Activity act;
    private LayoutInflater listInflater;

    public GrowingtraceAdapter(Activity act , String jason){
        this.act = act;
        this.listInflater = LayoutInflater.from(act);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh =null;
            if ((viewType ==1)||(viewType ==2))
            {
                View view = listInflater.inflate(R.layout.learningtracesplitline,null);
                vh = new SplitlineHolder(view,viewType);
                return vh;
            } else {
                View view = listInflater.inflate(R.layout.learningtrace_classitem,null);
                vh = new AnsweritemHolder(view, viewType);
                return vh;
            }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position ==(getItemCount()-1))
        {
            AnsweritemHolder vh = (AnsweritemHolder) holder;
        } else if (position%2 ==0)
        {
            SplitlineHolder vh = (SplitlineHolder) holder;
        }else{
            AnsweritemHolder vh = (AnsweritemHolder) holder;

        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if(position ==(getItemCount()-1) )
        {
            return 3;
        }else if (position==0){
            return 2;
        }
        else if (position%2==0){
            return 1;
        }else {
            return 0;
        }
    }

    public class SplitlineHolder extends RecyclerView.ViewHolder{
        TextView date;
        ImageView oval;
        View splitline;

        public SplitlineHolder(View itemView,int viewType) {
            super(itemView);
            TextView date = (TextView) itemView.findViewById(R.id.date);
            ImageView oval = (ImageView) itemView.findViewById(R.id.oval);
            View splitline = itemView.findViewById(R.id.splitline);
            if (viewType==2)
            {
                splitline.setVisibility(View.INVISIBLE);
            }


        }
    }

    public class AnsweritemHolder extends RecyclerView.ViewHolder{

        TextView time,serialtime,classname,onlineclass;
        View view1,view2,view3;
        ImageView icon_oval, classicon;

        public AnsweritemHolder(View itemView,int viewType) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.time);
            serialtime = (TextView) itemView.findViewById(R.id.serialtime);
            classname= (TextView) itemView.findViewById(R.id.classname);
            onlineclass= (TextView) itemView.findViewById(R.id.onlineclass);
            view1 =itemView.findViewById(R.id.view1);
            view2  =itemView.findViewById(R.id.view2);
            view3 =itemView.findViewById(R.id.view3);
            icon_oval = (ImageView) itemView.findViewById(R.id.icon_oval);
            classicon = (ImageView) itemView.findViewById(R.id.classicon);
            if(viewType ==3)
            {
                time.setVisibility(View.INVISIBLE);
                serialtime.setVisibility(View.INVISIBLE);
                classname.setVisibility(View.INVISIBLE);
                onlineclass.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                icon_oval.setVisibility(View.INVISIBLE);
                classicon.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.VISIBLE);
            }
        }
    }
}
