//,temp,TestExecDriver.java,558,566,temp,TestExecDriver.java,500,507
//,2
public class xxx {
  @Test
  public void testMapRedPlan5() throws Exception {

    LOG.info("Beginning testMapPlan5");
    populateMapRedPlan5(db.getTable(Warehouse.DEFAULT_DATABASE_NAME,
        "src"));
    executePlan();
    fileDiff("kv1.string-sorted.txt", "mapredplan5.out");
  }

};