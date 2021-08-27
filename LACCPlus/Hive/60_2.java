//,temp,TestUpdateDeleteSemanticAnalyzer.java,214,226,temp,TestUpdateDeleteSemanticAnalyzer.java,168,177
//,3
public class xxx {
  @Test
  public void testUpdateAllPartitionedWhere() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("update U set b = 5 where b > 5",
          "testUpdateAllPartitionedWhere");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};