package com.example.clientapp.Activities;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clientapp.R;
import com.example.clientapp.VO.NotificationVO;

import java.util.ArrayList;

public class NotificationListViewAdapter extends BaseExpandableListAdapter {
    private ArrayList<NotificationVO> mList = null;
    private String mContents = null;
    private Context mContext;
    private LayoutInflater inflater;

    public NotificationListViewAdapter(Context context, ArrayList<NotificationVO> parent) {

        this.mContext = context;
        this.mList = parent;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return mList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mList.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.listview_parent, null);
        }
        //get position
        NotificationVO vo = (NotificationVO) getGroup(i);

        TextView titleTv = (TextView) view.findViewById(R.id.titleTv);
        TextView dateTv = (TextView) view.findViewById(R.id.dateTv);
        titleTv.setText(vo.getTitle());
        dateTv.setText(vo.getNotification_time());

        ImageView imageView = (ImageView) view.findViewById(R.id.updown);
        if (b)
            imageView.setImageResource(R.drawable.up);
        else
            imageView.setImageResource(R.drawable.down);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        //inflate the layout
        if (view == null)
            view = inflater.inflate(R.layout.listview_child, null);

        mContents = ((NotificationVO) getChild(i, 0)).getContents();
        TextView contentsTv = (TextView) view.findViewById(R.id.contentsTv);

//        view.setBackgroundColor(Color.LTGRAY);
        contentsTv.setText(mContents);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


}