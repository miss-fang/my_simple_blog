package com.cl.service;

import com.cl.model.Vo.CommentVo;
import com.cl.model.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo repo;

    @Override
    public void saveComment(CommentVo comment) {
        repo.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        repo.delete(id);
    }

    @Override
    public List<CommentVo> findCurPageComments(Integer page, long nums) {
        List<CommentVo> comments;
        if (page == null)
            page = 0;
        long total = getTotal();
        long from = total - nums * (page + 1);
        if (from >= 0)
            comments = repo.findCurPageComment(from, nums);
        else {
            comments = repo.findCurPageComment(0l, nums + from);
        }
        return comments;
    }

    @Override
    public List<CommentVo> findCurPageCommentsByArticleId(long articleId, Integer page, long nums) {
        List<CommentVo> comments;
/*        if (page == null)
            page = 0;
        long total = findCommentNumsByArticleId(articleId);
        long from = total - nums * (page + 1);
        if (from >= 0)
            comments = repo.findCommentsByArticleId(articleId,from, nums);
        else {
            comments = repo.findCommentsByArticleId(articleId,0, nums + from);
        }*/
        comments = repo.findCommentsByArticleId(articleId,0, 9);
        return comments;
    }

    @Override
    public long findCommentNumsByArticleId(long id) {
        return repo.countByContentId(id);
    }

    @Override
    public long getTotal() {
        return repo.count();
    }

}
