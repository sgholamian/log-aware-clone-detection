//,temp,TestEditLog.java,1417,1461,temp,TestEditLog.java,1376,1412
//,3
public class xxx {
  @Test
  public void testEditLogFailOverFromCorrupt() throws IOException {
    File f1 = new File(TEST_DIR + "/failover0");
    File f2 = new File(TEST_DIR + "/failover1");
    List<URI> editUris = ImmutableList.of(f1.toURI(), f2.toURI());

    NNStorage storage = setupEdits(editUris, 3);
    
    final long startErrorTxId = 1*TXNS_PER_ROLL + 1;
    final long endErrorTxId = 2*TXNS_PER_ROLL;

    File[] files = new File(f1, "current").listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
          if (name.startsWith(NNStorage.getFinalizedEditsFileName(startErrorTxId, 
                                  endErrorTxId))) {
            return true;
          }
          return false;
        }
      });
    assertEquals(1, files.length);

    long fileLen = files[0].length();
    LOG.debug("Corrupting Log File: " + files[0] + " len: " + fileLen);
    RandomAccessFile rwf = new RandomAccessFile(files[0], "rw");
    rwf.seek(fileLen-4); // seek to checksum bytes
    int b = rwf.readInt();
    rwf.seek(fileLen-4);
    rwf.writeInt(b+1);
    rwf.close();
    
    FSEditLog editlog = getFSEditLog(storage);
    editlog.initJournalsForWrite();
    long startTxId = 1;
    Collection<EditLogInputStream> streams = null;
    try {
      streams = editlog.selectInputStreams(startTxId, 4*TXNS_PER_ROLL);
      readAllEdits(streams, startTxId);
    } catch (IOException e) {
      LOG.error("edit log failover didn't work", e);
      fail("Edit log failover didn't work");
    } finally {
      IOUtils.cleanup(null, streams.toArray(new EditLogInputStream[0]));
    }
  }

};