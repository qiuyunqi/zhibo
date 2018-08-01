package com.zhibo.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 
 * @author Administrator
 * 
 * @param <T>
 * @param <ID>
 */
@SuppressWarnings("all")
public interface BaseDao<T, ID extends Serializable> {

	public SessionFactory getSessionFactory();

	public Session getSession();

	public HibernateTemplate getHibernateTemplate();

	public JdbcTemplate getJdbcTemplate();

	public void reload(T entity);

	public T merge(T entity);

	public T get(ID id);

	public void save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public void delete(ID id);

	public int countQueryResult(String hql, List<Object> valueList);

	public int countQueryResult(String hql);

	public int countSqlQueryResult(String sql, Object... values);

	public void saveOrUpdateAll(final Collection<T> collection);

	public List<T> findListByDetachedCriteria(DetachedCriteria detachedCriteria);

	public List<String> findCollectionByHql(String hql);

	public List<T> findAll();

	public List<T> findAllByHQL(String hql);

	public List<T> findAllByHQL(String hql, Object value);

	public List<T> findAllByHQL(String hql, Object... values);

	public List<T> findAllByHQL(String hql, List<Object> valueList);

	public T findUniqueByHQL(String hql, Object... values);

	public List<T> findListByHQL(String hql, Object... values);

	public List<T> findListByHQL(int fromIndex, int size, String hql, List<Object> valueList);

	public List<Object> findParamListByHQL(String hql, Object... values);

	public List<Object[]> findParamsListByHQL(String hql, Object... values);

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	public String getIdName(Class clazz);

	/**
	 * 通过sql，根据list类型的条件参数 查询对象数组集合
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */

	public List<Object[]> findBySqlGetArray(final String sql, final List paramList);

	public List<Object[]> findListBySql(final int fromIndex, final int size, final String sql, final List paramList);

}