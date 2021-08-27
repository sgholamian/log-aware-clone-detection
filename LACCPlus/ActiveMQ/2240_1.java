//,temp,JournalCorruptionExceptionTest.java,144,171,temp,MKahaDBTxRecoveryTest.java,468,500
//,3
public class xxx {
    private void corruptLocationAtDataFileIndex(int id) throws IOException {

        Collection<DataFile> files = ((KahaDBPersistenceAdapter) broker.getPersistenceAdapter()).getStore().getJournal().getFileMap().values();
        DataFile dataFile = (DataFile) files.toArray()[id];

        RecoverableRandomAccessFile randomAccessFile = dataFile.openRandomAccessFile();

        final ByteSequence header = new ByteSequence(Journal.BATCH_CONTROL_RECORD_HEADER);
        byte data[] = new byte[1024 * 20];
        ByteSequence bs = new ByteSequence(data, 0, randomAccessFile.read(data, 0, data.length));

        int offset = bs.indexOf(header, 0);

        offset += Journal.BATCH_CONTROL_RECORD_SIZE;

        if (fillLength >= 10) {
            offset += 4; // location size
            offset += 1; // location type
        }

        LOG.info("Whacking batch record in file:" + id + ", at offset: " + offset + " with fill:" + fill);
        // whack that record
        byte[] bla = new byte[fillLength];
        Arrays.fill(bla, fill);
        randomAccessFile.seek(offset);
        randomAccessFile.write(bla, 0, bla.length);
        randomAccessFile.getFD().sync();
    }

};