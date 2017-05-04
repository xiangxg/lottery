package cn.hx.bat.sso.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import cn.hx.bat.sso.pojo.SetData;

public interface SetDataMapper extends Mapper<SetData> {

	List<SetData> findPage(@Param("page")Integer page, @Param("rows")Integer rows);
}