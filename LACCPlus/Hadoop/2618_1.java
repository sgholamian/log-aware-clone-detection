//,temp,RandomTextDataGenerator.java,105,112,temp,ApplicationMaster.java,1211,1217
//,3
public class xxx {
  static void setRandomTextDataGeneratorListSize(Configuration conf, 
                                                 int listSize) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Random text data generator is configured to use a dictionary " 
                + " with " + listSize + " words");
    }
    conf.setInt(GRIDMIX_DATAGEN_RANDOMTEXT_LISTSIZE, listSize);
  }

};