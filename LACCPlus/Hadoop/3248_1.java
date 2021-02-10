//,temp,AppendOp.java,60,118,temp,ReadOp.java,62,140
//,3
public class xxx {
  @Override // Operation
  List<OperationOutput> run(FileSystem fs) {
    List<OperationOutput> out = super.run(fs);
    OutputStream os = null;
    try {
      Path fn = getAppendFile();
      // determine file status for file length requirement
      // to know if should fill in partial bytes
      Range<Long> appendSizeRange = getConfig().getAppendSize();
      if (getConfig().shouldAppendUseBlockSize()) {
        appendSizeRange = getConfig().getBlockSize();
      }
      long appendSize = Range.betweenPositive(getRandom(), appendSizeRange);
      long timeTaken = 0, bytesAppended = 0;
      DataWriter writer = new DataWriter(getRandom());
      LOG.info("Attempting to append to file at " + fn + " of size "
          + Helper.toByteInfo(appendSize));
      {
        // open
        long startTime = Timer.now();
        os = fs.append(fn);
        timeTaken += Timer.elapsed(startTime);
        // append given length
        GenerateOutput stats = writer.writeSegment(appendSize, os);
        timeTaken += stats.getTimeTaken();
        bytesAppended += stats.getBytesWritten();
        // capture close time
        startTime = Timer.now();
        os.close();
        os = null;
        timeTaken += Timer.elapsed(startTime);
      }
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.BYTES_WRITTEN, bytesAppended));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.OK_TIME_TAKEN, timeTaken));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.SUCCESSES, 1L));
      LOG.info("Appended " + Helper.toByteInfo(bytesAppended) + " to file "
          + fn + " in " + timeTaken + " milliseconds");
    } catch (FileNotFoundException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.NOT_FOUND, 1L));
      LOG.warn("Error with appending", e);
    } catch (IOException | UnsupportedOperationException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error with appending", e);
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (IOException e) {
          LOG.warn("Error with closing append stream", e);
        }
      }
    }
    return out;
  }

};