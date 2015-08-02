package com.piranhaview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.piranhaview.data.AssignmentRepository;
import com.piranhaview.data.BoatRepository;
import com.piranhaview.data.TimeSlotRepository;
import com.piranhaview.domain.Assignment;
import com.piranhaview.domain.Boat;
import com.piranhaview.domain.TimeSlot;

@Service
public class AssignmentService implements IAssignmentService {

    private final TimeSlotRepository timeSlotRepository;
    private final BoatRepository boatRepository;
	private final AssignmentRepository assignmentRepository;

    @Autowired
    public AssignmentService(TimeSlotRepository timeSlotRepository,
    		BoatRepository boatRepository, AssignmentRepository assignmentRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.boatRepository = boatRepository;
        this.assignmentRepository = assignmentRepository;
    }

	@Override
	public Assignment create(Assignment assignment) {
		Assignment assigned = assignmentRepository.save(assignment);
        processAssignment(assigned);
        return assigned;
	}

	private void processAssignment(Assignment assigned) {
		TimeSlot timeSlot = timeSlotRepository.findOne(assigned.getTimeSlotId());
		Boat boat = boatRepository.findOne(assigned.getBoatId());
		
		if (boat.getCapacity() > timeSlot.getAvailability()) {
			timeSlot.setAvailability(boat.getCapacity());
		}

		timeSlotRepository.save(timeSlot);
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

	@Override
	public void removeBoatAssignments(Long boatId, Long timeSlotId) {
		assignmentRepository.removeAssignments(boatId, timeSlotId);

		// TODO: this could be more robust
		for (TimeSlot timeSlot : timeSlotRepository.findAll()) {
			if (timeSlot.getId() != timeSlotId) {
				if (timeSlot.getBoats().isEmpty()) {
					timeSlot.setAvailability(0);
					timeSlotRepository.save(timeSlot);
				}
			}
		}
	}
}
