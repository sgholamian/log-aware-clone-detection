//,temp,GetTablesOperation.java,102,146,temp,GetFunctionsOperation.java,86,142
//,3
public class xxx {
  @Override
  public void runInternal() throws HiveSQLException {
    setState(OperationState.RUNNING);
    LOG.info("Fetching function metadata");
    if (isAuthV2Enabled()) {
      // get databases for schema pattern
      IMetaStoreClient metastoreClient = getParentSession().getMetaStoreClient();
      String schemaPattern = convertSchemaPattern(schemaName);
      List<String> matchingDbs;
      try {
        matchingDbs = metastoreClient.getDatabases(schemaPattern);
      } catch (TException e) {
        setState(OperationState.ERROR);
        throw new HiveSQLException(e);
      }
      // authorize this call on the schema objects
      List<HivePrivilegeObject> privObjs = HivePrivilegeObjectUtils
          .getHivePrivDbObjects(matchingDbs);
      String cmdStr = "catalog : " + catalogName + ", schemaPattern : " + schemaName;
      authorizeMetaGets(HiveOperationType.GET_FUNCTIONS, privObjs, cmdStr);
    }

    try {
      if ((null == catalogName || "".equals(catalogName))
          && (null == schemaName || "".equals(schemaName))) {
        Set<String> functionNames =  FunctionRegistry
            .getFunctionNames(CLIServiceUtils.patternToRegex(functionName));
        for (String functionName : functionNames) {
          FunctionInfo functionInfo = FunctionRegistry.getFunctionInfo(functionName);
          Object rowData[] = new Object[] {
              null, // FUNCTION_CAT
              null, // FUNCTION_SCHEM
              functionInfo.getDisplayName(), // FUNCTION_NAME
              "", // REMARKS
              (functionInfo.isGenericUDTF() ?
                  DatabaseMetaData.functionReturnsTable
                  : DatabaseMetaData.functionNoTable), // FUNCTION_TYPE
             functionInfo.getClass().getCanonicalName()
          };
          rowSet.addRow(rowData);

          if (LOG.isDebugEnabled()) {
            String debugMessage = getDebugMessage("function", RESULT_SET_SCHEMA);
            LOG.debug(debugMessage, rowData);
          }
        }
      }
      if (LOG.isDebugEnabled() && rowSet.numRows() == 0) {
        LOG.debug("No function metadata has been returned");
      }
      setState(OperationState.FINISHED);
      LOG.info("Fetching function metadata has been successfully finished");
    } catch (Exception e) {
      setState(OperationState.ERROR);
      throw new HiveSQLException(e);
    }
  }

};