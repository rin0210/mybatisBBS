package com.my.batisBBS;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.DTO.MemberDTO;
import com.my.service.ControllerToModel;

@Controller
public class UserLoginController {
	
	@Autowired  // bean을 주입하는 방식 이때  ControllerToModel 타입으로 주입
	ControllerToModel tomodel;
	
//	@Autowired
//    private SqlSession sqlSession;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginForm() {
		System.out.println("Login Controller");
		return "login/loginform";
	}
	@RequestMapping(value="/loginPro", method=RequestMethod.POST)
	public String loginProcess(HttpSession session, @RequestParam("id") String id,  @RequestParam("name") String name) {
		System.out.println("loginPro Controller : "+id);
		String returnURL = "";
		//mapper = sqlSession.getMapper(MapperDAO.class);
        if ( session.getAttribute("login") != null ){
            // 기존에 login이란 세션 값이 존재한다면
            session.removeAttribute("login"); // 기존값을 제거해 준다.
        }
        MemberDTO b = tomodel.selectOne(id);
        System.out.println("B : "+b);
        if ( b != null ){ // 로그인 성공
            session.setAttribute("login", b.getName()); // 세션에 login인이란 이름으로 UserVO 객체를 저장해 놈. 
            returnURL = "redirect:/bbsConlist"; // 로그인 성공시 게시글 목록페이지로 바로 이동하도록 하고
        }else { // 로그인에 실패한 경우
            returnURL = "redirect:/login"; // 로그인 폼으로 다시 가도록 함
        }
		return returnURL;
	}
	@RequestMapping(value="/logoutPro", method=RequestMethod.POST)
	public String logoutProcess(HttpSession session) {
		session.invalidate(); // 세션 전체를 날려버림
//      session.removeAttribute("login"); // 하나씩 하려면 이렇게 해도 됨.
		return "redirect:/login";
	}
}
