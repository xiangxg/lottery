package cn.hx.bat.sso.pojo;

import java.util.Date;
import javax.persistence.*;
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型 1 资讯 2预测
     */
    private Byte type;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 详情
     */
    private String info;
    
    /**
     * 描述
     */
    private String description;
    

    

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取类型 1 资讯 2预测
     *
     * @return type - 类型 1 资讯 2预测
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型 1 资讯 2预测
     *
     * @param type 类型 1 资讯 2预测
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取发布时间
     *
     * @return publish_time - 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取详情
     *
     * @return info - 详情
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置详情
     *
     * @param info 详情
     */
    public void setInfo(String info) {
        this.info = info;
    }
}