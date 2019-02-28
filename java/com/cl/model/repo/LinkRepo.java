package com.cl.model.repo;

import com.cl.model.Vo.LinkVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepo extends JpaRepository<LinkVo, Integer> {
    LinkVo findByName(String name);
    LinkVo findById(Integer id);
}
