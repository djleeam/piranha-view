package com.piranhaview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.piranhaview.data.AssignmentRepository;
import com.piranhaview.domain.Assignment;

@Service
public class AssignmentService implements IAssignmentService {

	private final AssignmentRepository assignmentRepository;
    private final TimeSlotService timeSlotService;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository,
    		TimeSlotService timeSlotService) {
        this.assignmentRepository = assignmentRepository;
        this.timeSlotService = timeSlotService;
    }

	@Override
	public Assignment create(Assignment assignment) {
		Assignment assigned = assignmentRepository.save(assignment);
        timeSlotService.updateTimeSlotAvailability(assigned.getTimeSlotId());
        return assigned;
	}

	@Override
	public Assignment findOne(Long id) {
		return assignmentRepository.findOne(id);
	}

	@Override
	public List<Assignment> findAll() {
		return Lists.newArrayList(assignmentRepository.findAll());
	}

	@Override
	public void removeOne(Long id) {
		assignmentRepository.delete(id);
	}

	/**
	 * Remove assignments of given boat for all time slots except
	 * for indicated time slot. 
	 */
	@Override
	public void removeBoatAssignments(Long boatId, Long timeSlotId) {
		assignmentRepository.removeAssignments(boatId, timeSlotId);
		timeSlotService.updateTimeSlotAvailabilities();
	}
}
