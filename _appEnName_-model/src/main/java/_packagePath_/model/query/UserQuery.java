/*
 * 
 * 
 * 
 * 
 */

package ${packageName}.model.query;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @author admin
 *
 * @2016-12-08 22
 */
public class UserQuery extends BaseQuery {
    
    private static final long serialVersionUID = 1L;
    
    /**  ID * */
    private java.lang.Long id;
        
    /**  创建时间 * */
    private java.util.Date gmtCreate;
        
    /**  修改时间 * */
    private java.util.Date gmtModify;
        
    /**  删除状态（0：已删除；1：未删除） * */
    private java.lang.Boolean delState;
        
    /**  账号 * */
    private java.lang.String accountNo;
        
    /**  密码 * */
    private java.lang.String pwd;
        
    /**  昵称 * */
    private java.lang.String nickname;
        
    /**  类型 * */
    private java.lang.Integer type;
        
    /**  状态 * */
    private java.lang.Integer status;
        
    /**  等级 * */
    private java.lang.Integer level;
        
    /**  手机 * */
    private java.lang.String mobile;
        
    /**  邮件 * */
    private java.lang.String email;
        
    /**  性别 * */
    private java.lang.Integer sex;
        
    /**  生日 * */
    private java.lang.String birthday;
        
    /**  宣言 * */
    private java.lang.String message;
        
    /**  头像 * */
    private java.lang.String avatar;
        
    /**  身高 * */
    private java.lang.Double height;
        
    /**  体重 * */
    private java.lang.Double weight;
        
    /**  描述 * */
    private java.lang.String desc;
        
    /**  支付密码 * */
    private java.lang.String payPwd;
        
    /**  用户地址 * */
    private java.lang.String address;
        
    /**  目标 * */
    private java.lang.Integer target;
        
    /**  第三方openId * */
    private java.lang.String openId;
        
    /**  绑定类型 * */
    private java.lang.Integer bindType;
        
    /**  绑定状态 * */
    private java.lang.Integer bindStatus;
        
    /**  姓名 * */
    private java.lang.String userName;
        
    /**  角色 * */
    private java.lang.String role;
        
    /**  方案id * */
    private java.lang.Long solutionId;
        
    /**  其他信息标签 * */
    private java.lang.String otherInfoTag;
        
    /**  运动频率 * */
    private java.lang.Integer sportRate;
        
    /**  教练id * */
    private java.lang.Long trainerId;
        
    /**  课程状态 * */
    private java.lang.Integer courseStatus;
        
    


    public void setId(java.lang.Long value) {
        this.id = value;
    }
    
    public java.lang.Long getId() {
        return this.id;
    }
    public void setGmtCreate(java.util.Date value) {
        this.gmtCreate = value;
    }
    
    public java.util.Date getGmtCreate() {
        return this.gmtCreate;
    }
    public void setGmtModify(java.util.Date value) {
        this.gmtModify = value;
    }
    
    public java.util.Date getGmtModify() {
        return this.gmtModify;
    }
    public void setDelState(java.lang.Boolean value) {
        this.delState = value;
    }
    
    public java.lang.Boolean getDelState() {
        return this.delState;
    }
    public void setAccountNo(java.lang.String value) {
        this.accountNo = value;
    }
    
    public java.lang.String getAccountNo() {
        return this.accountNo;
    }
    public void setPwd(java.lang.String value) {
        this.pwd = value;
    }
    
    public java.lang.String getPwd() {
        return this.pwd;
    }
    public void setNickname(java.lang.String value) {
        this.nickname = value;
    }
    
    public java.lang.String getNickname() {
        return this.nickname;
    }
    public void setType(java.lang.Integer value) {
        this.type = value;
    }
    
    public java.lang.Integer getType() {
        return this.type;
    }
    public void setStatus(java.lang.Integer value) {
        this.status = value;
    }
    
    public java.lang.Integer getStatus() {
        return this.status;
    }
    public void setLevel(java.lang.Integer value) {
        this.level = value;
    }
    
    public java.lang.Integer getLevel() {
        return this.level;
    }
    public void setMobile(java.lang.String value) {
        this.mobile = value;
    }
    
    public java.lang.String getMobile() {
        return this.mobile;
    }
    public void setEmail(java.lang.String value) {
        this.email = value;
    }
    
    public java.lang.String getEmail() {
        return this.email;
    }
    public void setSex(java.lang.Integer value) {
        this.sex = value;
    }
    
    public java.lang.Integer getSex() {
        return this.sex;
    }
    public void setBirthday(java.lang.String value) {
        this.birthday = value;
    }
    
    public java.lang.String getBirthday() {
        return this.birthday;
    }
    public void setMessage(java.lang.String value) {
        this.message = value;
    }
    
    public java.lang.String getMessage() {
        return this.message;
    }
    public void setAvatar(java.lang.String value) {
        this.avatar = value;
    }
    
    public java.lang.String getAvatar() {
        return this.avatar;
    }
    public void setHeight(java.lang.Double value) {
        this.height = value;
    }
    
    public java.lang.Double getHeight() {
        return this.height;
    }
    public void setWeight(java.lang.Double value) {
        this.weight = value;
    }
    
    public java.lang.Double getWeight() {
        return this.weight;
    }
    public void setDesc(java.lang.String value) {
        this.desc = value;
    }
    
    public java.lang.String getDesc() {
        return this.desc;
    }
    public void setPayPwd(java.lang.String value) {
        this.payPwd = value;
    }
    
    public java.lang.String getPayPwd() {
        return this.payPwd;
    }
    public void setAddress(java.lang.String value) {
        this.address = value;
    }
    
    public java.lang.String getAddress() {
        return this.address;
    }
    public void setTarget(java.lang.Integer value) {
        this.target = value;
    }
    
    public java.lang.Integer getTarget() {
        return this.target;
    }
    public void setOpenId(java.lang.String value) {
        this.openId = value;
    }
    
    public java.lang.String getOpenId() {
        return this.openId;
    }
    public void setBindType(java.lang.Integer value) {
        this.bindType = value;
    }
    
    public java.lang.Integer getBindType() {
        return this.bindType;
    }
    public void setBindStatus(java.lang.Integer value) {
        this.bindStatus = value;
    }
    
    public java.lang.Integer getBindStatus() {
        return this.bindStatus;
    }
    public void setUserName(java.lang.String value) {
        this.userName = value;
    }
    
    public java.lang.String getUserName() {
        return this.userName;
    }
    public void setRole(java.lang.String value) {
        this.role = value;
    }
    
    public java.lang.String getRole() {
        return this.role;
    }
    public void setSolutionId(java.lang.Long value) {
        this.solutionId = value;
    }
    
    public java.lang.Long getSolutionId() {
        return this.solutionId;
    }
    public void setOtherInfoTag(java.lang.String value) {
        this.otherInfoTag = value;
    }
    
    public java.lang.String getOtherInfoTag() {
        return this.otherInfoTag;
    }
    public void setSportRate(java.lang.Integer value) {
        this.sportRate = value;
    }
    
    public java.lang.Integer getSportRate() {
        return this.sportRate;
    }
    public void setTrainerId(java.lang.Long value) {
        this.trainerId = value;
    }
    
    public java.lang.Long getTrainerId() {
        return this.trainerId;
    }
    public void setCourseStatus(java.lang.Integer value) {
        this.courseStatus = value;
    }
    
    public java.lang.Integer getCourseStatus() {
        return this.courseStatus;
    }
    



}