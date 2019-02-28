package com.cl.model.repo;

import com.cl.model.Vo.CommentVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<CommentVo, Long> {
    long countByContentId(long contentId);
    /**
     * 评论管理
     * @param page
     * @param nums
     * @return
     */
    @Query(value = "select * from t_comment order by contentId desc limit ?1,?2",nativeQuery = true)
    List<CommentVo> findCurPageComment(long page,long nums);

    /**
     * 根据文章id查找对应nums条评论
     * @param articleId
     * @param page
     * @param nums
     * @return
     */
    @Query(value = "select * from t_comment where contentId=?1 order by id desc limit ?2,?3",nativeQuery = true)
    List<CommentVo> findCommentsByArticleId(long articleId,long page,long nums);
}
