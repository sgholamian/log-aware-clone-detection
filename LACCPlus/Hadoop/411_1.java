//,temp,TestGridmixRecord.java,66,95,temp,TestGridmixRecord.java,37,64
//,3
public class xxx {
  static void randomReplayTest(GridmixRecord x, GridmixRecord y, int min,
      int max) throws Exception {
    final Random r = new Random();
    final long seed = r.nextLong();
    r.setSeed(seed);
    LOG.info("randReplay: " + seed);
    final DataOutputBuffer out1 = new DataOutputBuffer();
    for (int i = min; i < max; ++i) {
      final int s = out1.getLength();
      x.setSeed(r.nextLong());
      x.setSize(i);
      x.write(out1);
      assertEquals(i, out1.getLength() - s);
    }
    final DataInputBuffer in = new DataInputBuffer();
    in.reset(out1.getData(), 0, out1.getLength());
    final DataOutputBuffer out2 = new DataOutputBuffer();
    // deserialize written records, write to separate buffer
    for (int i = min; i < max; ++i) {
      final int s = in.getPosition();
      y.readFields(in);
      assertEquals(i, in.getPosition() - s);
      y.write(out2);
    }
    // verify written contents match
    assertEquals(out1.getLength(), out2.getLength());
    // assumes that writes will grow buffer deterministically
    assertEquals("Bad test", out1.getData().length, out2.getData().length);
    assertArrayEquals(out1.getData(), out2.getData());
  }

};