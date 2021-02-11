//,temp,TestBookKeeperJournalManager.java,471,533,temp,TestBookKeeperJournalManager.java,383,464
//,3
public class xxx {
  @Test
  public void testAllBookieFailure() throws Exception {
    // bookie to fail
    newBookie = bkutil.newBookie();
    BookieServer replacementBookie = null;

    try {
      int ensembleSize = numBookies + 1;
      assertEquals("New bookie didn't start",
                   ensembleSize, bkutil.checkBookiesUp(ensembleSize, 10));

      // ensure that the journal manager has to use all bookies,
      // so that a failure will fail the journal manager
      Configuration conf = new Configuration();
      conf.setInt(BookKeeperJournalManager.BKJM_BOOKKEEPER_ENSEMBLE_SIZE,
                  ensembleSize);
      conf.setInt(BookKeeperJournalManager.BKJM_BOOKKEEPER_QUORUM_SIZE,
                  ensembleSize);
      long txid = 1;
      NamespaceInfo nsi = newNSInfo();
      BookKeeperJournalManager bkjm = new BookKeeperJournalManager(conf,
          BKJMUtil.createJournalURI("/hdfsjournal-allbookiefailure"),
          nsi);
      bkjm.format(nsi);
      EditLogOutputStream out = bkjm.startLogSegment(txid,
        NameNodeLayoutVersion.CURRENT_LAYOUT_VERSION);

      for (long i = 1 ; i <= 3; i++) {
        FSEditLogOp op = FSEditLogTestUtil.getNoOpInstance();
        op.setTransactionId(txid++);
        out.write(op);
      }
      out.setReadyToFlush();
      out.flush();
      newBookie.shutdown();
      assertEquals("New bookie didn't die",
                   numBookies, bkutil.checkBookiesUp(numBookies, 10));

      try {
        for (long i = 1 ; i <= 3; i++) {
          FSEditLogOp op = FSEditLogTestUtil.getNoOpInstance();
          op.setTransactionId(txid++);
          out.write(op);
        }
        out.setReadyToFlush();
        out.flush();
        fail("should not get to this stage");
      } catch (IOException ioe) {
        LOG.debug("Error writing to bookkeeper", ioe);
        assertTrue("Invalid exception message",
                   ioe.getMessage().contains("Failed to write to bookkeeper"));
      }
      replacementBookie = bkutil.newBookie();

      assertEquals("New bookie didn't start",
                   numBookies+1, bkutil.checkBookiesUp(numBookies+1, 10));
      bkjm.recoverUnfinalizedSegments();
      out = bkjm.startLogSegment(txid,
        NameNodeLayoutVersion.CURRENT_LAYOUT_VERSION);
      for (long i = 1 ; i <= 3; i++) {
        FSEditLogOp op = FSEditLogTestUtil.getNoOpInstance();
        op.setTransactionId(txid++);
        out.write(op);
      }

      out.setReadyToFlush();
      out.flush();

    } catch (Exception e) {
      LOG.error("Exception in test", e);
      throw e;
    } finally {
      if (replacementBookie != null) {
        replacementBookie.shutdown();
      }
      newBookie.shutdown();

      if (bkutil.checkBookiesUp(numBookies, 30) != numBookies) {
        LOG.warn("Not all bookies from this test shut down, expect errors");
      }
    }
  }

};