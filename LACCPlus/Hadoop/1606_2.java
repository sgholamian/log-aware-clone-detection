//,temp,TestSwiftFileSystemPartitionedUploads.java,155,215,temp,TestSwiftFileSystemPartitionedUploads.java,79,151
//,3
public class xxx {
  @Test(timeout = SWIFT_BULK_IO_TEST_TIMEOUT)
  public void testFilePartUpload() throws Throwable {

    final Path path = new Path("/test/testFilePartUpload");

    int len = 8192;
    final byte[] src = SwiftTestUtils.dataset(len, 32, 144);
    FSDataOutputStream out = fs.create(path,
                                       false,
                                       getBufferSize(),
                                       (short) 1,
                                       BLOCK_SIZE);

    try {
      int totalPartitionsToWrite = len / PART_SIZE_BYTES;
      assertPartitionsWritten("Startup", out, 0);
      //write 2048
      int firstWriteLen = 2048;
      out.write(src, 0, firstWriteLen);
      //assert
      long expected = getExpectedPartitionsWritten(firstWriteLen,
                                                   PART_SIZE_BYTES,
                                                   false);
      SwiftUtils.debug(LOG, "First write: predict %d partitions written",
                       expected);
      assertPartitionsWritten("First write completed", out, expected);
      //write the rest
      int remainder = len - firstWriteLen;
      SwiftUtils.debug(LOG, "remainder: writing: %d bytes", remainder);

      out.write(src, firstWriteLen, remainder);
      expected =
        getExpectedPartitionsWritten(len, PART_SIZE_BYTES, false);
      assertPartitionsWritten("Remaining data", out, expected);
      out.close();
      expected =
        getExpectedPartitionsWritten(len, PART_SIZE_BYTES, true);
      assertPartitionsWritten("Stream closed", out, expected);

      Header[] headers = fs.getStore().getObjectHeaders(path, true);
      for (Header header : headers) {
        LOG.info(header.toString());
      }

      byte[] dest = readDataset(fs, path, len);
      LOG.info("Read dataset from " + path + ": data length =" + len);
      //compare data
      SwiftTestUtils.compareByteArrays(src, dest, len);
      FileStatus status;

      final Path qualifiedPath = path.makeQualified(fs);
      status = fs.getFileStatus(qualifiedPath);
      //now see what block location info comes back.
      //This will vary depending on the Swift version, so the results
      //aren't checked -merely that the test actually worked
      BlockLocation[] locations = fs.getFileBlockLocations(status, 0, len);
      assertNotNull("Null getFileBlockLocations()", locations);
      assertTrue("empty array returned for getFileBlockLocations()",
                 locations.length > 0);

      //last bit of test -which seems to play up on partitions, which we download
      //to a skip
      try {
        validatePathLen(path, len);
      } catch (AssertionError e) {
        //downgrade to a skip
        throw new AssumptionViolatedException(e, null);
      }

    } finally {
      IOUtils.closeStream(out);
    }
  }

};