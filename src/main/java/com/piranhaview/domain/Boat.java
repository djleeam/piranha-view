package com.piranhaview.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Boat")
public class Boat implements Comparable<Boat> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="boat_id")
	private long id;

	private int capacity;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Boat o) {
		if (this.getCapacity() > o.getCapacity()) {
			return -1;
		}
		else if (this.getCapacity() < o.getCapacity()) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Boat other = (Boat) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Boat [id=" + id + ", capacity=" + capacity + ", name=" + name
				+ "]";
	}
}
