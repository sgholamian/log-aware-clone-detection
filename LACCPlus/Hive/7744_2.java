//,temp,ShowViewsOperation.java,45,69,temp,ShowMaterializedViewsOperation.java,46,73
//,3
public class xxx {
  @Override
  public int execute() throws HiveException {
    if (!context.getDb().databaseExists(desc.getDbName())) {
      throw new HiveException(ErrorMsg.DATABASE_NOT_EXISTS, desc.getDbName());
    }

    // the returned list is not sortable as it is immutable, thus it must be put into a new ArrayList
    List<Table> viewObjects = new ArrayList<>(
        context.getDb().getMaterializedViewObjectsByPattern(desc.getDbName(), null));
    if (desc.getPattern() != null) {
      Pattern pattern = Pattern.compile(UDFLike.likePatternToRegExp(desc.getPattern()), Pattern.CASE_INSENSITIVE);
      viewObjects = viewObjects.stream()
          .filter(object -> pattern.matcher(object.getTableName()).matches())
          .collect(Collectors.toList());
    }
    Collections.sort(viewObjects, Comparator.comparing(Table::getTableName));
    LOG.debug("Found {} materialized view(s) matching the SHOW MATERIALIZED VIEWS statement.", viewObjects.size());

    try (DataOutputStream os = ShowUtils.getOutputStream(new Path(desc.getResFile()), context)) {
      Collections.sort(viewObjects, Comparator.comparing(Table::getTableName));
      ShowMaterializedViewsFormatter formatter = ShowMaterializedViewsFormatter.getFormatter(context.getConf());
      formatter.showMaterializedViews(os, viewObjects);
    } catch (Exception e) {
      throw new HiveException(e, ErrorMsg.GENERIC_ERROR, "in database" + desc.getDbName());
    }

    return 0;
  }

};