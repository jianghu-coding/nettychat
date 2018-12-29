package com.chat.androidclient.mvvm.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.chat.androidclient.greendao.DaoSession;
import com.chat.androidclient.greendao.FriendDao;
import com.chat.androidclient.greendao.GroupDao;

/**
 * Created by lps on 2018/12/29 15:36.
 * 好友分组
 */
@Entity
public class Group {
    @Id
    private Long id;
    private Long groupId;
    @ToMany(referencedJoinProperty = "customid")
    private List<Friend> mFriendList;
    private String name;
    private int totalcount;
    private int onlinecount;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1591306109)
    private transient GroupDao myDao;
    @Generated(hash = 782253019)
    public Group(Long id, Long groupId, String name, int totalcount,
            int onlinecount) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.totalcount = totalcount;
        this.onlinecount = onlinecount;
    }
    @Generated(hash = 117982048)
    public Group() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getGroupId() {
        return this.groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTotalcount() {
        return this.totalcount;
    }
    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }
    public int getOnlinecount() {
        return this.onlinecount;
    }
    public void setOnlinecount(int onlinecount) {
        this.onlinecount = onlinecount;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1309426559)
    public List<Friend> getMFriendList() {
        if (mFriendList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FriendDao targetDao = daoSession.getFriendDao();
            List<Friend> mFriendListNew = targetDao._queryGroup_MFriendList(id);
            synchronized (this) {
                if (mFriendList == null) {
                    mFriendList = mFriendListNew;
                }
            }
        }
        return mFriendList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 498463670)
    public synchronized void resetMFriendList() {
        mFriendList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1333602095)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGroupDao() : null;
    }
}
