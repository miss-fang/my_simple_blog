package com.cl.service;

import com.cl.model.Vo.CommentVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    @Transactional(propagation = Propagation.REQUIRED)
    void saveComment(CommentVo comment);

    /**
     * 只查找10条
     * @param page
     * @return
     */
    List<CommentVo> findCurPageComments(Integer page,long nums);

    /**
     * 查找对应文章的nums条评论
     * @param articleId
     * @param page
     * @param nums
     * @return
     */
    List<CommentVo> findCurPageCommentsByArticleId(long articleId,Integer page,long nums);
    @Transactional(propagation = Propagation.REQUIRED)
    void deleteComment(Long id);
    long findCommentNumsByArticleId(long id);
    long getTotal();
}
