package com.piranhaview;

import static com.jayway.restassured.RestAssured.given;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class PiranhaViewIntegrationTest {
	private static final String API_ENDPOINT = "http://localhost:3000/api";
	private static final String BOATS_PATH = "/boats";
	private static final String TIMESLOTS_PATH = "/timeslots";
	private static final String ASSIGNMENTS_PATH = "/assignments";
	private static final String BOOKINGS_PATH = "/bookings";

	@Test
	public void testCase1() throws Exception {
		String timeslot_1_id = createEntity(TIMESLOTS_PATH, "{ \"start_time\": 1437533075000, \"duration\": 120 }");
		String boat_1_id = createEntity(BOATS_PATH, "{ \"capacity\": 8, \"name\": \"Amazon Express\" }");
		String boat_2_id = createEntity(BOATS_PATH, "{ \"capacity\": 4, \"name\": \"Amazon Express Mini\" }");

		createEntity(ASSIGNMENTS_PATH, "{ \"timeslot_id\": " + timeslot_1_id + ", \"boat_id\": " + boat_1_id + " }");
		createEntity(ASSIGNMENTS_PATH, "{ \"timeslot_id\": " + timeslot_1_id + ", \"boat_id\": " + boat_2_id + " }");

		Response resp1 =
		given()
			.spec(getRequestSpec())
		.expect()
			.statusCode(200).log().all()
		.when()
			.get(API_ENDPOINT + "/timeslots/search/findByStartTime?date=2015-07-22");

		Assert.assertEquals(120, resp1.jsonPath().getInt("_embedded.timeslots[0].duration"));
		Assert.assertEquals(8, resp1.jsonPath().getInt("_embedded.timeslots[0].availability"));
		Assert.assertEquals(0, resp1.jsonPath().getInt("_embedded.timeslots[0].customer_count"));

		createEntity(BOOKINGS_PATH, "{ \"timeslot_id\": " + timeslot_1_id + ", \"size\": 6 }");

		Response resp2 =
		given()
			.spec(getRequestSpec())
		.expect()
			.statusCode(200).log().all()
		.when()
			.get(API_ENDPOINT + "/timeslots/search/findByStartTime?date=2015-07-22");

		Assert.assertEquals(120, resp2.jsonPath().getInt("_embedded.timeslots[0].duration"));
		Assert.assertEquals(4, resp2.jsonPath().getInt("_embedded.timeslots[0].availability"));
		Assert.assertEquals(6, resp2.jsonPath().getInt("_embedded.timeslots[0].customer_count"));
	}

	@Test
	public void testCase2() throws Exception {
		
		String timeslot_1_id = createEntity(TIMESLOTS_PATH, "{ \"start_time\": 1406052000000, \"duration\": 120 }");
		String timeslot_2_id = createEntity(TIMESLOTS_PATH, "{ \"start_time\": 1406055600000, \"duration\": 120 }");
		String boat_1_id = createEntity(BOATS_PATH, "{ \"capacity\": 8, \"name\": \"Amazon Express\" }");

		createEntity(ASSIGNMENTS_PATH, "{ \"timeslot_id\": " + timeslot_1_id + ", \"boat_id\": " + boat_1_id + " }");
		createEntity(ASSIGNMENTS_PATH, "{ \"timeslot_id\": " + timeslot_2_id + ", \"boat_id\": " + boat_1_id + " }");

		Response resp1 =
		given()
			.spec(getRequestSpec())
		.expect()
			.statusCode(200).log().all()
		.when()
			.get(API_ENDPOINT + "/timeslots/search/findByStartTime?date=2014-07-22");

		Assert.assertEquals(120, resp1.jsonPath().getInt("_embedded.timeslots[0].duration"));
		Assert.assertEquals(8, resp1.jsonPath().getInt("_embedded.timeslots[0].availability"));
		Assert.assertEquals(0, resp1.jsonPath().getInt("_embedded.timeslots[0].customer_count"));

		Assert.assertEquals(120, resp1.jsonPath().getInt("_embedded.timeslots[1].duration"));
		Assert.assertEquals(8, resp1.jsonPath().getInt("_embedded.timeslots[1].availability"));
		Assert.assertEquals(0, resp1.jsonPath().getInt("_embedded.timeslots[1].customer_count"));

		createEntity(BOOKINGS_PATH, "{ \"timeslot_id\": " + timeslot_2_id + ", \"size\": 2 }");

		Response resp2 =
		given()
			.spec(getRequestSpec())
		.expect()
			.statusCode(200).log().all()
		.when()
			.get(API_ENDPOINT + "/timeslots/search/findByStartTime?date=2014-07-22");

		Assert.assertEquals(120, resp2.jsonPath().getInt("_embedded.timeslots[0].duration"));
		Assert.assertEquals(0, resp2.jsonPath().getInt("_embedded.timeslots[0].availability"));
		Assert.assertEquals(0, resp2.jsonPath().getInt("_embedded.timeslots[0].customer_count"));

		Assert.assertEquals(120, resp2.jsonPath().getInt("_embedded.timeslots[1].duration"));
		Assert.assertEquals(6, resp2.jsonPath().getInt("_embedded.timeslots[1].availability"));
		Assert.assertEquals(2, resp2.jsonPath().getInt("_embedded.timeslots[1].customer_count"));
	}

	private String createEntity(String path, String entity) {
		Response response =
		given()
			.spec(getRequestSpec())
			.body(entity)
		.expect()
			.statusCode(201).log().all()
		.when()
			.post(API_ENDPOINT + path);
		
		return StringUtils.substringAfterLast(response.jsonPath().get("_links.self.href").toString(), "/");
	}

	private RequestSpecification getRequestSpec() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.addHeader("Content-Type", "application/json");
		return requestSpecBuilder.build();
	}
}
