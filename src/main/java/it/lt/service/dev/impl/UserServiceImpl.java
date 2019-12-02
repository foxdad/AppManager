package it.lt.service.dev.impl;

import it.lt.dao.dev.IDevUser;
import it.lt.entity.DevUser;
import it.lt.service.dev.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
@Service
public class UserServiceImpl implements IUserService {
    //
    @Autowired
    private IDevUser user;
    @Override
    public DevUser findByName(String username, String password) {
        return user.findByName(username,password);
    }
}
