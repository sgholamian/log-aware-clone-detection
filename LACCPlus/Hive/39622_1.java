//,temp,MetaStoreDirectSql.java,2551,2613,temp,MetaStoreDirectSql.java,2487,2549
//,2
public class xxx {
  public List<SQLCheckConstraint> getCheckConstraints(String catName, String db_name, String tbl_name)
      throws MetaException {
    List<SQLCheckConstraint> ret = new ArrayList<SQLCheckConstraint>();
    String queryText =
        "SELECT " + DBS + ".\"NAME\", " + TBLS + ".\"TBL_NAME\","
            + "CASE WHEN " + COLUMNS_V2 + ".\"COLUMN_NAME\" IS NOT NULL THEN " + COLUMNS_V2 + ".\"COLUMN_NAME\" "
            + "ELSE " + PARTITION_KEYS + ".\"PKEY_NAME\" END, "
            + "" + KEY_CONSTRAINTS + ".\"CONSTRAINT_NAME\", " + KEY_CONSTRAINTS + ".\"ENABLE_VALIDATE_RELY\", "
            + "" + KEY_CONSTRAINTS + ".\"DEFAULT_VALUE\" "
            + " from " + TBLS + " "
            + " INNER JOIN " + KEY_CONSTRAINTS + " ON " + TBLS + ".\"TBL_ID\" = " + KEY_CONSTRAINTS + ".\"PARENT_TBL_ID\" "
            + " INNER JOIN " + DBS + " ON " + TBLS + ".\"DB_ID\" = " + DBS + ".\"DB_ID\" "
            + " LEFT OUTER JOIN " + COLUMNS_V2 + " ON " + COLUMNS_V2 + ".\"CD_ID\" = " + KEY_CONSTRAINTS + ".\"PARENT_CD_ID\" AND "
            + " " + COLUMNS_V2 + ".\"INTEGER_IDX\" = " + KEY_CONSTRAINTS + ".\"PARENT_INTEGER_IDX\" "
            + " LEFT OUTER JOIN " + PARTITION_KEYS + " ON " + TBLS + ".\"TBL_ID\" = " + PARTITION_KEYS + ".\"TBL_ID\" AND "
            + " " + PARTITION_KEYS + ".\"INTEGER_IDX\" = " + KEY_CONSTRAINTS + ".\"PARENT_INTEGER_IDX\" "
            + " WHERE " + KEY_CONSTRAINTS + ".\"CONSTRAINT_TYPE\" = "+ MConstraint.CHECK_CONSTRAINT+ " AND"
            + " " + DBS + ".\"CTLG_NAME\" = ? AND"
            + (db_name == null ? "" : " " + DBS + ".\"NAME\" = ? AND")
            + (tbl_name == null ? "" : " " + TBLS + ".\"TBL_NAME\" = ? ") ;

    queryText = queryText.trim();
    if (queryText.endsWith("AND")) {
      queryText = queryText.substring(0, queryText.length()-3);
    }
    if (LOG.isDebugEnabled()){
      LOG.debug("getCheckConstraints: directsql : " + queryText);
    }
    List<String> pms = new ArrayList<>();
    pms.add(catName);
    if (db_name != null) {
      pms.add(db_name);
    }
    if (tbl_name != null) {
      pms.add(tbl_name);
    }

    Query queryParams = pm.newQuery("javax.jdo.query.SQL", queryText);
    List<Object[]> sqlResult = MetastoreDirectSqlUtils.ensureList(executeWithArray(
        queryParams, pms.toArray(), queryText));

    if (!sqlResult.isEmpty()) {
      for (Object[] line : sqlResult) {
        int enableValidateRely = MetastoreDirectSqlUtils.extractSqlInt(line[4]);
        boolean enable = (enableValidateRely & 4) != 0;
        boolean validate = (enableValidateRely & 2) != 0;
        boolean rely = (enableValidateRely & 1) != 0;
        SQLCheckConstraint currConstraint = new SQLCheckConstraint(
            catName,
            MetastoreDirectSqlUtils.extractSqlString(line[0]),
            MetastoreDirectSqlUtils.extractSqlString(line[1]),
            MetastoreDirectSqlUtils.extractSqlString(line[2]),
            MetastoreDirectSqlUtils.extractSqlString(line[5]),
            MetastoreDirectSqlUtils.extractSqlString(line[3]),
            enable,
            validate,
            rely);
        ret.add(currConstraint);
      }
    }
    queryParams.closeAll();
    return ret;
  }

};