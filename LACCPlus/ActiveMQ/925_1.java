//,temp,JournalCorruptionEofIndexRecoveryTest.java,385,398,temp,JournalCorruptionEofIndexRecoveryTest.java,354,383
//,3
public class xxx {
    private void corruptBatchEndEof(int id) throws Exception{
        Collection<DataFile> files =
                ((KahaDBPersistenceAdapter) broker.getPersistenceAdapter()).getStore().getJournal().getFileMap().values();
        DataFile dataFile = (DataFile) files.toArray()[id];
        RecoverableRandomAccessFile randomAccessFile = dataFile.openRandomAccessFile();

        ArrayList<Integer> batchPositions = findBatch(randomAccessFile, Integer.MAX_VALUE);
        int pos = batchPositions.get(batchPositions.size() - 3);
        LOG.info("corrupting checksum and size (to push it past eof) of batch record at:" + id + "-" + pos);
        randomAccessFile.seek(pos + Journal.BATCH_CONTROL_RECORD_HEADER.length);
        randomAccessFile.writeInt(31 * 1024 * 1024);
        randomAccessFile.writeLong(0l);
        randomAccessFile.getChannel().force(true);
    }

};