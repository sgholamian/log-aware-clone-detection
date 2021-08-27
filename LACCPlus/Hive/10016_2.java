//,temp,TestExecDriver.java,558,566,temp,TestExecDriver.java,500,507
//,2
public class xxx {
  @Test
  public void testMapPlan1() throws Exception {

    LOG.info("Beginning testMapPlan1");
    populateMapPlan1(db.getTable(Warehouse.DEFAULT_DATABASE_NAME, "src"));
    executePlan();
    fileDiff("lt100.txt.deflate", "mapplan1.out");
  }

};