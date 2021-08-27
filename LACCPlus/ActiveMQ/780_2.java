//,temp,MissingDataFileTest.java,89,108,temp,VerifySteadyEnqueueRate.java,127,152
//,3
public class xxx {
    private void startBroker() throws Exception {
        broker = new BrokerService();
        //broker.setDeleteAllMessagesOnStartup(true);
        broker.setPersistent(true);
        broker.setUseJmx(true);

        KahaDBStore kaha = new KahaDBStore();
        kaha.setDirectory(new File("target/activemq-data/kahadb"));
        // The setEnableJournalDiskSyncs(false) setting is a little dangerous right now, as I have not verified
        // what happens if the index is updated but a journal update is lost.
        // Index is going to be in consistent, but can it be repaired?
        kaha.setEnableJournalDiskSyncs(false);
        // Using a bigger journal file size makes he take fewer spikes as it is not switching files as often.
        kaha.setJournalMaxFileLength(1024*1024*100);

        // small batch means more frequent and smaller writes
        kaha.setIndexWriteBatchSize(100);
        // do the index write in a separate thread
        kaha.setEnableIndexWriteAsync(true);

        broker.setPersistenceAdapter(kaha);

        broker.addConnector("tcp://localhost:0").setName("Default");
        broker.start();
        LOG.info("Starting broker..");
    }

};