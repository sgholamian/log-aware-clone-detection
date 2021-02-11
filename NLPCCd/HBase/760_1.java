//,temp,sample_3390.java,2,16,temp,sample_4879.java,2,15
//,3
public class xxx {
public void dummy_method(){
RegionInfo[] regions = MasterProcedureTestingUtility.createTable( procExec, tableName, null, "f");
UTIL.getAdmin().disableTable(tableName);
long procId1 = procExec.submitProcedure( new DeleteTableProcedure(procExec.getEnvironment(), tableName));
long procId2 = procExec.submitProcedure( new DeleteTableProcedure(procExec.getEnvironment(), tableName));
ProcedureTestingUtility.waitProcedure(procExec, procId1);
ProcedureTestingUtility.waitProcedure(procExec, procId2);
ProcedureTestingUtility.assertProcNotFailed(procExec, procId1);
MasterProcedureTestingUtility.validateTableDeletion(getMaster(), tableName);
Procedure<?> result = procExec.getResult(procId2);
assertTrue(result.isFailed());


log.info("delete failed with exception");
}

};