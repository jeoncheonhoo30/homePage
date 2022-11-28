package homePage.ex01;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/*") //회원정보 조회 기능
public class MemberController extends HttpServlet {
	MemberDAO memberDAO;
	public void init(ServletConfig config) throws ServletException {
		memberDAO=new MemberDAO(); //객체 초기화
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage=null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action=request.getPathInfo(); //요청명을 가져오는 메서드
		System.out.println("요청명 : " + action);
		if(action==null || action.equals("/listMembers.do")) { 
			List<MemberVO> memberList=memberDAO.listMembers(); //요청에 대해 회원 정보를 조회  //DAO돌고 리턴 문으로 받음
			request.setAttribute("memberList", memberList);
			nextPage="/listMembers.jsp";
		}else if(action.equals("/memberForm.do")) { //회원가입하기 누르면 이 값이 들어감
			nextPage="/memberForm.jsp";
		}else if(action.equals("/addMember.do" )) {//memberForm.jsp 에서 회원가입 하기 누르면 실행
			String id=request.getParameter("id"); //DAO에 addMember에서 입력 받아온 값
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			MemberVO memberVO=new MemberVO(id, pwd, name, email);
			memberDAO.addMember(memberVO);
			request.setAttribute("msg", "modified");
			nextPage="/newMember.jsp";
		}else if(action.equals("/LoginService")) {
			nextPage="/main.jsp";
		}else if(action.equals("/modMemberForm.do")) {
			String id=request.getParameter("id");
			MemberVO memberVO=memberDAO.findMember(id); //findMember메서드를 만들어야한다 수정할 자료를 받아서 수정
			request.setAttribute("memberInfo", memberVO); //findmember에서 찾은 아이디를 memberInfo에 넣어준다.
			nextPage="/modMemberForm.jsp"; //jsp를 생성 
		}else if(action.equals("/modMember.do")) {
			String id=request.getParameter("id");
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			MemberVO memberVO=new MemberVO(id, pwd, name, email);
			memberDAO.modMember(memberVO); //memberDAO에서 modMember 메서드 생성
			request.setAttribute("msg", "modified"); //
			nextPage="/member/listMembers.do";
		}else if(action.equals("/delMember.do")) {
			String id=request.getParameter("id");
			memberDAO.delMember(id); //memberDAO에서 delMember메서드 생성
			request.setAttribute("msg", "deleted");
			nextPage="/member/listMembers.do";
		}//조건에 맞는 거에 따라 포워딩 해준다.
		RequestDispatcher dispatcher=request.getRequestDispatcher(nextPage); //주소는 바뀌지않는데 포워딩 해준다
		dispatcher.forward(request, response);//포워딩 view jsp에 넘겨주는것
	}

}
