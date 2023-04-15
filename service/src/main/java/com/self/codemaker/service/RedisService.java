package com.self.codemaker.service;

/**
 * @author huangchangjun
 * @date 2023/4/15
 */
public interface RedisService {

    public void putString(String key, String val);

    public String getString(String key);


}
