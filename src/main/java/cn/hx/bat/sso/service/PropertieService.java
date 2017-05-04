package cn.hx.bat.sso.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertieService {
    
   
	@Value("${account}")
    public String account;
	
	@Value("${password}")
    public String password;
	
	
   
    
   
    
    

}
