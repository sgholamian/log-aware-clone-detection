//,temp,JournalMaxFileLengthChangeTest.java,88,106,temp,JournalMaxFileLengthChangeTest.java,64,82
//,2
public class xxx {
    @Test
    public void testMaxFileLengthGrow() throws Exception {
        MessageStore messageStore = createStore(8 * ONE_MB);
        addMessages(messageStore, 4);

        long sizeBeforeChange = store.getJournal().getDiskSize();
        LOG.info("Journal size before: " + sizeBeforeChange);

        store.stop();
        messageStore = createStore(6 * ONE_MB);
        verifyMessages(messageStore, 4);

        long sizeAfterChange = store.getJournal().getDiskSize();
        LOG.info("Journal size after: " + sizeAfterChange);

        //verify the size is the same - will be slightly different as checkpoint journal
        //commands are written but should be close
        assertEquals(sizeBeforeChange, sizeAfterChange, 4096);
    }

};