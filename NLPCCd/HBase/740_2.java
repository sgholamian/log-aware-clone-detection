//,temp,sample_4904.java,2,15,temp,sample_4445.java,2,17
//,3
public class xxx {
public void dummy_method(){
ProcedureTestingUtility.waitProcedure(procExec, procId2);
Procedure<?> result = procExec.getResult(procId2);
assertTrue(result.isFailed());
assertTrue( ProcedureTestingUtility.getExceptionCause(result) instanceof TableNotEnabledException);
try {
final ProcedurePrepareLatch prepareLatch = new ProcedurePrepareLatch.CompatibilityLatch();
long procId3 = procExec.submitProcedure(new DisableTableProcedure( procExec.getEnvironment(), tableName, false, prepareLatch));
prepareLatch.await();
Assert.fail("Disable should throw exception through latch.");
} catch (TableNotEnabledException tnee) {


log.info("disable failed with expected exception");
}
}

};