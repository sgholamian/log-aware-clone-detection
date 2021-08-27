//,temp,ApiMethodHelper.java,241,267,temp,FacebookMethodsTypeHelper.java,117,135
//,3
public class xxx {
    public static List<FacebookMethodsType> getCandidateMethods(String name, String... argNames) {
        final List<FacebookMethodsType> methods = METHOD_MAP.get(name);
        if (methods == null) {
            LOG.debug("No matching method for method {}", name);
            return Collections.emptyList();
        }
        int nArgs = argNames != null ? argNames.length : 0;
        if (nArgs == 0) {
            LOG.debug("Found {} methods for method {}", methods.size(), name);
            return Collections.unmodifiableList(methods);
        } else {
            final List<FacebookMethodsType> filteredSet = filterMethods(methods, MatchType.SUBSET, argNames);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Found {} filtered methods for {}",
                        filteredSet.size(), name + Arrays.toString(argNames).replace('[', '(').replace(']', ')'));
            }
            return filteredSet;
        }
    }

};