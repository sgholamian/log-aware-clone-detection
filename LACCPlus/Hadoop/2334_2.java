//,temp,TestGridmixRecord.java,66,95,temp,TestGridmixRecord.java,37,64
//,3
public class xxx {
  static void lengthTest(GridmixRecord x, GridmixRecord y, int min,
      int max) throws Exception {
    final Random r = new Random();
    final long seed = r.nextLong();
    r.setSeed(seed);
    LOG.info("length: " + seed);
    final DataInputBuffer in = new DataInputBuffer();
    final DataOutputBuffer out1 = new DataOutputBuffer();
    final DataOutputBuffer out2 = new DataOutputBuffer();
    for (int i = min; i < max; ++i) {
      setSerialize(x, r.nextLong(), i, out1);
      // check write
      assertEquals(i, out1.getLength());
      // write to stream
      x.write(out2);
      // check read
      in.reset(out1.getData(), 0, out1.getLength());
      y.readFields(in);
      assertEquals(i, x.getSize());
      assertEquals(i, y.getSize());
    }
    // check stream read
    in.reset(out2.getData(), 0, out2.getLength());
    for (int i = min; i < max; ++i) {
      y.readFields(in);
      assertEquals(i, y.getSize());
    }
  }

};