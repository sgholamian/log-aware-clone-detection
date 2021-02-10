//,temp,ChunkUtils.java,154,203,temp,ChunkUtils.java,75,141
//,3
public class xxx {
  public static ByteBuffer readData(File chunkFile, ChunkInfo data,
                                    VolumeIOStats volumeIOStats)
      throws
      StorageContainerException, ExecutionException, InterruptedException,
      NoSuchAlgorithmException {
    Logger log = LoggerFactory.getLogger(ChunkManagerImpl.class);

    if (!chunkFile.exists()) {
      log.error("Unable to find the chunk file. chunk info : {}",
          data.toString());
      throw new StorageContainerException("Unable to find the chunk file. " +
          "chunk info " +
          data.toString(), UNABLE_TO_FIND_CHUNK);
    }

    AsynchronousFileChannel file = null;
    FileLock lock = null;
    try {
      long readStartTime = Time.monotonicNow();
      file =
          AsynchronousFileChannel.open(chunkFile.toPath(),
              StandardOpenOption.READ);
      lock = file.lock(data.getOffset(), data.getLen(), true).get();

      ByteBuffer buf = ByteBuffer.allocate((int) data.getLen());
      file.read(buf, data.getOffset()).get();

      // Increment volumeIO stats here.
      volumeIOStats.incReadTime(Time.monotonicNow() - readStartTime);
      volumeIOStats.incReadOpCount();
      volumeIOStats.incReadBytes(data.getLen());
      if (data.getChecksum() != null && !data.getChecksum().isEmpty()) {
        verifyChecksum(data, buf.array(), log);
      }
      return buf;
    } catch (IOException e) {
      throw new StorageContainerException(e, IO_EXCEPTION);
    } finally {
      if (lock != null) {
        try {
          lock.release();
        } catch (IOException e) {
          log.error("I/O error is lock release.");
        }
      }
      if (file != null) {
        IOUtils.closeStream(file);
      }
    }
  }

};