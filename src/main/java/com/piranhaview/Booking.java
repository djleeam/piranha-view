package com.piranhaview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="Booking")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="booking_id")
	private long id;

	@JsonProperty("timeslot_id")
	@Column(name="timeslot_id")
	private long timeslotId;

	@JsonProperty("size")
	private int size;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTimeslotId() {
		return timeslotId;
	}

	public void setTimeslotId(long timeslotId) {
		this.timeslotId = timeslotId;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
