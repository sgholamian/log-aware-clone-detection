//,temp,MetaStoreDirectSql.java,256,266,temp,ObjectStore.java,410,420
//,2
public class xxx {
  static String getProductName(PersistenceManager pm) {
    JDOConnection jdoConn = pm.getDataStoreConnection();
    try {
      return ((Connection)jdoConn.getNativeConnection()).getMetaData().getDatabaseProductName();
    } catch (Throwable t) {
      LOG.warn("Error retrieving product name", t);
      return null;
    } finally {
      jdoConn.close(); // We must release the connection before we call other pm methods.
    }
  }

};