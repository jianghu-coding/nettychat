//package com.chat.androidclient.adapter
//
//import android.database.DataSetObserver
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ExpandableListAdapter
//import com.chat.androidclient.R
//import com.chat.androidclient.mvvm.model.Group
//
///**
// * Created by lps on 2018/12/29 15:31.
// * to do 有空 改成databinding
// */
//class FriendAdapter : ExpandableListAdapter {
//    var friendList = mutableListOf<Group>()
//    override fun getChildrenCount(groupPosition: Int) = friendList[groupPosition].mFriendList.size
//
//    override fun getGroup(groupPosition: Int) = friendList[groupPosition]
//
//    override fun onGroupCollapsed(groupPosition: Int) {
//    }
//
//    override fun isEmpty(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun registerDataSetObserver(observer: DataSetObserver?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getChild(groupPosition: Int, childPosition: Int) = friendList[groupPosition].mFriendList[childPosition]
//
//    override fun onGroupExpanded(groupPosition: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getCombinedChildId(groupId: Long, childId: Long): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()
//
//    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun hasStableIds()=true
//
//    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun areAllItemsEnabled(): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
//        return childPosition.toLong()
//    }
//
//    override fun getCombinedGroupId(groupId: Long): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
//        var vh: GroupVH
//        if (convertView==null) {
//            convertView = LayoutInflater.from(parent.context).inflate(R.layout.item_friend_group,parent)
//            vh= GroupVH()
//            convertView.tag=vh
//        }else{
//            vh=convertView.tag as GroupVH
//
//        }
//        return convertView!!
//    }
//
//    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getGroupCount() = friendList.size
//
//
//}
//class GroupVH{
//
//}
//class ChildVH{
//
//}