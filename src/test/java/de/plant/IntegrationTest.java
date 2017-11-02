package de.plant;

import de.plant.data.Plant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

  @Value("${local.server.port}")
  private int port;

  @Test
  public void plantyGetAll() {
    TestRestTemplate testRestTemplate = new TestRestTemplate("admin","PlantsVsZombies",
        TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    ResponseEntity<List> plantResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/plant/all", List.class);

    Assert.assertEquals(plantResponse.getStatusCode(), HttpStatus.OK);
    Assert.assertNotNull(plantResponse.getBody());
  }

  @Test
  public void plantyGetFirst() {
    TestRestTemplate testRestTemplate = new TestRestTemplate("admin","PlantsVsZombies",
        TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    ResponseEntity<Plant> plantResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/plant/0", Plant.class, "");

    Assert.assertEquals(plantResponse.getStatusCode(), HttpStatus.OK);
    Assert.assertNotNull(plantResponse.getBody());
  }

  @Test
  public void plantyPut() {
    Plant testPlant = new Plant("Test", 0, 1, 70, 10);
    TestRestTemplate testRestTemplate = new TestRestTemplate("admin","PlantsVsZombies",
        TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    testRestTemplate.put("http://localhost:" + port + "/plant", testPlant);
    ResponseEntity<Plant> plantResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/plant/1", Plant.class);

    Assert.assertEquals(plantResponse.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(testPlant, plantResponse.getBody());
  }

  @Test
  public void plantyPost() {
    TestRestTemplate testRestTemplate = new TestRestTemplate("admin","PlantsVsZombies",
        TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    testRestTemplate.postForEntity("http://localhost:" + port + "/plant",
        new Plant("Test", 0, 1, 70, 10), Plant.class);
    ResponseEntity<Plant> plantResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/plant/0", Plant.class, "");

    Assert.assertEquals(plantResponse.getStatusCode(), HttpStatus.OK);
    Assert.assertNotNull(plantResponse.getBody());
  }

  @Test
  public  void plantyConnectionRefused() {
    TestRestTemplate testRestTemplate = new TestRestTemplate();
    ResponseEntity<Plant> plantResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/plant/0", Plant.class, "");

    Assert.assertEquals(plantResponse.getStatusCode(), HttpStatus.UNAUTHORIZED);
  }

  @Test
  public void plantyGetAllAdded() {
    TestRestTemplate testRestTemplate = new TestRestTemplate("admin","PlantsVsZombies",
        TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    ResponseEntity<List> plantResponse = testRestTemplate.getForEntity("http://localhost:" + port + "/plant/all", List.class);

    Assert.assertEquals(plantResponse.getStatusCode(), HttpStatus.OK);
    Assert.assertNotNull(plantResponse.getBody());
  }
}