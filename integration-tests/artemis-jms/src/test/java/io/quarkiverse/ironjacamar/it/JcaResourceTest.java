package io.quarkiverse.ironjacamar.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.artemis.test.ArtemisTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(value = ArtemisTestResource.class, restrictToAnnotatedClass = true)
public class JcaResourceTest {

    @Test
    public void testProducer() {
        given().when().get("/jca?name=George").then().statusCode(200).body(is("Hello George"));
    }

    @Test
    public void testProducerRollback() {
        given().when().get("/jca?name=rollback").then().statusCode(200).body(is("Hello rollback"));
    }

    @Test
    public void testTransacted() {
        given().when().get("/jca/transacted").then().statusCode(200).body(is("true"));
    }

    @Test
    public void testNotTransacted() {
        given().when().get("/jca/not-transacted").then().statusCode(200).body(is("false"));
    }
}