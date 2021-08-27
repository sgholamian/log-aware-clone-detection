//,temp,ShowViewsOperation.java,45,69,temp,ShowMaterializedViewsOperation.java,46,73
//,3
public class xxx {
  @Override
  public int execute() throws HiveException {
    if (!context.getDb().databaseExists(desc.getDbName())) {
      throw new HiveException(ErrorMsg.DATABASE_NOT_EXISTS, desc.getDbName());
    }

    List<String> viewNames = context.getDb().getTablesByType(desc.getDbName(), null, TableType.VIRTUAL_VIEW);
    if (desc.getPattern() != null) {
      Pattern pattern = Pattern.compile(UDFLike.likePatternToRegExp(desc.getPattern()), Pattern.CASE_INSENSITIVE);
      viewNames = viewNames.stream()
          .filter(name -> pattern.matcher(name).matches())
          .collect(Collectors.toList());
    }
    Collections.sort(viewNames);
    LOG.debug("Found {} view(s) matching the SHOW VIEWS statement.", viewNames.size());

    try (DataOutputStream os = ShowUtils.getOutputStream(new Path(desc.getResFile()), context)) {
      ShowTablesFormatter formatter = ShowTablesFormatter.getFormatter(context.getConf());
      formatter.showTables(os, viewNames);
    } catch (Exception e) {
      throw new HiveException(e, ErrorMsg.GENERIC_ERROR, "in database" + desc.getDbName());
    }

    return 0;
  }

};