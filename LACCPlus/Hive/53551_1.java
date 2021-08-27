//,temp,SemanticAnalyzer.java,13175,13203,temp,TransactionalValidationListener.java,375,395
//,3
public class xxx {
  private Map<String, String> convertToAcidByDefault(
      StorageFormat storageFormat, String qualifiedTableName, List<Order> sortCols,
      Map<String, String> retValue) {
    /*for CTAS, TransactionalValidationListener.makeAcid() runs to late to make table Acid
     so the initial write ends up running as non-acid...*/
    try {
      Class inputFormatClass = storageFormat.getInputFormat() == null ? null :
          Class.forName(storageFormat.getInputFormat());
      Class outputFormatClass = storageFormat.getOutputFormat() == null ? null :
          Class.forName(storageFormat.getOutputFormat());
      if (inputFormatClass == null || outputFormatClass == null ||
          !AcidInputFormat.class.isAssignableFrom(inputFormatClass) ||
          !AcidOutputFormat.class.isAssignableFrom(outputFormatClass)) {
        return retValue;
      }
    } catch (ClassNotFoundException e) {
      LOG.warn("Could not verify InputFormat=" + storageFormat.getInputFormat() + " or OutputFormat=" +
          storageFormat.getOutputFormat() + "  for " + qualifiedTableName);
      return retValue;
    }
    if (sortCols != null && !sortCols.isEmpty()) {
      return retValue;
    }
    retValue.put(hive_metastoreConstants.TABLE_IS_TRANSACTIONAL, "true");
    retValue.put(hive_metastoreConstants.TABLE_TRANSACTIONAL_PROPERTIES,
        TransactionalValidationListener.DEFAULT_TRANSACTIONAL_PROPERTY);
    LOG.info("Automatically chose to make " + qualifiedTableName + " acid.");
    return retValue;
  }

};