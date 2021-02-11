//,temp,TestSetFile.java,90,101,temp,TestArrayFile.java,61,72
//,3
public class xxx {
  private static RandomDatum[] generate(int count) {
    LOG.info("generating " + count + " records in memory");
    RandomDatum[] data = new RandomDatum[count];
    RandomDatum.Generator generator = new RandomDatum.Generator();
    for (int i = 0; i < count; i++) {
      generator.next();
      data[i] = generator.getValue();
    }
    LOG.info("sorting " + count + " records");
    Arrays.sort(data);
    return data;
  }

};