package com.piranhaview;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AssignmentRepository extends PagingAndSortingRepository<Assignment, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM Assignment WHERE boatId = :boatId AND timeSlotId != :timeSlotId")
	void removeAssignments(@Param("boatId") long boatId, @Param("timeSlotId") long timeSlotId);
}
