//,temp,Log4JConfigView.java,158,199,temp,Log4JConfigView.java,56,85
//,3
public class xxx {
    @Override
    public void setRootLogLevel(String level) throws Exception {
        ClassLoader cl = getClassLoader();

        if (!isLog4JAvailable(cl)) {
            return;
        }

        Class<?> loggerClass = getLoggerClass(cl);
        Class<?> levelClass = getLevelClass(cl);
        if (levelClass == null || loggerClass == null) {
            return;
        }

        String targetLevel = level.toUpperCase(Locale.US);
        Method getRootLogger = loggerClass.getMethod("getRootLogger", new Class[]{});
        Method setLevel = loggerClass.getMethod("setLevel", levelClass);
        Object rootLogger = getRootLogger.invoke(null, (Object[])null);
        Method toLevel = levelClass.getMethod("toLevel", String.class);
        Object newLevel = toLevel.invoke(null, targetLevel);

        // Check that the level conversion worked and that we got a level
        // that matches what was asked for.  A bad level name will result
        // in the lowest level value and we don't want to change unless we
        // matched what the user asked for.
        if (newLevel != null && newLevel.toString().equals(targetLevel)) {
            LOG.debug("Set level {} for root logger.", level);
            setLevel.invoke(rootLogger, newLevel);
        }
    }

};