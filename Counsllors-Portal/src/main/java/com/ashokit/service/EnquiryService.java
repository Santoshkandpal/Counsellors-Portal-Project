package com.ashokit.service;

import java.util.List;

import com.ashokit.dto.ViewEnqsFilterRequest;
import com.ashokit.entities.Enquiry;

public interface EnquiryService {

	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception;

	public List<Enquiry> getAllEnquiries(Integer counsellorId) throws Exception;

	public List<Enquiry> getEnquiresWithFilter(ViewEnqsFilterRequest filterReq,
			Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer enqId);
	
	

}
