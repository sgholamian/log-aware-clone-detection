//,temp,AdviceWithTasks.java,261,290,temp,AdviceWithTasks.java,195,226
//,3
public class xxx {
            public void task() throws Exception {
                Iterator<ProcessorDefinition<?>> it = AdviceWithTasks.createMatchByIterator(route, matchBy, selectFirst,
                        selectLast, selectFrom, selectTo, maxDeep);
                boolean match = false;
                while (it.hasNext()) {
                    ProcessorDefinition<?> output = it.next();
                    if (matchBy.match(output)) {
                        List<ProcessorDefinition<?>> outputs = getOutputs(output, route);
                        if (outputs != null) {
                            int index = outputs.indexOf(output);
                            if (index != -1) {
                                match = true;
                                // flattern as replace uses a pipeline as
                                // temporary holder
                                ProcessorDefinition<?> flattern = flatternOutput(replace);
                                outputs.add(index + 1, flattern);
                                Object old = outputs.remove(index);
                                // must set parent on the node we added in the
                                // route
                                ProcessorDefinition parent = output.getParent() != null ? output.getParent() : route;
                                flattern.setParent(parent);
                                LOG.info("AdviceWith ({}) : [{}] --> replace [{}]", matchBy.getId(), old, flattern);
                            }
                        }
                    }
                }

                if (!match) {
                    throw new IllegalArgumentException(
                            "There are no outputs which matches: " + matchBy.getId() + " in the route: " + route);
                }
            }

};