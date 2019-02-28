package com.cl.service;

import com.cl.model.Vo.ContentVo;
import com.cl.model.repo.ContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ContentRepo repo;

	@Override
	public void saveArticle(ContentVo article) {
		repo.save(article);
	}

	/**
	 * 本来想用存储过程，但是返回结果集的话不知道怎么操作 这里使用自定义查询方法，指定为本地查询，即此语句原封不动给mysql了
	 * 之前用findByIdBetween，但是当删除文章时，id不连续了
	 *
	 * @param page
	 *            当前页
	 * @param nums
	 *            查询几条
	 * @return
	 */
	@Override
	public List<ContentVo> findCurPageArticles(Integer page, long nums) {
		if (page == null)
			page = 0;
		long total = getTotal();
		long from = total - nums * (page + 1);
		List<ContentVo> articles;
		if (from >= 0)
			articles = repo.findContents(from, nums);
		else {
			// 如果limit第一个参数小于0，则置为0，同时第二个参数为每页数+这个负值
			// 表示最后一页不足nums篇
			articles = repo.findContents(0L, nums + from);
		}
		return articles;
	}

	@Override
	public void deleteArticle(Long id) {
		repo.delete(id);
	}

	@Override
	public ContentVo findArticleById(Long id) {
		return repo.findById(id);
	}

	/**
	 * 指定排序方式返回所有文章
	 *
	 * @param
	 * @return
	 */
	/*
	 * @Override public List<ContentVo> findAllArticles(Pageable pageable) { return
	 * repo.findAll(pageable).getContent(); }
	 */
	@Override
	public long getTotal() {
		return repo.count();
	}

	@Override
	public List<ContentVo> findByCategory(String categoryName) {
		return repo.findByCategories(categoryName);
	}

	@Override
	public List<ContentVo> findByTag(String tagName) {
		return repo.findByTagsContaining(tagName);
	}

	@Override
	public List<ContentVo> findByKeyword(String keyword) {
		return repo.findByTitleContaining(keyword);
	}
}
