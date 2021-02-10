//,temp,ChunkUtils.java,154,203,temp,ChunkUtils.java,75,141
//,3
public class xxx {
  public static void writeData(File chunkFile, ChunkInfo chunkInfo,
                               byte[] data, VolumeIOStats volumeIOStats) throws
      StorageContainerException, ExecutionException, InterruptedException,
      NoSuchAlgorithmException {

    Logger log = LoggerFactory.getLogger(ChunkManagerImpl.class);
    if (data.length != chunkInfo.getLen()) {
      String err = String.format("data array does not match the length " +
              "specified. DataLen: %d Byte Array: %d",
          chunkInfo.getLen(), data.length);
      log.error(err);
      throw new StorageContainerException(err, INVALID_WRITE_SIZE);
    }

    AsynchronousFileChannel file = null;
    FileLock lock = null;

    try {
      if (chunkInfo.getChecksum() != null &&
          !chunkInfo.getChecksum().isEmpty()) {
        verifyChecksum(chunkInfo, data, log);
      }

      long writeTimeStart = Time.monotonicNow();
      file =
          AsynchronousFileChannel.open(chunkFile.toPath(),
              StandardOpenOption.CREATE,
              StandardOpenOption.WRITE,
              StandardOpenOption.SPARSE,
              StandardOpenOption.SYNC);
      lock = file.lock().get();
      int size = file.write(ByteBuffer.wrap(data), chunkInfo.getOffset()).get();
      // Increment volumeIO stats here.
      volumeIOStats.incWriteTime(Time.monotonicNow() - writeTimeStart);
      volumeIOStats.incWriteOpCount();
      volumeIOStats.incWriteBytes(size);
      if (size != data.length) {
        log.error("Invalid write size found. Size:{}  Expected: {} ", size,
            data.length);
        throw new StorageContainerException("Invalid write size found. " +
            "Size: " + size + " Expected: " + data.length, INVALID_WRITE_SIZE);
      }
    } catch (StorageContainerException ex) {
      throw ex;
    } catch(IOException e) {
      throw new StorageContainerException(e, IO_EXCEPTION);

    } finally {
      if (lock != null) {
        try {
          lock.release();
        } catch (IOException e) {
          log.error("Unable to release lock ??, Fatal Error.");
          throw new StorageContainerException(e, CONTAINER_INTERNAL_ERROR);

        }
      }
      if (file != null) {
        try {
          file.close();
        } catch (IOException e) {
          throw new StorageContainerException("Error closing chunk file",
              e, CONTAINER_INTERNAL_ERROR);
        }
      }
    }
  }

};