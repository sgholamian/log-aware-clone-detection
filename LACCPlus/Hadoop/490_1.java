//,temp,TestBookKeeperJournalManager.java,471,533,temp,TestBookKeeperJournalManager.java,383,464
//,3
public class xxx {
  @Test
  public void testOneBookieFailure() throws Exception {
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
          BKJMUtil.createJournalURI("/hdfsjournal-onebookiefailure"),
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

      replacementBookie = bkutil.newBookie();
      assertEquals("replacement bookie didn't start",
                   ensembleSize+1, bkutil.checkBookiesUp(ensembleSize+1, 10));
      newBookie.shutdown();
      assertEquals("New bookie didn't die",
                   ensembleSize, bkutil.checkBookiesUp(ensembleSize, 10));

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