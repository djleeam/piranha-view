package com.piranhaview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityListeners(TimeSlotEventListener.class)
@Table(name="TimeSlot")
public class TimeSlot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="timeslot_id")
	private long id;

	@JsonProperty("start_time")
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Column(nullable=false)
	private Long duration;

	@Column(columnDefinition="INT default '0'")
	private int availability;

	@JsonProperty("customer_count")
	@Column(columnDefinition="INT default '0'")
	private int customerCount;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(
		name="Assignment",
		joinColumns={@JoinColumn(name="timeslot_id", referencedColumnName="timeslot_id")},
		inverseJoinColumns={@JoinColumn(name="boat_id", referencedColumnName="boat_id")})
	private List<Boat> boats;

	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(name="timeslot_id", referencedColumnName="timeslot_id")
	private List<Booking> bookings;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}

	public List<Boat> getBoats() {
		return (boats != null) ? boats : new ArrayList<Boat>();
	}

	public void setBoats(List<Boat> boats) {
		this.boats = boats;
	}

	public List<Booking> getBookings() {
		return (bookings != null) ? bookings : new ArrayList<Booking>();
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
}
