//,temp,BufferedImageCanvas.java,131,153,temp,RdpBufferedImageCanvas.java,77,100
//,2
public class xxx {
    @Override
    public byte[] getTilesMergedJpeg(List<TileInfo> tileList, int tileWidth, int tileHeight) {
        int width = Math.max(tileWidth, tileWidth * tileList.size());
        BufferedImage bufferedImage = new BufferedImage(width, tileHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = bufferedImage.createGraphics();

        synchronized (offlineImage) {
            int i = 0;
            for (TileInfo tile : tileList) {
                Rectangle rc = tile.getTileRect();
                g.drawImage(offlineImage, i * tileWidth, 0, i * tileWidth + rc.width, rc.height, rc.x, rc.y, rc.x + rc.width, rc.y + rc.height, null);
                i++;
            }
        }

        byte[] imgBits = null;
        try {
            imgBits = ImageHelper.jpegFromImage(bufferedImage);
        } catch (IOException e) {
            s_logger.info("[ignored] read error on image tiles", e);
        }
        return imgBits;
    }

};