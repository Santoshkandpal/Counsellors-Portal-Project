package com.ashokit.service;

import com.ashokit.dto.DashboardResponse;
import com.ashokit.entities.Counsellor;

public interface CounsellorService {
	public Counsellor findByEmail(String email);

	public Counsellor login(String email,String pwd);
	
	public boolean register(Counsellor counsellor);
	
	public DashboardResponse getDashboardInfo(Integer counsellorId) throws Exception;
	
	

}