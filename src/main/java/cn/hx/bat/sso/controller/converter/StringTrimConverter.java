package cn.hx.bat.sso.controller.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * <p>Title: CustomDateConverter</p>
 * <p>Description: 自定义日期转换器</p>
 */
public class StringTrimConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		
		try {
			//去掉字符串两边空格，如果去除后为空设置为null
			if(source!=null){
				source = source.trim();
				if(source.equals("")){
					return null;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return source;
	}

}
