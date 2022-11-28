package homePage.ex01;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginService")
public class LoginService extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		MemberVO memberVO=new MemberVO(id,pwd,name,email);
		MemberDAO dao=new MemberDAO();
		MemberVO memberInfo= dao.newMember(memberVO);
		
		HttpSession session = request.getSession();
		
		if (memberInfo != null) {
			System.out.println("로그인 성공");
			System.out.println(memberInfo.getId());
			System.out.println(memberInfo.getPwd());
			System.out.println(memberInfo.getName());
			System.out.println(memberInfo.getEmail());
			session.setAttribute("sessionID", id);
		} else { //info가 null값이라면
			System.out.println("로그인 실패");
		}
		
	}

}
