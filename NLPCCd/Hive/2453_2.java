//,temp,sample_2512.java,2,7,temp,sample_2515.java,2,7
//,3
public class xxx {
public OperationHandle getCrossReference(SessionHandle sessionHandle, String primaryCatalog, String primarySchema, String primaryTable, String foreignCatalog, String foreignSchema, String foreignTable) throws HiveSQLException {
OperationHandle opHandle = sessionManager.getSession(sessionHandle) .getCrossReference(primaryCatalog, primarySchema, primaryTable, foreignCatalog, foreignSchema, foreignTable);


log.info("getcrossreference");
}

};