package com.piranhaview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="Assignment")
public class Assignment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="assignment_id")
	private long id;

	@JsonProperty("timeslot_id")
	@Column(name="timeslot_id")
	private long timeSlotId;

	@JsonProperty("boat_id")
	@Column(name="boat_id")
	private long boatId;

	public long getId() {
		return id;
	}

	public long getTimeSlotId() {
		return timeSlotId;
	}

	public void setTimeSlotId(long timeSlotId) {
		this.timeSlotId = timeSlotId;
	}

	public long getBoatId() {
		return boatId;
	}

	public void setBoatId(long boatId) {
		this.boatId = boatId;
	}
}
