//,temp,TestUpdateDeleteSemanticAnalyzer.java,115,124,temp,TestUpdateDeleteSemanticAnalyzer.java,75,83
//,3
public class xxx {
  @Test
  public void testDeleteAllNonPartitioned() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("delete from T", "testDeleteAllNonPartitioned");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};