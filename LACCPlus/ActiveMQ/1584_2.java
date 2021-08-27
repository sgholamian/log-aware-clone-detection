//,temp,Journal.java,434,447,temp,Journal.java,422,432
//,3
public class xxx {
    private void doPreallocationKernelCopy(RecoverableRandomAccessFile file) {
        try (RandomAccessFile templateRaf = new RandomAccessFile(osKernelCopyTemplateFile, "rw");){
            templateRaf.getChannel().transferTo(0, getMaxFileLength(), file.getChannel());
        } catch (ClosedByInterruptException ignored) {
            LOG.trace("Could not preallocate journal file with kernel copy", ignored);
        } catch (FileNotFoundException e) {
            LOG.error("Could not find the template file on disk at " + osKernelCopyTemplateFile.getAbsolutePath(), e);
        } catch (IOException e) {
            LOG.error("Could not transfer the template file to journal, transferFile=" + osKernelCopyTemplateFile.getAbsolutePath(), e);
        }
    }

};