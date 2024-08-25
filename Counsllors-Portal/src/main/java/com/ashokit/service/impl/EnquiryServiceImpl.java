package com.ashokit.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ashokit.dto.ViewEnqsFilterRequest;
import com.ashokit.entities.Counsellor;
import com.ashokit.entities.Enquiry;
import com.ashokit.repo.CounsellorRepo;
import com.ashokit.repo.EnquiryRepo;
import com.ashokit.service.EnquiryService;

import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	private EnquiryRepo enqRepo;

	private CounsellorRepo counsellorRepo;

	public EnquiryServiceImpl(EnquiryRepo enqRepo, CounsellorRepo counsellorRepo) {
		super();
		this.enqRepo = enqRepo;
		this.counsellorRepo = counsellorRepo;
	}

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);
		if (counsellor == null) {
			throw new Exception("No counsellor found");
		}
		// associating counsellor to enquiry
		enq.setCounsellor(counsellor);

		Enquiry save = enqRepo.save(enq); // UPSERT (update+insert)

		if (save.getEnqId() != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		return enqRepo.getEnquiryByCounsellorId(counsellorId);
	}

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		return enqRepo.findById(enqId).orElse(null);
	}

	@Override
	public List<Enquiry> getEnquiresWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId) {

		// Query By Example implementation (Dynamic Query preparation)

		Enquiry enq = new Enquiry(); // entity

		if (StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}

		if (StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}

		if (StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}

		Counsellor c = counsellorRepo.findById(counsellorId).orElse(null);

		enq.setCounsellor(c);

		Example<Enquiry> of = Example.of(enq); // dynamic query

		List<Enquiry> enqList = enqRepo.findAll(of);

		return enqList;
	}

}
