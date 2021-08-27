//,temp,TestUpdateDeleteSemanticAnalyzer.java,126,135,temp,TestUpdateDeleteSemanticAnalyzer.java,85,93
//,3
public class xxx {
  @Test
  public void testDeleteWhereNoPartition() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("delete from T where a > 5", "testDeleteWhereNoPartition");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};