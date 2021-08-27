//,temp,TestReplicationScenarios.java,319,328,temp,TestReplicationScenarios.java,308,317
//,3
public class xxx {
  private Tuple replDumpAllDbs(List<String> withClauseOptions) throws IOException {
    String withClause = getWithClause(withClauseOptions);
    advanceDumpDir();
    String dumpCmd = "REPL DUMP `*` " + withClause;
    run(dumpCmd, driver);
    String dumpLocation = getResult(0, 0, driver);
    String lastReplId = getResult(0, 1, true, driver);
    LOG.info("Dumped to {} with id {} for command: {}", dumpLocation, lastReplId, dumpCmd);
    return new Tuple(dumpLocation, lastReplId);
  }

};