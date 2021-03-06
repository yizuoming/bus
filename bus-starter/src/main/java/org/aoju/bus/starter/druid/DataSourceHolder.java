package org.aoju.bus.starter.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.aoju.bus.core.utils.StringUtils;
import org.aoju.bus.logger.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 数据源信息
 *
 * @author Kimi Liu
 * @version 5.5.2
 * @since JDK 1.8+
 */
public class DataSourceHolder {

    private static final ThreadLocal<String> DATA_SOURCE_KEY = ThreadLocal.withInitial(() -> "dataSource");

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getKey() {
        return DATA_SOURCE_KEY.get();
    }

    /**
     * To switch DataSource
     *
     * @param key the key
     */
    public static void setKey(String key) {
        DATA_SOURCE_KEY.set(key);
    }

    /**
     * To set DataSource as default
     */
    public static void remove() {
        DATA_SOURCE_KEY.remove();
    }

    /* */

    /**
     * 动态增加数据源
     *
     * @param map 数据源属性
     * @return the true/false
     */
    public boolean addDataSource(Map<String, String> map) {
        try {
            String key = map.get("key");
            if (StringUtils.isBlank(key)) return false;
            if (DynamicDataSource.getInstance().containsKey(key)) {
                return true;
            }
            DruidDataSource druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(map);
            druidDataSource.init();
            DynamicDataSource.addDataSource(key, druidDataSource);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Order(-1)
    @Aspect
    @Component
    public class DataSourceSwitch {

        /**
         * 扫描所有含有@DataSource注解的类
         */
        @Pointcut("@annotation(org.aoju.bus.starter.druid.DataSource)")
        public void switching() {
        }

        /**
         * 使用around方式监控
         *
         * @param point 切面信息
         * @return the object
         * @throws Throwable 异常
         */
        @Around("switching()")
        public Object around(ProceedingJoinPoint point) throws Throwable {
            // 获取执行方法
            Method method = ((MethodSignature) point.getSignature()).getMethod();
            // 获取方法的@DataSource注解
            DataSource dataSource = method.getAnnotation(DataSource.class);
            if (!StringUtils.hasLength(dataSource.value())) {
                // 获取类级别的@DataSource注解
                dataSource = method.getDeclaringClass().getAnnotation(DataSource.class);
            }
            if (null != dataSource) {
                // 设置数据源key值
                DataSourceHolder.setKey(dataSource.value());
                Logger.info("Switch datasource to [{}] in method [{}]",
                        DataSourceHolder.getKey(), point.getSignature());
            }
            // 继续执行该方法
            Object object = point.proceed();
            // 恢复默认数据源
            DataSourceHolder.remove();
            Logger.info("Restore datasource to [{}] in method [{}]",
                    DataSourceHolder.getKey(), point.getSignature());
            return object;
        }

    }

}
