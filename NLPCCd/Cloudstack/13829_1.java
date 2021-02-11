//,temp,sample_7606.java,2,17,temp,sample_487.java,2,17
//,3
public class xxx {
public void dummy_method(){
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


log.info("ignored read error on image");
}
}

};