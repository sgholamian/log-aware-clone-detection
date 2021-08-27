//,temp,TestExecDriver.java,518,526,temp,TestExecDriver.java,509,516
//,2
public class xxx {
  @Test
  public void testMapPlan2() throws Exception {

    LOG.info("Beginning testMapPlan2");
    populateMapPlan2(db.getTable(Warehouse.DEFAULT_DATABASE_NAME, "src"));
    executePlan();
    fileDiff("lt100.txt", "mapplan2.out");
  }

};