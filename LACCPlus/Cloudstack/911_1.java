//,temp,BufferedImageCanvas.java,108,129,temp,RdpBufferedImageCanvas.java,55,75
//,3
public class xxx {
    @Override
    public byte[] getFrameBufferJpeg() {
        int width = 800;
        int height = 600;

        width = offlineImage.getWidth();
        height = offlineImage.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = bufferedImage.createGraphics();
        synchronized (offlineImage) {
            g.drawImage(offlineImage, 0, 0, width, height, 0, 0, width, height, null);
        }

        byte[] imgBits = null;
        try {
            imgBits = ImageHelper.jpegFromImage(bufferedImage);
        } catch (IOException e) {
            s_logger.info("[ignored] read error on image", e);
        }
        return imgBits;
    }

};