package com.piranhaview.service;

import java.util.List;

import com.piranhaview.domain.Assignment;

public interface IAssignmentService {

	Assignment create(Assignment assignment);

	Assignment findOne(Long id);

	List<Assignment> findAll();

	void removeOne(Long id);

	void removeBoatAssignments(Long boatId, Long timeSlotId);

}
