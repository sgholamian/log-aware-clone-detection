//,temp,TestUpdateDeleteSemanticAnalyzer.java,179,188,temp,TestUpdateDeleteSemanticAnalyzer.java,158,166
//,3
public class xxx {
  @Test
  public void testUpdateAllPartitioned() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("update U set b = 5", "testUpdateAllPartitioned");
      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));
    } finally {
      cleanupTables();
    }
  }

};