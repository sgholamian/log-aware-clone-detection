//,temp,TarAggregationStrategy.java,240,272,temp,TarAggregationStrategy.java,204,238
//,3
public class xxx {
    private void addFileToTar(File source, File file, String fileName) throws IOException, ArchiveException {
        File tmpTar = Files.createTempFile(parentDir.toPath(), source.getName(), null).toFile();
        tmpTar.delete();
        if (!source.renameTo(tmpTar)) {
            throw new IOException("Could not make temp file (" + source.getName() + ")");
        }

        FileInputStream fis = new FileInputStream(tmpTar);
        TarArchiveInputStream tin
                = (TarArchiveInputStream) new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.TAR, fis);
        TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(source));
        tos.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
        tos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_POSIX);

        InputStream in = new FileInputStream(file);

        // copy the existing entries    
        ArchiveEntry nextEntry;
        while ((nextEntry = tin.getNextEntry()) != null) {
            tos.putArchiveEntry(nextEntry);
            IOUtils.copy(tin, tos);
            tos.closeArchiveEntry();
        }

        // Add the new entry
        TarArchiveEntry entry = new TarArchiveEntry(fileName == null ? file.getName() : fileName);
        entry.setSize(file.length());
        tos.putArchiveEntry(entry);
        IOUtils.copy(in, tos);
        tos.closeArchiveEntry();

        IOHelper.close(fis, in, tin, tos);
        LOG.trace("Deleting temporary file: {}", tmpTar);
        FileUtil.deleteFile(tmpTar);
    }

};