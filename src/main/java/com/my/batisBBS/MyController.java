package com.my.batisBBS;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.my.DTO.MemberDTO;
import com.my.service.ControllerToModel;

@Controller
public class MyController {
//	private MapperDAO mapper;
//
//	@Autowired // 해당되는 객체(정확히는 IOC에 있는 bean을)를 자동 주입
//	private SqlSession sqlSession;

//	@RequestMapping("list")
//	public String list1(Model model) {
//		mapper = sqlSession.getMapper(MapperDAO.class);
//		ArrayList<MemberDTO> dTOs = mapper.selectAll();
//		model.addAttribute("list", dTOs);
//		System.out.println("DB에서 가져온 튜플 수" + dTOs.size());
//		
//		return "list"; // list는 view이름 (model이라는 매개변수를 넘겨줌)
//	}
//	
//	@RequestMapping("del")
//	public String list2(Model model) {
//		mapper = sqlSession.getMapper(MapperDAO.class);
//		mapper.del();
//		
//		ArrayList<MemberDTO> dTOs = mapper.selectAll();
//		model.addAttribute("list", dTOs);
//		System.out.println("DB에서 가져온 튜플 수" + dTOs.size());
//		
//		return "list"; // list는 view이름 (model이라는 매개변수를 넘겨줌)
//	}

	@Autowired // bean을 주입하는 방식, 이때 ControllerToModel 타입으로 주입
	ControllerToModel tomodel;
	// 스프링 컨테이너에 있는 객체를 가져오라
	// 여기서 가져온 빈이 서블릿컨텍스트에 있는 <beans:bean id="testDB" class="com.my.service.MyTestService"></beans:bean>

	// 컨트롤러랑 모델의 소스를 분리시킨 것
	@RequestMapping("bbsConlist")
	public String list(Model model) {
//		MyTestService k = new MyTestService(); // 스프링에서는 new 연산자를 잘 사용하지 않음
		model.addAttribute("list", tomodel.selectAll());

		return "/bbs/list";
	}

	@RequestMapping("bbsConinput")
	public String inputform() {
		return "/bbs/input";
	}

	@RequestMapping("bbsConinputprocess")
	public String inputDB(HttpServletRequest request) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		System.out.println(id + "/" + name);
		tomodel.insert(id, name);
		// mybatis에 접근해서 전달하면 된다.

		return "redirect:bbsConlist"; // 다시 list 컨트롤러에게 넘어가라
	}

	@RequestMapping("bbsCondel")
	public String del(HttpServletRequest request) { // 변수를 이렇게 받아도 되고
		String id = request.getParameter("id");
		tomodel.del(id);

		return "redirect:list";
	}

	@RequestMapping("bbsConmodify")
	public String mod(@RequestParam("id") String id, Model model) { // 이렇게 받아도 된다.
		model.addAttribute("userInfo", tomodel.selectOne(id));

		return "/bbs/modify";
	}

	@RequestMapping("bbsConmodifyprocess")
	public String modp(@ModelAttribute MemberDTO user) { // 변수를 객체로 받는 방법(이때 MemberDTO 랑 변수명이 동일해야함)
//		System.out.println(user.getId());
//		System.out.println(user.getName());

		tomodel.modify(user);
		return "redirect:list";
	}

	// 수정할때 데이터 받는 부분을 hashmap으로 처리하는 부분
	@RequestMapping("bbsConmodifyprocesshash")
	public String modpHash(HttpServletRequest request) {
		HashMap<String, String> h = new HashMap();
		h.put("id", request.getParameter("id"));
		h.put("name", request.getParameter("name"));
		tomodel.modifyhash(h);
		// mybatis에 접근해서 전달하면 된다.

		return "redirect:list"; // 다시 list 컨트롤러에게 넘어가라
	}
}
