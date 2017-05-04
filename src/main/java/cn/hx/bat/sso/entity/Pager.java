package cn.hx.bat.sso.entity;

import java.io.Serializable;

public class Pager implements Serializable{

	private Integer total;//总条数
	private Integer start;//第几页
	private Integer count=20;//查询条数
	private Integer curPosi;//当前位置
	private Object data;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public Integer getCurPosi() {
		if(start==1){
			curPosi=0;
		}else{
			curPosi=(start-1)*count;
		}
		
		return curPosi;
	}
	
	
}
