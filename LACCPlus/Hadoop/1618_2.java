//,temp,AppendOp.java,60,118,temp,CreateOp.java,118,181
//,3
public class xxx {
  @Override // Operation
  List<OperationOutput> run(FileSystem fs) {
    List<OperationOutput> out = super.run(fs);
    FSDataOutputStream os = null;
    try {
      Path fn = getCreateFile();
      Range<Long> writeSizeRange = getConfig().getWriteSize();
      long writeSize = 0;
      long blockSize = determineBlockSize();
      short replicationAmount = determineReplication();
      if (getConfig().shouldWriteUseBlockSize()) {
        writeSizeRange = getConfig().getBlockSize();
      }
      writeSize = Range.betweenPositive(getRandom(), writeSizeRange);
      long bytesWritten = 0;
      long timeTaken = 0;
      int bufSize = getBufferSize();
      boolean overWrite = false;
      DataWriter writer = new DataWriter(getRandom());
      LOG.info("Attempting to create file at " + fn + " of size "
          + Helper.toByteInfo(writeSize) + " using blocksize "
          + Helper.toByteInfo(blockSize) + " and replication amount "
          + replicationAmount);
      {
        // open & create
        long startTime = Timer.now();
        os = fs.create(fn, overWrite, bufSize, replicationAmount, blockSize);
        timeTaken += Timer.elapsed(startTime);
        // write the given length
        GenerateOutput stats = writer.writeSegment(writeSize, os);
        bytesWritten += stats.getBytesWritten();
        timeTaken += stats.getTimeTaken();
        // capture close time
        startTime = Timer.now();
        os.close();
        os = null;
        timeTaken += Timer.elapsed(startTime);
      }
      LOG.info("Created file at " + fn + " of size "
          + Helper.toByteInfo(bytesWritten) + " bytes using blocksize "
          + Helper.toByteInfo(blockSize) + " and replication amount "
          + replicationAmount + " in " + timeTaken + " milliseconds");
      // collect all the stats
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.OK_TIME_TAKEN, timeTaken));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.BYTES_WRITTEN, bytesWritten));
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.SUCCESSES, 1L));
    } catch (IOException e) {
      out.add(new OperationOutput(OutputType.LONG, getType(),
          ReportWriter.FAILURES, 1L));
      LOG.warn("Error with creating", e);
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (IOException e) {
          LOG.warn("Error closing create stream", e);
        }
      }
    }
    return out;
  }

};