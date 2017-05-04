package cn.hx.bat.sso.pojo;

import java.util.Date;
import javax.persistence.*;
@Table(name = "lottery")
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 地区
     */
    private String area;

    /**
     * 彩种
     */
    private String name;

    /**
     * 中奖号码
     */
    @Column(name = "lottery_num")
    private String lotteryNum;

    /**
     * 日期数
     */
    @Column(name = "day_periods")
    private String dayPeriods;

    /**
     * 总期数
     */
    @Column(name = "toal_periods")
    private String toalPeriods;

    /**
     * 开奖日期
     */
    @Column(name = "open_time")
    private Date openTime;

    /**
     * 奖池缓存
     */
    @Column(name = "prize_cache")
    private String prizeCache;

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
     * 获取地区
     *
     * @return area - 地区
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置地区
     *
     * @param area 地区
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取彩种
     *
     * @return name - 彩种
     */
    public String getName() {
        return name;
    }

    /**
     * 设置彩种
     *
     * @param name 彩种
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取中奖号码
     *
     * @return lottery_num - 中奖号码
     */
    public String getLotteryNum() {
        return lotteryNum;
    }

    /**
     * 设置中奖号码
     *
     * @param lotteryNum 中奖号码
     */
    public void setLotteryNum(String lotteryNum) {
        this.lotteryNum = lotteryNum;
    }

    /**
     * 获取日期数
     *
     * @return day_periods - 日期数
     */
    public String getDayPeriods() {
        return dayPeriods;
    }

    /**
     * 设置日期数
     *
     * @param dayPeriods 日期数
     */
    public void setDayPeriods(String dayPeriods) {
        this.dayPeriods = dayPeriods;
    }

    /**
     * 获取总期数
     *
     * @return toal_periods - 总期数
     */
    public String getToalPeriods() {
        return toalPeriods;
    }

    /**
     * 设置总期数
     *
     * @param toalPeriods 总期数
     */
    public void setToalPeriods(String toalPeriods) {
        this.toalPeriods = toalPeriods;
    }

    /**
     * 获取开奖日期
     *
     * @return open_time - 开奖日期
     */
    public Date getOpenTime() {
        return openTime;
    }

    /**
     * 设置开奖日期
     *
     * @param openTime 开奖日期
     */
    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    /**
     * 获取奖池缓存
     *
     * @return prize_cache - 奖池缓存
     */
    public String getPrizeCache() {
        return prizeCache;
    }

    /**
     * 设置奖池缓存
     *
     * @param prizeCache 奖池缓存
     */
    public void setPrizeCache(String prizeCache) {
        this.prizeCache = prizeCache;
    }
}