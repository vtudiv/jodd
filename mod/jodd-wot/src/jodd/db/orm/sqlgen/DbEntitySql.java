// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

package jodd.db.orm.sqlgen;

import static jodd.db.orm.sqlgen.DbSqlBuilder.sql;
import jodd.db.orm.DbOrmManager;
import jodd.db.orm.DbEntityDescriptor;
import static jodd.db.orm.DbNameUtil.convertTableNameToClassName;
import static jodd.db.orm.DbNameUtil.convertColumnNameToPropertyName;
import static jodd.util.StringPool.EQUALS;
import static jodd.util.StringPool.SPACE;
import static jodd.util.StringUtil.uncapitalize;
import static jodd.util.StringUtil.capitalize;
import jodd.bean.BeanUtil;

/**
 * Useful {@link DbSqlBuilder} generators.
 */
public class DbEntitySql {

	private static final String DELETE_FROM = "delete from ";
	private static final String WHERE = " where ";
	private static final String UPDATE = "update ";
	private static final String SELECT = "select ";
	private static final String FROM = " from ";
	private static final String SELECT_COUNT_1_FROM = "select count(1) from ";

	// ---------------------------------------------------------------- insert

	/**
	 * Creates INSERT query for the entity.
	 */
	public static DbSqlBuilder insert(Object entity) {
		return sql().insert(entity);
	}

	// ---------------------------------------------------------------- truncate

	/**
	 * Creates DELETE query that truncates all table data.
	 */
	public static DbSqlBuilder truncate(Object entity) {
		return sql()._(DELETE_FROM).table(entity, null);
	}

	// ---------------------------------------------------------------- update

	/**
	 * Creates UPDATE query that updates all non-null values of an entity that is matched by id.
	 */
	public static DbSqlBuilder update(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(UPDATE).table(entity, tableRef).set(tableRef, entity)._(WHERE).matchIds(tableRef, entity);
	}

	/**
	 * Creates UPDATE query that updates all values of an entity that is matched by id.
	 */
	public static DbSqlBuilder updateAll(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(UPDATE).table(entity, tableRef).setAll(tableRef, entity)._(WHERE).matchIds(tableRef, entity);
	}


	// ---------------------------------------------------------------- delete

	/**
	 * Creates DELETE query that deletes entity matched by non-null values.
	 */
	public static DbSqlBuilder delete(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(DELETE_FROM).table(entity, tableRef)._(WHERE).match(tableRef, entity);
	}

	/**
	 * Creates DELETE query that deletes entity matched by all values.
	 */
	public static DbSqlBuilder deleteByAll(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(DELETE_FROM).table(entity, tableRef)._(WHERE).matchAll(tableRef, entity);
	}

	// ---------------------------------------------------------------- delete by id

	/**
	 * Creates DELETE query that deletes entity by ID.
	 */
	public static DbSqlBuilder deleteById(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(DELETE_FROM).table(entity, tableRef)._(WHERE).matchIds(tableRef, entity);
	}

	/**
	 * Creates DELETE query that deletes entity by ID.
	 */
	public static DbSqlBuilder deleteById(Object entityType, Number id) {
		String tableRef = createTableRefName(entityType);
		return sql()._(DELETE_FROM).table(entityType, tableRef)._(WHERE).refId(tableRef)._(EQUALS).value(id);
	}


	// ---------------------------------------------------------------- from

	/**
	 * Creates 'SELECT all FROM entity' part of the SQL query that can be easily extended.
	 * Entity is referred with its simple class name.
	 */
	public static DbSqlBuilder from(Object entity) {
		return from(entity, createTableRefName(entity));
	}

	public static DbSqlBuilder from(Object entity, String tableRef) {
		return sql()._(SELECT).column(tableRef)._(FROM).table(entity, tableRef)._(SPACE);
	}

	// ---------------------------------------------------------------- find

	/**
	 * Creates SELECT criteria for the entity matched by non-null values.
	 */
	public static DbSqlBuilder find(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(SELECT).column(tableRef)._(FROM).table(entity, tableRef)._(WHERE).match(tableRef, entity);
	}

	/**
	 * Creates SELECT criteria for the entity matched by all values.
	 */
	public static DbSqlBuilder findByAll(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(SELECT).column(tableRef)._(FROM).table(entity, tableRef)._(WHERE).matchAll(tableRef, entity);
	}

	/**
	 * Creates SELECT criteria for the entity matched by column name
	 */
	public static DbSqlBuilder findByColumn(Class entity, String column, Object value) {
		String tableRef = createTableRefName(entity);
		return sql()._(SELECT).column(tableRef)._(FROM).table(entity, tableRef)._(WHERE).ref(tableRef, column)._(EQUALS).value(value);
	}

	/**
	 * Creates SELECT criteria for the entity matched by foreign key.
	 * Foreign key is created by concatenating foreign table name and column name.
	 */
	public static DbSqlBuilder findForeign(Class entity, Object value) {
		String tableRef = createTableRefName(entity);
		DbOrmManager dbOrmManager = DbOrmManager.getInstance();
		DbEntityDescriptor dedFk = dbOrmManager.lookupType(value.getClass());

		String fkColum =
				uncapitalize(convertTableNameToClassName(dedFk.getTableName())) +
				capitalize(convertColumnNameToPropertyName(dedFk.getIdColumnName()));
		Object idValue = BeanUtil.getDeclaredPropertySilently(value, dedFk.getIdPropertyName());
		return sql()._(SELECT).column(tableRef)._(FROM).table(entity, tableRef)._(WHERE).ref(tableRef, fkColum)._(EQUALS).value(idValue);
	}

	// ---------------------------------------------------------------- find by Id

	/**
	 * Creates SELECT criteria for the entity matched by id.
	 */
	public static DbSqlBuilder findById(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(SELECT).column(tableRef)._(FROM).table(entity, tableRef)._(WHERE).matchIds(tableRef, entity);
	}

	/**
	 * Creates SELECT criteria for the entity matched by id.
	 */
	public static DbSqlBuilder findById(Object entityType, Number id) {
		String tableRef = createTableRefName(entityType);
		return sql()._(SELECT).column(tableRef)._(FROM).table(entityType, tableRef)._(WHERE).refId(tableRef)._(EQUALS).value(id);
	}

	// ---------------------------------------------------------------- count

	/**
	 * Creates SELECT COUNT criteria for the entity matched by non-null values.
	 */
	public static DbSqlBuilder count(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(SELECT_COUNT_1_FROM).table(entity, tableRef)._(WHERE).match(tableRef, entity);
	}


	/**
	 * Creates SELECT COUNT criteria for the entity matched by all values.
	 */
	public static DbSqlBuilder countAll(Object entity) {
		String tableRef = createTableRefName(entity);
		return sql()._(SELECT_COUNT_1_FROM).table(entity, tableRef)._(WHERE).matchAll(tableRef, entity);
	}


	// ---------------------------------------------------------------- resolve tableRef

	/**
	 * Creates table reference name from entity type.
	 */
	protected static String createTableRefName(Object entity) {
		Class type = entity.getClass();
		type = (type == Class.class ? (Class) entity : type);
		return type.getSimpleName();
	}

}