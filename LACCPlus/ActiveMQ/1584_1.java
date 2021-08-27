//,temp,Journal.java,434,447,temp,Journal.java,422,432
//,3
public class xxx {
    private File createJournalTemplateFile() {
        String fileName = "db-log.template";
        File rc = new File(directory, fileName);
        try (RandomAccessFile templateRaf = new RandomAccessFile(rc, "rw");) {
            templateRaf.getChannel().write(ByteBuffer.wrap(EOF_RECORD));
            templateRaf.setLength(maxFileLength);
            templateRaf.getChannel().force(true);
        } catch (FileNotFoundException e) {
            LOG.error("Could not find the template file on disk at " + osKernelCopyTemplateFile.getAbsolutePath(), e);
        } catch (IOException e) {
            LOG.error("Could not transfer the template file to journal, transferFile=" + osKernelCopyTemplateFile.getAbsolutePath(), e);
        }
        return rc;
    }

};