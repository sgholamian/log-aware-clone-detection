//,temp,ZooObjectDetectionPredictor.java,87,95,temp,CustomImageClassificationPredictor.java,66,74
//,3
public class xxx {
    private Map<String, Float> classify(Model model, Translator translator, File input) throws Exception {
        try {
            Image image = ImageFactory.getInstance().fromInputStream(new FileInputStream(input));
            return classify(model, translator, image);
        } catch (IOException e) {
            LOG.error("Couldn't transform input into a BufferedImage");
            throw new RuntimeException("Couldn't transform input into a BufferedImage", e);
        }
    }

};