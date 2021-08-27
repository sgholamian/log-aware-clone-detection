//,temp,ZooImageClassificationPredictor.java,81,89,temp,CustomObjectDetectionPredictor.java,74,82
//,3
public class xxx {
    public DetectedObjects classify(Model model, Translator translator, File input) throws Exception {
        try {
            Image image = ImageFactory.getInstance().fromInputStream(new FileInputStream(input));
            return classify(model, translator, image);
        } catch (IOException e) {
            LOG.error("Couldn't transform input into a BufferedImage");
            throw new RuntimeException("Couldn't transform input into a BufferedImage", e);
        }
    }

};