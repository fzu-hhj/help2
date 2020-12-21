package fzu.hhj.help2.service;

import java.util.Map;

public interface UserService {

    Map<String, Object> loginByPassword(Integer id, String password);
}
