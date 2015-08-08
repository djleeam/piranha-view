package com.piranhaview;

import java.util.Date;

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
import com.piranhaview.domain.Assignment;
import com.piranhaview.domain.Boat;
import com.piranhaview.domain.Booking;
import com.piranhaview.domain.TimeSlot;

import static com.jayway.restassured.RestAssured.given;

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
		long timeslot_1_id = postEntity(TIMESLOTS_PATH, createTimeSlot(1437533075000l, 120));
		long boat_1_id = postEntity(BOATS_PATH, createBoat(8, "Amazon Express"));
		long boat_2_id = postEntity(BOATS_PATH, createBoat(4, "Amazon Express Mini"));

		postEntity(ASSIGNMENTS_PATH, createAssignment(timeslot_1_id, boat_1_id));
		postEntity(ASSIGNMENTS_PATH, createAssignment(timeslot_1_id, boat_2_id));

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

		postEntity(BOOKINGS_PATH, createBooking(timeslot_1_id, 6));

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
		long timeslot_1_id = postEntity(TIMESLOTS_PATH, createTimeSlot(1406052000000l, 120));
		long timeslot_2_id = postEntity(TIMESLOTS_PATH, createTimeSlot(1406055600000l, 120));
		long boat_1_id = postEntity(BOATS_PATH, createBoat(8, "Amazon Express"));

		postEntity(ASSIGNMENTS_PATH, createAssignment(timeslot_1_id, boat_1_id));
		postEntity(ASSIGNMENTS_PATH, createAssignment(timeslot_2_id, boat_1_id));

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

		postEntity(BOOKINGS_PATH, createBooking(timeslot_2_id, 2));

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

	private TimeSlot createTimeSlot(long startTime, long duration) throws Exception {
		TimeSlot timeSlot = new TimeSlot();
		timeSlot.setStartTime(new Date(startTime));
		timeSlot.setDuration(duration);
		return timeSlot;
	}

	private Boat createBoat(int capacity, String name) throws Exception {
		Boat boat = new Boat();
		boat.setCapacity(capacity);
		boat.setName(name);
		return boat;
	}

	private Assignment createAssignment(long timeSlotId, long boatId) throws Exception {
		Assignment assignment = new Assignment();
		assignment.setTimeSlotId(timeSlotId);
		assignment.setBoatId(boatId);
		return assignment;
	}

	private Booking createBooking(long timeSlotId, int size) throws Exception {
		Booking booking = new Booking();
		booking.setTimeslotId(timeSlotId);
		booking.setSize(size);
		return booking;
	}

	private Long postEntity(String path, Object entity) {
		Response response =
		given()
			.spec(getRequestSpec())
			.body(entity)
		.expect()
			.statusCode(201).log().all()
		.when()
			.post(API_ENDPOINT + path);
		
		return Long.valueOf(StringUtils.substringAfterLast(response.jsonPath().get("_links.self.href").toString(), "/"));
	}

	private RequestSpecification getRequestSpec() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.addHeader("Content-Type", "application/json");
		return requestSpecBuilder.build();
	}
}
