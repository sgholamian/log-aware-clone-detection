//,temp,UpgradeTool.java,614,635,temp,HiveMetaStoreClient.java,4343,4365
//,3
public class xxx {
  private static void makeConvertTableScript(List<String> alterTableAcid, List<String> alterTableMm,
      String scriptLocation) throws IOException {
    if (alterTableAcid.isEmpty()) {
      LOG.info("No acid conversion is necessary");
    } else {
      String fileName = "convertToAcid_" + System.currentTimeMillis() + ".sql";
      LOG.debug("Writing CRUD conversion commands to " + fileName);
      try(PrintWriter pw = createScript(alterTableAcid, fileName, scriptLocation)) {
        pw.println("-- These commands may be executed by Hive 3.x later");
      }
    }

    if (alterTableMm.isEmpty()) {
      LOG.info("No managed table conversion is necessary");
    } else {
      String fileName = "convertToMM_" + System.currentTimeMillis() + ".sql";
      LOG.debug("Writing managed table conversion commands to " + fileName);
      try(PrintWriter pw = createScript(alterTableMm, fileName, scriptLocation)) {
        pw.println("-- These commands must be executed by Hive 3.0 or later");
      }
    }
  }

};