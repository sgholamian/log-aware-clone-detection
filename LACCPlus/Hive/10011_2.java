//,temp,TestExecDriver.java,568,576,temp,TestExecDriver.java,548,556
//,2
public class xxx {
  @Test
  public void testMapRedPlan4() throws Exception {

    LOG.info("Beginning testMapPlan4");
    populateMapRedPlan4(db.getTable(Warehouse.DEFAULT_DATABASE_NAME,
        "src"));
    executePlan();
    fileDiff("kv1.string-sorted.txt", "mapredplan4.out");
  }

};