package com.carle.prime.controller;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.when;
import static com.jayway.restassured.path.xml.XmlPath.from;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.carle.prime.Application;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;

/**
 * Integration tests for the PrimesController
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext
public class PrimeControllerTest {

  @Value("${local.server.port}")
  int port;

  @Before
  public void setUp() {
    RestAssured.port = port;
  }

  @Test
  public void testGetPrimes10Json() {
    when().
      get("/primes/{upperBound}.json", 10).
    then().
      contentType(ContentType.JSON).
      statusCode(HttpStatus.SC_OK).
      body("initial", Matchers.is(10)).
      body("primes", Matchers.is(asList(2, 3, 5, 7)));
  }

  @Test
  public void testGetPrimes1Json() {
    when().
      get("/primes/{upperBound}.json", 1).
    then().
      contentType(ContentType.JSON).
      statusCode(HttpStatus.SC_OK).
      body("initial", Matchers.is(1)).
      body("primes", Matchers.is(emptyList()));
  }

  @Test
  public void testGetPrimes10Xml() {
    String xml = get("/primes/{upperBound}.xml", 10).andReturn().asString();
    assertThat(from(xml).get("primesResponse.initial"), is("10"));
    assertThat(from(xml).getList("primesResponse.primes.prime"), is(asList("2", "3", "5", "7")));
  }

  @Test
  public void testGetPrimes1Xml() {
    String xml = get("/primes/{upperBound}.xml", 1).andReturn().asString();
    assertThat(from(xml).get("primesResponse.initial"), is("1"));
    assertThat(from(xml).getList("primesResponse.primes.prime"), is(emptyList()));
  }

  @Test
  public void testGetPrimesDefaultContentTypeIsJson() {
    when().
      get("/primes/{upperBound}", 10).
    then().
      contentType(ContentType.JSON).
      statusCode(HttpStatus.SC_OK);
  }
}
