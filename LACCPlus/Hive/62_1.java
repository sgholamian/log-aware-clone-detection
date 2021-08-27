//,temp,TestUpdateDeleteSemanticAnalyzer.java,201,212,temp,TestUpdateDeleteSemanticAnalyzer.java,190,199
//,3
public class xxx {
  @Test
  public void testInsertValues() throws Exception {
    try {
      ReturnInfo rc = parseAndAnalyze("insert into table T values ('abc', 3), ('ghi', null)",
          "testInsertValues");

      LOG.info(explain((SemanticAnalyzer)rc.sem, rc.plan));

    } finally {
      cleanupTables();
    }
  }

};