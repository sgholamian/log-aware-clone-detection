//,temp,AcidGuaranteesTestTool.java,405,416,temp,CreateRandomStoreFile.java,310,320
//,3
public class xxx {
  public static void main(String[] args) {
    CreateRandomStoreFile app = new CreateRandomStoreFile();
    try {
      if (!app.run(args))
        System.exit(EXIT_FAILURE);
    } catch (IOException ex) {
      LOG.error(ex.toString(), ex);
      System.exit(EXIT_FAILURE);
    }

  }

};