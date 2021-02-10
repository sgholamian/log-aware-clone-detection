//,temp,ChecksumFs.java,127,152,temp,ChecksumFileSystem.java,138,161
//,3
public class xxx {
    public ChecksumFSInputChecker(ChecksumFileSystem fs, Path file, int bufferSize)
      throws IOException {
      super( file, fs.getFileStatus(file).getReplication() );
      this.datas = fs.getRawFileSystem().open(file, bufferSize);
      this.fs = fs;
      Path sumFile = fs.getChecksumFile(file);
      try {
        int sumBufferSize = fs.getSumBufferSize(fs.getBytesPerSum(), bufferSize);
        sums = fs.getRawFileSystem().open(sumFile, sumBufferSize);

        byte[] version = new byte[CHECKSUM_VERSION.length];
        sums.readFully(version);
        if (!Arrays.equals(version, CHECKSUM_VERSION))
          throw new IOException("Not a checksum file: "+sumFile);
        this.bytesPerSum = sums.readInt();
        set(fs.verifyChecksum, DataChecksum.newCrc32(), bytesPerSum, 4);
      } catch (FileNotFoundException e) {         // quietly ignore
        set(fs.verifyChecksum, null, 1, 0);
      } catch (IOException e) {                   // loudly ignore
        LOG.warn("Problem opening checksum file: "+ file + 
                 ".  Ignoring exception: " , e); 
        set(fs.verifyChecksum, null, 1, 0);
      }
    }

};