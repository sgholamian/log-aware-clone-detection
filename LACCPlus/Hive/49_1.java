//,temp,TestUpdateDeleteSemanticAnalyzer.java,137,145,temp,TestUpdateDeleteSemanticAnalyzer.java,63,73
//,3
public class xxx {
  @Test
  public void testUpdateAllNonPartitioned() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("update T set b = 5", "testUpdateAllNonPartitioned");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};