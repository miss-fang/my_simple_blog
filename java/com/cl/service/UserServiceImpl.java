package com.cl.service;

import com.cl.model.Vo.UserVo;
import com.cl.model.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo repo;

    @Override
    public UserVo findUserById(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void saveUser(UserVo user) {
        repo.save(user);
    }

    @Override
    public List<UserVo> findAllUsers() {
        return repo.findAll();
    }

    @Override
    public void deleteUserById(Integer id) {
        repo.delete(id);
    }

    @Override
    public UserVo findUserByNameAndPassword(String name, String password) {
        return repo.findByUsernameAndPassword(name,password);
    }
}
