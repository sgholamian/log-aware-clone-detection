//,temp,SemanticAnalyzer.java,13175,13203,temp,TransactionalValidationListener.java,375,395
//,3
public class xxx {
  public static boolean conformToAcid(Table table) throws MetaException {
    StorageDescriptor sd = table.getSd();
    try {
      Class inputFormatClass = sd.getInputFormat() == null ? null :
          Class.forName(sd.getInputFormat());
      Class outputFormatClass = sd.getOutputFormat() == null ? null :
          Class.forName(sd.getOutputFormat());

      if (inputFormatClass == null || outputFormatClass == null ||
          !Class.forName("org.apache.hadoop.hive.ql.io.AcidInputFormat").isAssignableFrom(inputFormatClass) ||
          !Class.forName("org.apache.hadoop.hive.ql.io.AcidOutputFormat").isAssignableFrom(outputFormatClass)) {
        return false;
      }
    } catch (ClassNotFoundException e) {
      LOG.warn("Could not verify InputFormat=" + sd.getInputFormat() + " or OutputFormat=" +
          sd.getOutputFormat() + "  for " + Warehouse.getQualifiedName(table));
      return false;
    }

    return true;
  }

};