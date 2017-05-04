package cn.hx.bat.sso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import cn.hx.bat.sso.entity.Pager;
import cn.hx.bat.sso.mapper.LotteryMapper;
import cn.hx.bat.sso.pojo.Lottery;

@Service
public class LotteryService {
	@Autowired
	private LotteryMapper lotteryMapper;

	
	public LotteryMapper getLotteryMapper() {
		return lotteryMapper;
	}


	public List<Lottery> queryLottery(Lottery lot){
		Example example = new Example(Lottery.class);
        example.createCriteria().andEqualTo("area", lot.getArea())
        		.andEqualTo("name", lot.getName()).andEqualTo("toalPeriods", lot.getToalPeriods());
       
        return lotteryMapper.selectByExample(example);
        
	}


	public void findPage(Pager pager, String type, String area) {
		Lottery lo = new Lottery();
		lo.setName(type);
		lo.setArea(area);
		int count = lotteryMapper.selectCount(lo);
		 pager.setTotal(count);
		 pager.setData(lotteryMapper.findPage(pager.getCurPosi(), pager.getCount(),type,area));
		
	}
	
}
