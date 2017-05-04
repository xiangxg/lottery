package cn.hx.bat.sso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import cn.hx.bat.sso.mapper.UserMapper;
import cn.hx.bat.sso.pojo.SetData;
import cn.hx.bat.sso.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	

	public User queryUserByAccountAndPassword(String account, String password) {
		Example example = new Example(User.class);
		example.createCriteria().andEqualTo("username", account).andEqualTo("password", password);

		List<User> list = userMapper.selectByExample(example);
		if(list!= null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
}
