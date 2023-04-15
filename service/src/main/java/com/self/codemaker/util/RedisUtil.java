package com.self.codemaker.util;

import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Created by tiansichang on 2019/15/34.
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private GenericObjectPool<StatefulRedisConnection<String, String>> connectionPool;

    private static final String LOCK_SUCCESS = "OK";
    private static final int DEFAULT_SLEEP_TIME = 100;
    private static final long MAX_BLOCK_TIME = 30_000L;
    private static final Long RELEASE_SUCCESS = 1L;


    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
        StatefulRedisConnection connect = getConnect();
        try {
            RedisCommands sync = connect.sync();
            Object result = sync.eval(script, ScriptOutputType.INTEGER, lockKey, requestId);
            if (RELEASE_SUCCESS.equals(result)) {
                log.info("locKey={},requestId={},release lock success", lockKey, requestId);
                return true;
            }
            log.info("locKey={},requestId={},release lock fail", lockKey, requestId);
            return false;
        } catch (Exception e) {
            log.error("releaseDistributedLock error", e);
            log.info("locKey={},requestId={},release lock fail", lockKey, requestId);
            return false;
        } finally {
            release(connect);
        }
    }

    /**
     * 尝试获取阻塞分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    /*注释没有引用的代码 2020-03-13 11:14:04
    public boolean tryGetDistributedBlockLock(String lockKey, String requestId, int expireTime, long blockTime) {
        StatefulRedisConnection connect = getConnect();
        try {
            RedisCommands sync = connect.sync();
            if (blockTime > MAX_BLOCK_TIME) {
                blockTime = MAX_BLOCK_TIME;
            }

            while (blockTime >= 0) {
                SetArgs setArgs = SetArgs.Builder
                        .ex(expireTime)
                        .nx();
                String result = sync.set(lockKey, requestId, setArgs);
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }
                blockTime -= DEFAULT_SLEEP_TIME;
                try {
                    Thread.sleep(DEFAULT_SLEEP_TIME);
                } catch (InterruptedException e) {
                    log.error("tryGetDistributedBlockLock InterruptedException", e);
                }
            }
            return false;
        } catch (Exception e) {
            log.error("tryGetDistributedLock error", e);
            return true;
        } finally {
            release(connect);
        }

    }*/

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        StatefulRedisConnection connect = getConnect();
        try {
            RedisCommands sync = connect.sync();
            SetArgs setArgs = SetArgs.Builder
                    .ex(expireTime)
                    .nx();
            String result = sync.set(lockKey, requestId, setArgs);
            if (LOCK_SUCCESS.equals(result)) {
                log.info("locKey={},requestId={},get lock success", lockKey, requestId);
                return true;
            }
            log.info("locKey={},requestId={},get lock fail", lockKey, requestId);
            return false;
        } catch (Exception e) {
            log.error("tryGetDistributedLock error", e);
            log.info("locKey={},requestId={},get lock error", lockKey, requestId);
            return true;
        } finally {
            release(connect);
        }
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public Boolean getDistributedLock(String lockKey, String requestId, int expireTime) {
        StatefulRedisConnection connect = getConnect();
        try {
            RedisCommands sync = connect.sync();
            SetArgs setArgs = SetArgs.Builder
                    .ex(expireTime)
                    .nx();
            String result = sync.set(lockKey, requestId, setArgs);
            if (LOCK_SUCCESS.equals(result)) {
                log.info("locKey={},requestId={},get lock success", lockKey, requestId);
                return true;
            }
            log.info("locKey={},requestId={},get lock fail", lockKey, requestId);
            return false;
        } catch (Exception e) {
            log.error("tryGetDistributedLock error", e);
            log.info("locKey={},requestId={},get lock error", lockKey, requestId);
            return null;
        } finally {
            release(connect);
        }
    }

    public void setString(String stringKeyName, String value) {
        StatefulRedisConnection connect = getConnect();
        RedisCommands sync = connect.sync();
        try {
            sync.set(stringKeyName, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            release(connect);
        }
    }


    public void setString(String stringKeyName, String value, Integer expireTime) {
        StatefulRedisConnection connect = getConnect();
        RedisCommands sync = connect.sync();
        try {
            SetArgs setArgs = SetArgs.Builder
                    .ex(expireTime)
                    .nx();
            sync.set(stringKeyName, value, setArgs);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            release(connect);
        }
    }

    public String getString(String stringKeyName) {
        StatefulRedisConnection connect = getConnect();
        RedisCommands sync = connect.sync();
        try {
            Object obj = sync.get(stringKeyName);
            if (obj != null) {
                return obj.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            release(connect);
        }
        return "";
    }

    /**
     * 扣除键值
     *
     * @param key
     * @param value
     */
    public Long decrBy(String key, Integer value) {
        StatefulRedisConnection connect = getConnect();
        RedisCommands sync = connect.sync();
        try {
            return sync.decrby(key, value);
        } catch (Exception e) {
            log.error("decrby error", e);
        } finally {
            release(connect);
        }
        return 0L;
    }

    public Long incrBy(String key, Integer value) {
        StatefulRedisConnection connect = getConnect();
        RedisCommands sync = connect.sync();
        try {
            return sync.incrby(key, value);
        } catch (Exception e) {
            log.error("incrby error", e);
        } finally {
            release(connect);
        }
        return 0L;
    }


    public StatefulRedisConnection getConnect() {

        StatefulRedisConnection connection = borrowConnection();
        if (Objects.isNull(connection)) {
            throw new RuntimeException();
        }
        return connection;
    }

    private StatefulRedisConnection borrowConnection() {
        try {
            return connectionPool.borrowObject();
        } catch (Exception e) {
            log.error("borrow connection failure ", e);
        }
        return null;
    }

    private void release(StatefulRedisConnection connect) {
        try {
            connectionPool.returnObject(connect);
        } catch (Exception e) {
            log.error("release connection failure", e);
        }
    }

}
