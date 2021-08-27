//,temp,MySQLConnectorProvider.java,30,39,temp,DerbySQLConnectorProvider.java,31,41
//,2
public class xxx {
  @Override protected ResultSet fetchTableNames() throws MetaException {
    ResultSet rs = null;
    try {
      rs = getConnection().getMetaData().getTables(scoped_db, null, null, new String[] { "TABLE" });
    } catch (SQLException sqle) {
      LOG.warn("Could not retrieve table names from remote datasource, cause:" + sqle.getMessage());
      throw new MetaException("Could not retrieve table names from remote datasource, cause:" + sqle);
    }
    return rs;
  }

};