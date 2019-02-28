package com.cl.service;

import com.cl.model.Vo.LinkVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LinkService {
    List<LinkVo> findAll();
    @Transactional(propagation = Propagation.REQUIRED)
    void deleteLink(Integer id);

    @Transactional(propagation = Propagation.REQUIRED)
    void saveLink(LinkVo link);
    LinkVo findByName(String name);
    LinkVo findById(Integer id);
}
