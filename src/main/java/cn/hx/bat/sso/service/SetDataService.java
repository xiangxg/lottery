package cn.hx.bat.sso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import cn.hx.bat.sso.entity.Pager;
import cn.hx.bat.sso.mapper.SetDataMapper;
import cn.hx.bat.sso.pojo.Article;
import cn.hx.bat.sso.pojo.SetData;

@Service
public class SetDataService {
	@Autowired
	private SetDataMapper setDataMapper;

	public Pager queryPage(Integer page, Integer rows) {
		int count = setDataMapper.selectCount(null);
		Pager pager = new Pager();
		pager.setStart(page);
		pager.setCount(rows);
		pager.setTotal(count);
		pager.setData(setDataMapper.findPage(pager.getCurPosi(), rows));
		return pager;
	}

	public SetDataMapper getSetDataMapper() {
		return setDataMapper;
	}

	public SetData queryByName(String name) {
		Example example = new Example(SetData.class);
		example.createCriteria().andEqualTo("name", name);

		List<SetData> list = setDataMapper.selectByExample(example);
		if(list!= null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
