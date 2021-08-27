//,temp,JournalCorruptionEofIndexRecoveryTest.java,385,398,temp,JournalCorruptionEofIndexRecoveryTest.java,354,383
//,3
public class xxx {
    private void corruptBatchCheckSumSplash(int id) throws Exception{
        Collection<DataFile> files =
                ((KahaDBPersistenceAdapter) broker.getPersistenceAdapter()).getStore().getJournal().getFileMap().values();
        DataFile dataFile = (DataFile) files.toArray()[0];
        RecoverableRandomAccessFile randomAccessFile = dataFile.openRandomAccessFile();

        ArrayList<Integer> batchPositions = findBatch(randomAccessFile, Integer.MAX_VALUE);
        LOG.info("Batch positions: " + batchPositions);
        int pos = batchPositions.get(1);
        LOG.info("corrupting checksum and size (to push it past eof) of batch record at:" + id + "-" + pos);
        randomAccessFile.seek(pos + Journal.BATCH_CONTROL_RECORD_HEADER.length + 4);
        // whack the batch control record checksum
        randomAccessFile.writeLong(0l);

        // mod the data size in the location header so reading blows
        randomAccessFile.seek(pos + Journal.BATCH_CONTROL_RECORD_SIZE);
        int size = randomAccessFile.readInt();
        byte type = randomAccessFile.readByte();

        LOG.info("Read: size:" + size + ", type:" + type);

        randomAccessFile.seek(pos + Journal.BATCH_CONTROL_RECORD_SIZE);
        size -= 1;
        LOG.info("rewrite incorrect location size @:" + (pos + Journal.BATCH_CONTROL_RECORD_SIZE) + " as: " + size);
        randomAccessFile.writeInt(size);
        corruptOrderIndex(id, size);

        randomAccessFile.getChannel().force(true);
        dataFile.closeRandomAccessFile(randomAccessFile);
    }

};