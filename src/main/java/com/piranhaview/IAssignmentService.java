package com.piranhaview;

import java.util.List;

public interface IAssignmentService {

	Assignment create(Assignment assignment);

	Assignment findOne(Long id);

	List<Assignment> findAll();

	void removeOne(Long id);

	void removeBoatAssignments(Long boatId, Long timeSlotId);

}
