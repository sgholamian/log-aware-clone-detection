//,temp,TarAggregationStrategy.java,240,272,temp,TarAggregationStrategy.java,204,238
//,3
public class xxx {
    private void addEntryToTar(File source, String entryName, byte[] buffer, int length) throws IOException, ArchiveException {
        File tmpTar = Files.createTempFile(parentDir.toPath(), source.getName(), null).toFile();
        tmpTar.delete();
        if (!source.renameTo(tmpTar)) {
            throw new IOException("Cannot create temp file: " + source.getName());
        }

        FileInputStream fis = new FileInputStream(tmpTar);
        TarArchiveInputStream tin
                = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.TAR, fis);
        TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(source));
        tos.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
        tos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_POSIX);

        // copy the existing entries    
        ArchiveEntry nextEntry;
        while ((nextEntry = tin.getNextEntry()) != null) {
            tos.putArchiveEntry(nextEntry);
            IOUtils.copy(tin, tos);
            tos.closeArchiveEntry();
        }

        // Create new entry
        TarArchiveEntry entry = new TarArchiveEntry(entryName);
        entry.setSize(length);
        tos.putArchiveEntry(entry);
        tos.write(buffer, 0, length);
        tos.closeArchiveEntry();

        IOHelper.close(fis, tin, tos);
        LOG.trace("Deleting temporary file: {}", tmpTar);
        FileUtil.deleteFile(tmpTar);
    }

};