//,temp,DatabaseProduct.java,517,534,temp,DatabaseProduct.java,304,328
//,3
public class xxx {
  public String getDBTime() throws MetaException {
    String s;
    switch (dbType) {
    case DERBY:
    case CUSTOM: // ANSI SQL
      s = "values current_timestamp";
      break;

    case MYSQL:
    case POSTGRES:
    case SQLSERVER:
      s = "select current_timestamp";
      break;

    case ORACLE:
      s = "select current_timestamp from dual";
      break;

    default:
      String msg = "Unknown database product: " + dbType.toString();
      LOG.error(msg);
      throw new MetaException(msg);
  }
    return s;
  }

};