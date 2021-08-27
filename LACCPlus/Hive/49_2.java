//,temp,TestUpdateDeleteSemanticAnalyzer.java,137,145,temp,TestUpdateDeleteSemanticAnalyzer.java,63,73
//,3
public class xxx {
  @Test
  public void testInsertSelect() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("insert into table T select a, b from U", "testInsertSelect");

      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));

    } finally {
      cleanupTables();
    }
  }

};