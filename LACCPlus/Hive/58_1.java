//,temp,TestUpdateDeleteSemanticAnalyzer.java,147,156,temp,TestUpdateDeleteSemanticAnalyzer.java,95,103
//,3
public class xxx {
  @Test
  public void testUpdateAllNonPartitionedWhere() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("update T set b = 5 where b > 5",
          "testUpdateAllNonPartitionedWhere");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};