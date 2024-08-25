package com.ashokit.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashokit.entities.Counsellor;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor, Integer>{
	
	// select * from counsellor_tbl where email=:email
	public Counsellor findByEmail(String email);
	
	// select * from counsellor tbl where email=:email and pwd=:pwd
	public Counsellor findByEmailAndPwd(String email,String password);
}
