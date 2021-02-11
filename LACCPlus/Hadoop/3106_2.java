//,temp,BlockBlobAppendStream.java,783,820,temp,BlockBlobAppendStream.java,716,758
//,3
public class xxx {
  private void writeBlockRequestInternal(String blockId,
                                         ByteBuffer dataPayload,
                                         boolean bufferPoolBuffer) {
    IOException lastLocalException = null;

    int uploadRetryAttempts = 0;
    while (uploadRetryAttempts < MAX_BLOCK_UPLOAD_RETRIES) {
      try {
        long startTime = System.nanoTime();

        blob.uploadBlock(blockId, accessCondition, new ByteArrayInputStream(
            dataPayload.array()), dataPayload.position(),
            new BlobRequestOptions(), opContext);

        LOG.debug("upload block finished for {} ms. block {} ",
            TimeUnit.NANOSECONDS.toMillis(
                System.nanoTime() - startTime), blockId);
        break;

      } catch(Exception ioe) {
        LOG.debug("Encountered exception during uploading block for Blob {}"
            + " Exception : {}", key, ioe);
        uploadRetryAttempts++;
        lastLocalException = new AzureException(
            "Encountered Exception while uploading block: " + ioe, ioe);
        try {
          Thread.sleep(
              BLOCK_UPLOAD_RETRY_INTERVAL * (uploadRetryAttempts + 1));
        } catch(InterruptedException ie) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }

    if (bufferPoolBuffer) {
      poolReadyByteBuffers.putBuffer(dataPayload);
    }

    if (uploadRetryAttempts == MAX_BLOCK_UPLOAD_RETRIES) {
      maybeSetFirstError(lastLocalException);
    }
  }

};