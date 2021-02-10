//,temp,RandomTextDataGenerator.java,124,131,temp,TimelineCollector.java,217,225
//,3
public class xxx {
  static void setRandomTextDataGeneratorWordSize(Configuration conf, 
                                                 int wordSize) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Random text data generator is configured to use a dictionary " 
                + " with words of length " + wordSize);
    }
    conf.setInt(GRIDMIX_DATAGEN_RANDOMTEXT_WORDSIZE, wordSize);
  }

};