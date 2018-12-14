package com.company.project.auth.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_sys_menu")
public class SysMenu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单编号
     */
    private String code;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;

    /**
     * 父级编号，一级菜单为null
     */
    @Column(name = "parent_code")
    private String parentCode;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态  -1：已删除  0隐藏，1正常
     */
    private Byte status;

    /**
     * 授权
     */
    private String secured;

    /**
     * 备注
     */
    private String remark;

    /**
     * 排序
     */
    @Column(name = "order_num")
    private Integer orderNum;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取菜单编号
     *
     * @return code - 菜单编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置菜单编号
     *
     * @param code 菜单编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取菜单名称
     *
     * @return name - 菜单名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单名称
     *
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取类型   0：目录   1：菜单   2：按钮
     *
     * @return type - 类型   0：目录   1：菜单   2：按钮
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型   0：目录   1：菜单   2：按钮
     *
     * @param type 类型   0：目录   1：菜单   2：按钮
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取父级编号，一级菜单为null
     *
     * @return parent_code - 父级编号，一级菜单为null
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * 设置父级编号，一级菜单为null
     *
     * @param parentCode 父级编号，一级菜单为null
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * 获取菜单URL
     *
     * @return url - 菜单URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单URL
     *
     * @param url 菜单URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取状态  -1：已删除  0隐藏，1正常
     *
     * @return status - 状态  -1：已删除  0隐藏，1正常
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态  -1：已删除  0隐藏，1正常
     *
     * @param status 状态  -1：已删除  0隐藏，1正常
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取授权
     *
     * @return secured - 授权
     */
    public String getSecured() {
        return secured;
    }

    /**
     * 设置授权
     *
     * @param secured 授权
     */
    public void setSecured(String secured) {
        this.secured = secured;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取排序
     *
     * @return order_num - 排序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置排序
     *
     * @param orderNum 排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", parentCode=").append(parentCode);
        sb.append(", url=").append(url);
        sb.append(", icon=").append(icon);
        sb.append(", status=").append(status);
        sb.append(", secured=").append(secured);
        sb.append(", remark=").append(remark);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}