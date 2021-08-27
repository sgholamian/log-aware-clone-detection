//,temp,TestExecDriver.java,538,546,temp,TestExecDriver.java,528,536
//,3
public class xxx {
  @Test
  public void testMapRedPlan2() throws Exception {

    LOG.info("Beginning testMapPlan2");
    populateMapRedPlan2(db.getTable(Warehouse.DEFAULT_DATABASE_NAME,
        "src"));
    executePlan();
    fileDiff("lt100.sorted.txt", "mapredplan2.out");
  }

};