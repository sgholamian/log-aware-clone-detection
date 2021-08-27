//,temp,sample_2482.java,2,15,temp,sample_2481.java,2,14
//,3
public class xxx {
private void executePlan() throws Exception {
String testName = new Exception().getStackTrace()[1].getMethodName();
MapRedTask mrtask = new MapRedTask();
DriverContext dctx = new DriverContext ();
mrtask.setWork(mr);
mrtask.initialize(queryState, null, dctx, null);
int exitVal =  mrtask.execute(dctx);
if (exitVal != 0) {
assertEquals(true, false);
}


log.info("execution completed successfully");
}

};