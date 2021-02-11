//,temp,sample_2217.java,2,16,temp,sample_2216.java,2,16
//,2
public class xxx {
public void dummy_method(){
javax.imageio.ImageIO.write(img, "jpg", bos);
byte[] bs = bos.toByteArray();
Headers hds = t.getResponseHeaders();
hds.set("Content-Type", "image/jpeg");
hds.set("Cache-Control", "no-cache");
hds.set("Cache-Control", "no-store");
t.sendResponseHeaders(200, bs.length);
OutputStream os = t.getResponseBody();
os.write(bs);
os.close();


log.info("console not ready sent dummy jpg response");
}

};