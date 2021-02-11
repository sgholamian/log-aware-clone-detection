//,temp,RenameOp.java,80,118,temp,MkdirOp.java,59,93
//,3
public class xxx {
  @Override // Operation
  List<OperationOutput> run(FileSystem fs) {
    List<OperationOutput> out = super.run(fs);
    try {
      // find the files to modify
      SrcTarget targets = getRenames();
      Path src = targets.getSrc();
      Path target = targets.getTarget();
      // capture results
      boolean renamedOk = false;
      long timeTaken = 0;
      {
        // rename it
        long startTime = Timer.now();
        renamedOk = fs.rename(src, target);
        timeTaken = Timer.elapsed(startTime);
      }
      if (renamedOk) {
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.OK_TIME_TAKEN, timeTaken));
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.SUCCESSES, 1L));
        LOG.info("Renamed " + src + " to " + target);
      } else {
        out.add(new OperationOutput(OutputType.LONG, getType(),
            ReportWriter.FAILURES, 1L));
        LOG.warn("Could not rename " + src + " to " + target);
      }
    } catch (FileNotFoundException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.NOT_FOUND, 1L));
      LOG.warn("Error with renaming", e);
    } catch (IOException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error with renaming", e);
    }
    return out;
  }

};