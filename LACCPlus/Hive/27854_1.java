//,temp,ShowTablesOperation.java,62,78,temp,ShowDataConnectorsOperation.java,43,66
//,3
public class xxx {
  private void showTables() throws HiveException {
    List<String> tableNames = new ArrayList<>(
        context.getDb().getTablesByType(desc.getDbName(), null, desc.getTypeFilter()));
    if (desc.getPattern() != null) {
      Pattern pattern = Pattern.compile(UDFLike.likePatternToRegExp(desc.getPattern()), Pattern.CASE_INSENSITIVE);
      tableNames = tableNames.stream().filter(name -> pattern.matcher(name).matches()).collect(Collectors.toList());
    }
    Collections.sort(tableNames);
    LOG.debug("Found {} table(s) matching the SHOW TABLES statement.", tableNames.size());

    try (DataOutputStream os = ShowUtils.getOutputStream(new Path(desc.getResFile()), context)) {
      ShowTablesFormatter formatter = ShowTablesFormatter.getFormatter(context.getConf());
      formatter.showTables(os, tableNames);
    } catch (Exception e) {
      throw new HiveException(e, ErrorMsg.GENERIC_ERROR, "in database " + desc.getDbName());
    }
  }

};