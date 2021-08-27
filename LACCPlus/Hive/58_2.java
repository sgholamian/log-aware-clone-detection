//,temp,TestUpdateDeleteSemanticAnalyzer.java,147,156,temp,TestUpdateDeleteSemanticAnalyzer.java,95,103
//,3
public class xxx {
  @Test
  public void testDeleteAllPartitioned() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("delete from U", "testDeleteAllPartitioned");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};