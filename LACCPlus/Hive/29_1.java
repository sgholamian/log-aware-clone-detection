//,temp,TestExecDriver.java,538,546,temp,TestExecDriver.java,528,536
//,3
public class xxx {
  @Test
  public void testMapRedPlan3() throws Exception {

    LOG.info("Beginning testMapPlan3");
    populateMapRedPlan3(db.getTable(Warehouse.DEFAULT_DATABASE_NAME,
        "src"), db.getTable(Warehouse.DEFAULT_DATABASE_NAME, "src2"));
    executePlan();
    fileDiff("kv1kv2.cogroup.txt", "mapredplan3.out");
  }

};