package com.cl.model.repo;

import com.cl.model.Vo.UserVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserVo, Integer> {
    UserVo findById(Integer id);

    UserVo findByUsernameAndPassword(String username, String password);
}
