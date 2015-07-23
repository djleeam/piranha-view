package com.piranhaview;

import java.io.Serializable;

public class AssignmentId implements Serializable {
	private static final long serialVersionUID = 1L;

	private long timeslotId;
	private long boatId;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (boatId ^ (boatId >>> 32));
		result = prime * result + (int) (timeslotId ^ (timeslotId >>> 32));
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
		AssignmentId other = (AssignmentId) obj;
		if (boatId != other.boatId)
			return false;
		if (timeslotId != other.timeslotId)
			return false;
		return true;
	}
}
