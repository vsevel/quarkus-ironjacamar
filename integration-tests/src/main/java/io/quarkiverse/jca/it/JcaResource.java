package io.quarkiverse.jca.it;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import io.smallrye.mutiny.Uni;

@Path("/jca")
@ApplicationScoped
public class JcaResource {
    // add some rest methods here

    @Inject
    ConnectionFactory factory;

    @GET
    public Uni<String> hello(@QueryParam("name") @DefaultValue("JCA") String name) {
        return Uni.createFrom().item(() -> {
            try (JMSContext context = factory.createContext()) {
                Queue myQueue = context.createQueue("MyQueue");
                context.createProducer().send(myQueue, "Hello " + name);
                return "Hello " + name;
            }
        });
    }
}
