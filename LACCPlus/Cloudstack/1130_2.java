//,temp,ConsoleProxyManagerImpl.java,804,838,temp,ConsoleProxyManagerImpl.java,259,293
//,3
public class xxx {
        @Override
        public void onLoadReport(ConsoleProxyLoadReportCommand cmd) {
            if (cmd.getLoadInfo() == null) {
                return;
            }

            ConsoleProxyStatus status = null;
            try {
                GsonBuilder gb = new GsonBuilder();
                gb.setVersion(1.3);
                Gson gson = gb.create();
                status = gson.fromJson(cmd.getLoadInfo(), ConsoleProxyStatus.class);
            } catch (Throwable e) {
                s_logger.warn("Unable to parse load info from proxy, proxy vm id : " + cmd.getProxyVmId() + ", info : " + cmd.getLoadInfo());
            }

            if (status != null) {
                int count = 0;
                if (status.getConnections() != null) {
                    count = status.getConnections().length;
                }

                byte[] details = null;
                if (cmd.getLoadInfo() != null) {
                    details = cmd.getLoadInfo().getBytes(Charset.forName("US-ASCII"));
                }
                _consoleProxyDao.update(cmd.getProxyVmId(), count, DateUtil.currentGMTTime(), details);
            } else {
                if (s_logger.isTraceEnabled()) {
                    s_logger.trace("Unable to get console proxy load info, id : " + cmd.getProxyVmId());
                }

                _consoleProxyDao.update(cmd.getProxyVmId(), 0, DateUtil.currentGMTTime(), null);
            }
        }

};