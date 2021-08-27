//,temp,TestUpdateDeleteSemanticAnalyzer.java,214,226,temp,TestUpdateDeleteSemanticAnalyzer.java,168,177
//,3
public class xxx {
  @Test
  public void testInsertValuesPartitioned() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("insert into table U partition (ds) values " +
              "('abc', 3, 'today'), ('ghi', 5, 'tomorrow')",
          "testInsertValuesPartitioned");

      LOG.info(explain((SemanticAnalyzer) rc.sem, rc.plan));

    } finally {
      cleanupTables();
    }
  }

};