//,temp,TestExecDriver.java,568,576,temp,TestExecDriver.java,548,556
//,2
public class xxx {
  @Test
  public void testMapRedPlan6() throws Exception {

    LOG.info("Beginning testMapPlan6");
    populateMapRedPlan6(db.getTable(Warehouse.DEFAULT_DATABASE_NAME,
        "src"));
    executePlan();
    fileDiff("lt100.sorted.txt", "mapredplan6.out");
  }

};