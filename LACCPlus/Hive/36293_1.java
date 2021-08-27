//,temp,TestExecDriver.java,518,526,temp,TestExecDriver.java,509,516
//,2
public class xxx {
  @Test
  public void testMapRedPlan1() throws Exception {

    LOG.info("Beginning testMapRedPlan1");
    populateMapRedPlan1(db.getTable(Warehouse.DEFAULT_DATABASE_NAME,
        "src"));
    executePlan();
    fileDiff("kv1.val.sorted.txt", "mapredplan1.out");
  }

};