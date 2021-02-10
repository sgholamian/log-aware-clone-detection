//,temp,TestDFSIO.java,575,585,temp,TestDFSIO.java,514,522
//,3
public class xxx {
    @Override // IOMapperBase
    public Closeable getIOStream(String name) throws IOException {
      // open file
      InputStream in = fs.open(new Path(getDataDir(getConf()), name));
      if(compressionCodec != null)
        in = compressionCodec.createInputStream(in);
      LOG.info("in = " + in.getClass().getName());
      return in;
    }

};