//,temp,ConsoleProxyThumbnailHandler.java,104,182,temp,ConsoleProxyAjaxImageHandler.java,64,159
//,3
public class xxx {
    private void doHandle(HttpExchange t) throws Exception, IllegalArgumentException {
        String queries = t.getRequestURI().getQuery();
        Map<String, String> queryMap = getQueryMap(queries);
        int width = 0;
        int height = 0;
        int port = 0;
        String ws = queryMap.get("w");
        String hs = queryMap.get("h");
        String host = queryMap.get("host");
        String portStr = queryMap.get("port");
        String sid = queryMap.get("sid");
        String tag = queryMap.get("tag");
        String ticket = queryMap.get("ticket");
        String console_url = queryMap.get("consoleurl");
        String console_host_session = queryMap.get("sessionref");

        if (tag == null)
            tag = "";

        if (ws == null || hs == null || host == null || portStr == null || sid == null) {
            throw new IllegalArgumentException();
        }
        try {
            width = Integer.parseInt(ws);
            height = Integer.parseInt(hs);
            port = Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }

        ConsoleProxyClientParam param = new ConsoleProxyClientParam();
        param.setClientHostAddress(host);
        param.setClientHostPort(port);
        param.setClientHostPassword(sid);
        param.setClientTag(tag);
        param.setTicket(ticket);
        param.setClientTunnelUrl(console_url);
        param.setClientTunnelSession(console_host_session);

        ConsoleProxyClient viewer = ConsoleProxy.getVncViewer(param);

        if (!viewer.isHostConnected()) {
            // use generated image instead of static
            BufferedImage img = generateTextImage(width, height, "Connecting");
            ByteArrayOutputStream bos = new ByteArrayOutputStream(8196);
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

            if (s_logger.isInfoEnabled())
                s_logger.info("Console not ready, sent dummy JPG response");
            return;
        }

        {
            Image scaledImage = viewer.getClientScaledImage(width, height);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D bufImageGraphics = bufferedImage.createGraphics();
            bufImageGraphics.drawImage(scaledImage, 0, 0, null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(8196);
            javax.imageio.ImageIO.write(bufferedImage, "jpg", bos);
            byte[] bs = bos.toByteArray();
            Headers hds = t.getResponseHeaders();
            hds.set("Content-Type", "image/jpeg");
            hds.set("Cache-Control", "no-cache");
            hds.set("Cache-Control", "no-store");
            t.sendResponseHeaders(200, bs.length);
            OutputStream os = t.getResponseBody();
            os.write(bs);
            os.close();
        }
    }

};