package com.cl.service;

import com.cl.model.Vo.UserVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    UserVo findUserById(Integer id);

    @Transactional(propagation = Propagation.REQUIRED)
    void saveUser(UserVo user);

    @Transactional(propagation = Propagation.REQUIRED)
    void deleteUserById(Integer id);

    List<UserVo> findAllUsers();

    UserVo findUserByNameAndPassword(String name, String password);
}
