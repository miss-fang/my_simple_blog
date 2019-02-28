package com.cl.service;

import com.cl.model.Vo.ContentVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService {
	@Transactional(propagation = Propagation.REQUIRED)
	void saveArticle(ContentVo article);

	// List<ContentVo> findAllArticles(Pageable pageable);

	/**
	 *
	 * @param page
	 *            当前页
	 * @param nums
	 *            查询几条
	 * @return
	 */
	List<ContentVo> findCurPageArticles(Integer page, long nums);

	ContentVo findArticleById(Long id);

	List<ContentVo> findByCategory(String categoryName);

	List<ContentVo> findByTag(String tagName);

	long getTotal();

	@Transactional(propagation = Propagation.REQUIRED)
	void deleteArticle(Long id);

	List<ContentVo> findByKeyword(String keyword);
}
