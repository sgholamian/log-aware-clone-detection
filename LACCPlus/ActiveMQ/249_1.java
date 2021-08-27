//,temp,Journal.java,408,420,temp,Journal.java,390,406
//,3
public class xxx {
    private void doPreallocationZeros(RecoverableRandomAccessFile file) {
        preAllocateDirectBuffer.rewind();
        try {
            FileChannel channel = file.getChannel();
            channel.write(preAllocateDirectBuffer);
            channel.force(false);
            channel.position(0);
        } catch (ClosedByInterruptException ignored) {
            LOG.trace("Could not preallocate journal file with zeros", ignored);
        } catch (IOException e) {
            LOG.error("Could not preallocate journal file with zeros", e);
        }
    }

};