//,temp,DatabaseProduct.java,517,534,temp,DatabaseProduct.java,304,328
//,3
public class xxx {
  public boolean supportsGetGeneratedKeys() throws MetaException {
    switch (dbType) {
    case DERBY:
    case CUSTOM:
    case SQLSERVER:
      // The getGeneratedKeys is not supported for multi line insert
      return false;
    case ORACLE:
    case MYSQL:
    case POSTGRES:
      return true;
    case UNDEFINED:
    default:
      String msg = "Unknown database product: " + dbType.toString();
      LOG.error(msg);
      throw new MetaException(msg);
    }
  }

};