package cn.hx.bat.sso.pojo;

import javax.persistence.*;

@Table(name = "set_data")
public class SetData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否启动 1启动 0关闭
     */
    @Column(name = "is_start")
    private Boolean isStart;

    /**
     * 备注
     */
    private String remark;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取是否启动 1启动 0关闭
     *
     * @return is_start - 是否启动 1启动 0关闭
     */
    public Boolean getIsStart() {
        return isStart;
    }

    /**
     * 设置是否启动 1启动 0关闭
     *
     * @param isStart 是否启动 1启动 0关闭
     */
    public void setIsStart(Boolean isStart) {
        this.isStart = isStart;
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
}