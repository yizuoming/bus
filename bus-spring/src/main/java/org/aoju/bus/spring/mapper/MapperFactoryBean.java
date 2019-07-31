package org.aoju.bus.spring.mapper;

import org.aoju.bus.mapper.builder.MapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.FactoryBean;

import static org.aoju.bus.core.lang.Assert.notNull;

/**
 * 增加mapperBuilder
 *
 * @author aoju.org
 * @version 3.0.1
 * @group 839128
 * @since JDK 1.8
 */
public class MapperFactoryBean<T> extends SqlSessionDaoSupport
        implements FactoryBean<T> {

    private Class<T> mapperInterface;
    private boolean addToConfig = true;
    private MapperBuilder mapperBuilder;

    public MapperFactoryBean() {
    }

    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();

        notNull(this.mapperInterface, "Property 'mapperInterface' is required");

        Configuration configuration = getSqlSession().getConfiguration();
        if (this.addToConfig && !configuration.hasMapper(this.mapperInterface)) {
            try {
                configuration.addMapper(this.mapperInterface);
            } catch (Exception e) {
                logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
                throw new IllegalArgumentException(e);
            } finally {
                ErrorContext.instance().reset();
            }
        }
        //直接针对接口处理通用接口方法对应的 MappedStatement 是安全的，通用方法不会出现 IncompleteElementException 的情况
        if (configuration.hasMapper(this.mapperInterface) && mapperBuilder != null && mapperBuilder.isExtendCommonMapper(this.mapperInterface)) {
            mapperBuilder.processConfiguration(getSqlSession().getConfiguration(), this.mapperInterface);
        }
    }

    @Override
    public T getObject() {
        return getSqlSession().getMapper(this.mapperInterface);
    }

    @Override
    public Class<T> getObjectType() {
        return this.mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 返回MyBatis mapper的mapper接口
     */
    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    /**
     * 设置MyBatis mapper的mapper接口
     *
     * @param mapperInterface
     */
    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * 将添加的标志返回到MyBatis配置中。
     * <p>
     * 如果映射器将被添加到MyBatis，则返回true，
     * 如果它还没有被添加到MyBatis中注册。
     */
    public boolean isAddToConfig() {
        return addToConfig;
    }

    /**
     * 如果addToConfig为false，映射器将不会添加到MyBatis。这意味着
     * <p>
     * 它必须包含在mybatisconfig .xml中
     * <p>
     * 如果是真的，映射器将被添加到MyBatis中，如果还没有
     * <p>
     * 注册。默认情况下addToCofig为真。
     * <p>
     *
     * @param addToConfig
     */
    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    /**
     * 设置通用 Mapper 配置
     *
     * @param mapperBuilder
     */
    public void setMapperBuilder(MapperBuilder mapperBuilder) {
        this.mapperBuilder = mapperBuilder;
    }

}