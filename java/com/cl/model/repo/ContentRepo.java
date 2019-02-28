package com.cl.model.repo;

import com.cl.model.Vo.ContentVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepo extends JpaRepository<ContentVo, Long> {

	// Page<ContentVo> findAll(Pageable pageable);

	ContentVo findById(Long id);

	/**
	 * 本来想用存储过程，但是返回结果集的话不知道怎么操作 这里使用自定义查询方法，指定为本地查询，即此语句原封不动给mysql了
	 * 之前用findByIdBetween，但是当删除文章时，id不连续了
	 * 
	 * @param page
	 * @param nums
	 * @return 从page+1篇文章开始，返回nums篇
	 */
	@Query(value = "SELECT * FROM t_content c WHERE c.id IN"
			+ "(SELECT xx.id FROM (SELECT id FROM t_content LIMIT ?1,?2)AS xx )"
			+ "ORDER BY c.id DESC", nativeQuery = true)
	List<ContentVo> findContents(long page, long nums);

	/**
	 * 分类查找，BINARY支持中文，这里应该没有影响，ucase函数不分大小写
	 * 
	 * @param category_name
	 * @return
	 */
	//@Query(value="select * from t_content c where BINARY ucase(c.categories) like BINARY CONCAT('%',ucase(:category_name),'%')",nativeQuery=true)
	@Query(value="select * from t_content c where ucase(c.categories) like CONCAT('%',ucase(:category_name),'%')",nativeQuery=true)
	List<ContentVo> findByCategories(@Param("category_name")String category_name);

	/**
	 * 标签查找
	 * 
	 * @param tagName
	 * @return
	 */
	@Query(value="select * from t_content c where ucase(c.tags) like CONCAT('%',ucase(?1),'%')",nativeQuery=true)
	List<ContentVo> findByTagsContaining(String tagName);

	/**
	 * 关键词查找
	 * 
	 * @param keyword
	 * @return
	 */
	@Query(value="SELECT * FROM t_content WHERE ucase(title) LIKE CONCAT('%',ucase(?1),'%')",nativeQuery=true)
	List<ContentVo> findByTitleContaining(String keyword);
}
