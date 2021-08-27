//,temp,CamelAnnotationsHandler.java,312,369,temp,CamelAnnotationsHandler.java,172,226
//,3
public class xxx {
    public static void handleProvidesBreakpoint(ConfigurableApplicationContext context, Class<?> testClass) throws Exception {
        Collection<Method> methods = CamelSpringTestHelper.getAllMethods(testClass);
        final List<Breakpoint> breakpoints = new LinkedList<>();

        for (Method method : methods) {
            if (AnnotationUtils.findAnnotation(method, ProvidesBreakpoint.class) != null) {
                Class<?>[] argTypes = method.getParameterTypes();
                if (argTypes.length != 0) {
                    throw new IllegalArgumentException(
                            "Method [" + method.getName()
                                                       + "] is annotated with ProvidesBreakpoint but is not a no-argument method.");
                } else if (!Breakpoint.class.isAssignableFrom(method.getReturnType())) {
                    throw new IllegalArgumentException(
                            "Method [" + method.getName()
                                                       + "] is annotated with ProvidesBreakpoint but does not return a Breakpoint.");
                } else if (!Modifier.isStatic(method.getModifiers())) {
                    throw new IllegalArgumentException(
                            "Method [" + method.getName()
                                                       + "] is annotated with ProvidesBreakpoint but is not static.");
                } else if (!Modifier.isPublic(method.getModifiers())) {
                    throw new IllegalArgumentException(
                            "Method [" + method.getName()
                                                       + "] is annotated with ProvidesBreakpoint but is not public.");
                }

                try {
                    breakpoints.add((Breakpoint) method.invoke(null));
                } catch (Exception e) {
                    throw new RuntimeException(
                            "Method [" + method.getName()
                                               + "] threw exception during evaluation.",
                            e);
                }
            }
        }

        if (!breakpoints.isEmpty()) {
            CamelSpringTestHelper.doToSpringCamelContexts(context, new CamelSpringTestHelper.DoToSpringCamelContextsStrategy() {

                public void execute(String contextName, SpringCamelContext camelContext)
                        throws Exception {
                    Debugger debugger = camelContext.getDebugger();
                    if (debugger == null) {
                        debugger = new DefaultDebugger();
                        camelContext.setDebugger(debugger);
                    }

                    for (Breakpoint breakpoint : breakpoints) {
                        LOGGER.info("Adding Breakpoint [{}] to CamelContext with name [{}].", breakpoint, contextName);
                        debugger.addBreakpoint(breakpoint);
                    }
                }
            });
        }
    }

};