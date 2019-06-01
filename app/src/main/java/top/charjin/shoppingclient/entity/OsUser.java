package top.charjin.shoppingclient.entity;

import java.io.Serializable;

public class OsUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * -- 头像在服务器的路径
     */
    private String headPortrait;

    /**
     * 用户名
     */
    private String username;
    private Integer userId;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别,为了便于读取存为男,女
     */
    private String sex;

    /**
     * 联系方式
     */
    private String phone;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 默认收货地址id
     */
    private Integer addressId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", headPortrait=").append(headPortrait);
        sb.append(", username=").append(username);
        sb.append(", nickname=").append(nickname);
        sb.append(", password=").append(password);
        sb.append(", realName=").append(realName);
        sb.append(", sex=").append(sex);
        sb.append(", phone=").append(phone);
        sb.append(", addressId=").append(addressId);
        sb.append("]");
        return sb.toString();
    }
}