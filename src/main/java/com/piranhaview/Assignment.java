package com.piranhaview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="Assignment")
@IdClass(AssignmentId.class)
public class Assignment {

	@Id
	@JsonProperty("timeslot_id")
	@Column(name="timeslot_id")
	private long timeslotId;

	@Id
	@JsonProperty("boat_id")
	@Column(name="boat_id")
	private long boatId;

	public long getBoatId() {
		return boatId;
	}

	public void setBoatId(long boatId) {
		this.boatId = boatId;
	}
}
