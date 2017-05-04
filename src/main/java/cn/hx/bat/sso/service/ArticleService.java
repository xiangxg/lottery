package cn.hx.bat.sso.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import cn.hx.bat.sso.entity.Pager;
import cn.hx.bat.sso.mapper.ArticleMapper;
import cn.hx.bat.sso.pojo.Article;
import cn.hx.bat.sso.pojo.Lottery;

@Service
public class ArticleService {
	@Autowired
	private ArticleMapper articleMapper;

	public ArticleMapper getArticleMapper() {
		return articleMapper;
	}
	
	public List<Article> queryArticle(String title ,Byte type){
		Example example = new Example(Article.class);
        example.createCriteria().andEqualTo("title", title)
        		.andEqualTo("type", type);
       
       return  articleMapper.selectByExample(example);
	}

	public Pager queryPage(Integer start, Integer count) {
		
		return null;
	}

	public void queryPage(Pager pager,Byte type) {
		Article article = new Article();
		article.setType(type);
		int count = articleMapper.selectCount(null);
		 pager.setTotal(count);
		 pager.setData(articleMapper.findPage(pager.getCurPosi(), pager.getCount(),type));
		
		
	}

}
