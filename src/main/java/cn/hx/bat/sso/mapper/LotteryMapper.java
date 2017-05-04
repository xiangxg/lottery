package cn.hx.bat.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.hx.bat.sso.pojo.Lottery;
import tk.mybatis.mapper.common.Mapper;

public interface LotteryMapper extends Mapper<Lottery> {

	List<Lottery> queryGroup(@Param("num")Integer dataKeepAmount);

	void deleteRedundantData(@Param("name")String name,@Param("area") String area, @Param("num")Integer dataKeepAmount);

	List<Lottery> findPage(@Param("start") Integer start, @Param("count")Integer count, @Param("name")String type,@Param("area")  String area);
}