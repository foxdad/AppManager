package it.lt.service.dev;

import it.lt.entity.DevUser;

/**
 * @Atuhor LT
 * @Date Create in  2019/11/26
 */
public interface IUserService {
    DevUser findByName(String username,String password);
}
