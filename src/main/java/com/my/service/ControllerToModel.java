package com.my.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.my.DTO.MemberDTO;

// 컨트롤러에서 모델로 접근하기 위한 인터페이스(상속받는 클래스는 하나만 있을 수 있음)
public interface ControllerToModel {
	public ArrayList<MemberDTO> selectAll();
	
	public void insert(String id, String name);

	public void del(String id);
	
	public MemberDTO selectOne(String id);
	
	public void modify(MemberDTO memberdto);
	
	public void modifyhash(HashMap<String, String> map);
}
