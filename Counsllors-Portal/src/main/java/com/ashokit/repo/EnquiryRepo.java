package com.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ashokit.entities.Enquiry;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry,Integer> {
	
	@Query(value="select * from enquiry_tbl where counsellor_id=:counsellorID",nativeQuery = true)
	public List<Enquiry> getEnquiryByCounsellorId(Integer counsellorID);
	
	

}
