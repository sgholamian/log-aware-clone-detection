//,temp,TruncateOp.java,59,103,temp,MkdirOp.java,59,93
//,3
public class xxx {
  @Override // Operation
  List<OperationOutput> run(FileSystem fs) {
    List<OperationOutput> out = super.run(fs);
    try {
      Path dir = getDirectory();
      boolean mkRes = false;
      long timeTaken = 0;
      {
        long startTime = Timer.now();
        mkRes = fs.mkdirs(dir);
        timeTaken = Timer.elapsed(startTime);
      }
      // log stats
      if (mkRes) {
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.OK_TIME_TAKEN, timeTaken));
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.SUCCESSES, 1L));
        LOG.info("Made directory " + dir);
      } else {
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.FAILURES, 1L));
        LOG.warn("Could not make " + dir);
      }
    } catch (FileNotFoundException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.NOT_FOUND, 1L));
      LOG.warn("Error with mkdir", e);
    } catch (IOException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error with mkdir", e);
    }
    return out;
  }

};