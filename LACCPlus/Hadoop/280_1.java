//,temp,TestDFSIO.java,575,585,temp,TestDFSIO.java,514,522
//,3
public class xxx {
    @Override // IOMapperBase
    public Closeable getIOStream(String name) throws IOException {
      Path filePath = new Path(getDataDir(getConf()), name);
      this.fileSize = fs.getFileStatus(filePath).getLen();
      InputStream in = fs.open(filePath);
      if(compressionCodec != null)
        in = new FSDataInputStream(compressionCodec.createInputStream(in));
      LOG.info("in = " + in.getClass().getName());
      LOG.info("skipSize = " + skipSize);
      return in;
    }

};