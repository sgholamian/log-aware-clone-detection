//,temp,Journal.java,408,420,temp,Journal.java,390,406
//,3
public class xxx {
    private void doPreallocationSparseFile(RecoverableRandomAccessFile file) {
        final ByteBuffer journalEof = ByteBuffer.wrap(EOF_RECORD);
        try {
            FileChannel channel = file.getChannel();
            channel.position(0);
            channel.write(journalEof);
            channel.position(maxFileLength - 5);
            journalEof.rewind();
            channel.write(journalEof);
            channel.force(false);
            channel.position(0);
        } catch (ClosedByInterruptException ignored) {
            LOG.trace("Could not preallocate journal file with sparse file", ignored);
        } catch (IOException e) {
            LOG.error("Could not preallocate journal file with sparse file", e);
        }
    }

};