//,temp,SleepOp.java,54,73,temp,MkdirOp.java,59,93
//,3
public class xxx {
  List<OperationOutput> run(Range<Long> sleepTime) {
    List<OperationOutput> out = super.run(null);
    try {
      if (sleepTime != null) {
        long sleepMs = getSleepTime(sleepTime);
        long startTime = Timer.now();
        sleep(sleepMs);
        long elapsedTime = Timer.elapsed(startTime);
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.OK_TIME_TAKEN, elapsedTime));
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.SUCCESSES, 1L));
      }
    } catch (InterruptedException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error with sleeping", e);
    }
    return out;
  }

};