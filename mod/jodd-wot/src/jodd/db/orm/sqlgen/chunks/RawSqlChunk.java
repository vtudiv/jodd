// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

package jodd.db.orm.sqlgen.chunks;

/**
 * Simply holds hard-coded SQL string that will be appended to the result.
 */
public class RawSqlChunk extends SqlChunk {

	protected final String sql;

	public RawSqlChunk(String sql) {
		super(CHUNK_RAW);
		this.sql = sql;
	}

	@Override
	public void process(StringBuilder out) {
		out.append(sql);
	}

	// ---------------------------------------------------------------- clone

	@Override
	public SqlChunk clone() {
		return new RawSqlChunk(sql);
	}
}