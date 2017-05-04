package cn.hx.bat.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.hx.bat.sso.pojo.Article;

public interface ArticleMapper extends Mapper<Article> {
	void deleteRedundantData( @Param("type")Integer type,  @Param("num")Integer dataKeepAmount);

	List<Article> findPage(@Param("start")Integer start, @Param("count")Integer count, @Param("type")Byte type);
}