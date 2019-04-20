package com.tranxitpro.user.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by santhosh@appoets.com on 07-06-2018.
 */
public class LostItem {

    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("comments_by")
    @Expose
    private String commentsBy;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_admin")
    @Expose
    private Integer isAdmin;
    @SerializedName("lost_item_name")
    @Expose
    private String lostItemName;
    @SerializedName("parent_id")
    @Expose
    private Object parentId;
    @SerializedName("request_id")
    @Expose
    private Integer requestId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommentsBy() {
        return commentsBy;
    }

    public void setCommentsBy(String commentsBy) {
        this.commentsBy = commentsBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getLostItemName() {
        return lostItemName;
    }

    public void setLostItemName(String lostItemName) {
        this.lostItemName = lostItemName;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
