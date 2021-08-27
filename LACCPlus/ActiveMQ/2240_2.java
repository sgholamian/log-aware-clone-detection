//,temp,JournalCorruptionExceptionTest.java,144,171,temp,MKahaDBTxRecoveryTest.java,468,500
//,3
public class xxx {
    private void corruptTxStore(String pathToDataDir, boolean truncate) throws Exception {
        LOG.info("Path to broker datadir: " + pathToDataDir);

        RandomAccessFile randomAccessFile = new RandomAccessFile(String.format("%s/mKahaDB/txStore/db-1.log", pathToDataDir), "rw");
        final ByteSequence header = new ByteSequence(Journal.BATCH_CONTROL_RECORD_HEADER);
        byte data[] = new byte[1024 * 20];
        ByteSequence bs = new ByteSequence(data, 0, randomAccessFile.read(data, 0, data.length));

        int offset = bs.indexOf(header, 1);
        offset = bs.indexOf(header, offset+1);
        offset = bs.indexOf(header, offset+1);

        // 3rd batch
        LOG.info("3rd batch record in file: 1:" + offset);

        offset += Journal.BATCH_CONTROL_RECORD_SIZE;
        offset += 4; // location size
        offset += 1; // location type

        byte fill = (byte) 0xAF;
        LOG.info("Whacking batch record in file:" + 1 + ", at offset: " + offset + " with fill:" + fill);
        // whack that record
        byte[] bla = new byte[2];
        Arrays.fill(bla, fill);
        randomAccessFile.seek(offset);
        randomAccessFile.write(bla, 0, bla.length);

        if (truncate) {
            // set length to truncate
            randomAccessFile.setLength(randomAccessFile.getFilePointer());
        }
        randomAccessFile.getFD().sync();
    }

};