package com.lyranxi.link.mysql.biz;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.reflect.GenericTypeUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author ranxi
 * @date 2025-03-11 17:42
 */
@SuppressWarnings("unchecked")
public class BaseBiz<M extends BaseMapper<T>, T> {

    protected Log log = LogFactory.getLog(getClass());
    /**
     * 默认批次提交数量
     */
    protected int DEFAULT_BATCH_SIZE = 1000;

    @Autowired
    protected M baseMapper;

    protected final Class<?>[] typeArguments = GenericTypeUtils.resolveTypeArguments(getClass(), BaseBiz.class);

    protected Class<M> mapperClass = currentMapperClass();
    protected Class<T> entityClass = currentEntityClass();

    public boolean insert(T entity) {
        return baseMapper.insert(entity) > 0;
    }

    public boolean batchInsert(Collection<T> entities) {
        return batchInsert(entities, DEFAULT_BATCH_SIZE);
    }

    public boolean batchInsert(Collection<T> entities, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entities, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entities));
    }

    public boolean deleteByPrimaryKey(Serializable primaryKey) {
        return SqlHelper.retBool(baseMapper.deleteById(primaryKey));
    }

    public boolean batchDeleteByPrimaryKeys(Collection<? extends Serializable> primaryKeys) {
        return SqlHelper.retBool(baseMapper.deleteBatchIds(primaryKeys));
    }

    public boolean updateByPrimaryKey(T entity) {
        return SqlHelper.retBool(baseMapper.updateById(entity));
    }

    public boolean batchUpdateByPrimaryKeys(Collection<T> entities, int batchSize) {
        String sqlStatement = getSqlStatement(SqlMethod.UPDATE_BY_ID);
        return executeBatch(entities, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    public T getByPrimaryKey(Serializable primaryKey){
        return baseMapper.selectById(primaryKey);
    }

    public List<T> batchGetByPrimaryKeys(Collection<? extends Serializable> primaryKeys){
        return baseMapper.selectBatchIds(primaryKeys);
    }

    public boolean batchUpdateByPrimaryKeys(Collection<T> entities) {
        return batchUpdateByPrimaryKeys(entities, DEFAULT_BATCH_SIZE);
    }


    protected String getSqlStatement(SqlMethod method) {
        return SqlHelper.getSqlStatement(mapperClass, method);
    }

    protected <E> boolean executeBatch(Collection<E> entities, int batchSize, BiConsumer<SqlSession, E> consumer) {
        return SqlHelper.executeBatch(this.entityClass, this.log, entities, batchSize, consumer);
    }


    protected Class<M> currentMapperClass() {
        return (Class<M>) typeArguments[0];
    }

    protected Class<T> currentEntityClass() {
        return (Class<T>) typeArguments[1];
    }

}
