//,temp,TestSetFile.java,90,101,temp,TestArrayFile.java,61,72
//,3
public class xxx {
  private static RandomDatum[] generate(int count) {
    if(LOG.isDebugEnabled()) {
      LOG.debug("generating " + count + " records in debug");
    }
    RandomDatum[] data = new RandomDatum[count];
    RandomDatum.Generator generator = new RandomDatum.Generator();
    for (int i = 0; i < count; i++) {
      generator.next();
      data[i] = generator.getValue();
    }
    return data;
  }

};