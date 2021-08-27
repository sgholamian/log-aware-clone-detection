//,temp,ShowDatabasesOperation.java,43,66,temp,ShowTablesOperation.java,80,98
//,3
public class xxx {
  private void showTablesExtended() throws HiveException {
    List<Table> tableObjects = new ArrayList<>();
    tableObjects.addAll(context.getDb().getTableObjects(desc.getDbName(), null, desc.getTypeFilter()));
    if (desc.getPattern() != null) {
      Pattern pattern = Pattern.compile(UDFLike.likePatternToRegExp(desc.getPattern()), Pattern.CASE_INSENSITIVE);
      tableObjects = tableObjects.stream()
          .filter(object -> pattern.matcher(object.getTableName()).matches())
          .collect(Collectors.toList());
    }
    Collections.sort(tableObjects, Comparator.comparing(Table::getTableName));
    LOG.debug("Found {} table(s) matching the SHOW EXTENDED TABLES statement.", tableObjects.size());

    try (DataOutputStream os = ShowUtils.getOutputStream(new Path(desc.getResFile()), context)) {
      ShowTablesFormatter formatter = ShowTablesFormatter.getFormatter(context.getConf());
      formatter.showTablesExtended(os, tableObjects);
    } catch (Exception e) {
      throw new HiveException(e, ErrorMsg.GENERIC_ERROR, "in database " + desc.getDbName());
    }
  }

};