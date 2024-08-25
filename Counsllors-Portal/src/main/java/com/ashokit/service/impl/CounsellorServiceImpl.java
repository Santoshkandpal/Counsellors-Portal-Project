package com.ashokit.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ashokit.dto.DashboardResponse;
import com.ashokit.entities.Counsellor;
import com.ashokit.entities.Enquiry;
import com.ashokit.repo.CounsellorRepo;
import com.ashokit.repo.EnquiryRepo;
import com.ashokit.service.CounsellorService;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	
	private CounsellorRepo counsellorRepo;
	
	public CounsellorServiceImpl(CounsellorRepo counsellorRepo, EnquiryRepo enqRepo) {
		this.counsellorRepo = counsellorRepo;
		this.enqRepo = enqRepo;
	}

	private EnquiryRepo enqRepo;
	
	
	

	@Override
	public boolean register(Counsellor counsellor) {
       Counsellor savedCOunsellor = counsellorRepo.save(counsellor);
       if(null!=savedCOunsellor.getCounsellorId()) {
		return true;
       }
       return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		return counsellorRepo.findByEmailAndPwd(email, pwd);
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) throws Exception{
		DashboardResponse response = new DashboardResponse();
		
		List<Enquiry> enqList = enqRepo.getEnquiryByCounsellorId(counsellorId);
		int totalEnq = enqList.size();
		
		int enrolledEnqs = enqList.stream().filter(e->e.getEnqStatus().equals("Enrolled"))
		.collect(Collectors.toList())
		.size();
		
		int lostEnqs = enqList.stream().filter(e->e.getEnqStatus().equals("Lost"))
				.collect(Collectors.toList())
				.size();
		
		int openEnqs = enqList.stream().filter(e->e.getEnqStatus().equals("Open"))
				.collect(Collectors.toList())
				.size();
		
		response.setTotalEnqs(totalEnq);
		response.setEnrolledEnqs(enrolledEnqs);
		response.setLostEnqs(lostEnqs);
		response.setOpenEnqs(openEnqs);
		
		return response;
	}

	@Override
	public Counsellor findByEmail(String email) {
			return counsellorRepo.findByEmail(email);
		}
	}

