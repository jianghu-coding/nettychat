package com.chat.androidclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chat.androidclient.R;
import com.chat.androidclient.mvvm.model.Friend;
import com.chat.androidclient.mvvm.model.Group;
import com.chat.androidclient.mvvm.view.activity.ChatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lps on 2018/12/29 16:05.
 */
public class FriendAdapter extends BaseExpandableListAdapter {
    List<Group> friendList = new ArrayList<>();
    private Context mContext;

    public FriendAdapter(Context contx) {
        mContext = contx;
    }

    @Override
    public int getGroupCount() {
        return friendList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return friendList.get(groupPosition).getMFriendList().size();
    }

    @Override
    public Group getGroup(int groupPosition) {
        return friendList.get(groupPosition);
    }

    @Override
    public Friend getChild(int groupPosition, int childPosition) {
        return friendList.get(groupPosition).getMFriendList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupVH vh = null;
        if (convertView == null) {
            vh = new GroupVH();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_group, null);
            vh.name = convertView.findViewById(R.id.groupname);
            vh.online = convertView.findViewById(R.id.grouponline);
            vh.openstate = convertView.findViewById(R.id.iv_open_state);
            vh.rootview=convertView.findViewById(R.id.root_view);
            convertView.setTag(vh);
        } else {
            vh = (GroupVH) convertView.getTag();
        }
        TypedValue bgcolor = new TypedValue();
        TypedValue tvcolor = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.ui_background, bgcolor, true);
        mContext.getTheme().resolveAttribute(R.attr.tv_color, tvcolor, true);
        vh.name.setTextColor(mContext.getResources().getColor(tvcolor.resourceId));
        vh.rootview.setBackgroundResource(bgcolor.resourceId);
        vh.name.setText(getGroup(groupPosition).getName());
        vh.online.setText(getChildrenCount(groupPosition)+"/"+getChildrenCount(groupPosition));
        vh.openstate.setImageResource(isExpanded?R.drawable.skin_aio_arrowdown_nor:R.drawable.right_arrow1_disable);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildVH vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, null);
            vh = new ChildVH();
            vh.name=convertView.findViewById(R.id.name);
            vh.rootview=convertView.findViewById(R.id.root_view);
            vh.div_friend=convertView.findViewById(R.id.div_friend);
            convertView.setTag(vh);
        } else {
            vh = (ChildVH) convertView.getTag();
        }
        TypedValue bgcolor = new TypedValue();
        TypedValue divcolor = new TypedValue();
        TypedValue tvcolor = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.ui_background, bgcolor, true);
        mContext.getTheme().resolveAttribute(R.attr.tv_color, tvcolor, true);
        mContext.getTheme().resolveAttribute(R.attr.div_color, divcolor, true);
        vh.name.setTextColor(mContext.getResources().getColor(tvcolor.resourceId));
        vh.div_friend.setBackgroundResource(divcolor.resourceId);
        vh.rootview.setBackgroundResource(bgcolor.resourceId);
        vh.name.setText(getChild(groupPosition,childPosition).getNickname());
        convertView.setOnClickListener(v ->
           ChatActivity.startActivity(parent.getContext(),getChild(groupPosition,childPosition).getUserId())
        );
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public void setData(@NotNull List<Group> datas) {
        friendList.clear();
        friendList.addAll(datas);
        notifyDataSetChanged();
    }

    public void refreshUI() {
        notifyDataSetChanged();
    }

    class GroupVH {
        TextView name;
        TextView online;
        ImageView openstate;
        FrameLayout rootview;
    }

    class ChildVH {
 TextView name;
 RelativeLayout rootview;
 View div_friend;
    }
}
