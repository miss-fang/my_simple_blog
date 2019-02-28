package com.cl.service;

import com.cl.model.Vo.LinkVo;
import com.cl.model.repo.LinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkRepo repo;

    @Override
    public List<LinkVo> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteLink(Integer id) {
        repo.delete(id);
    }

    @Override
    public void saveLink(LinkVo link) {
        repo.save(link);
    }

    @Override
    public LinkVo findById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public LinkVo findByName(String name) {
        return repo.findByName(name);
    }
}
