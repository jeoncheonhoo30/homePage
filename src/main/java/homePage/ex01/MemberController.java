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

@WebServlet("/member/*") //ȸ������ ��ȸ ���
public class MemberController extends HttpServlet {
	MemberDAO memberDAO;
	public void init(ServletConfig config) throws ServletException {
		memberDAO=new MemberDAO(); //��ü �ʱ�ȭ
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
		String action=request.getPathInfo(); //��û���� �������� �޼���
		System.out.println("��û�� : " + action);
		if(action==null || action.equals("/listMembers.do")) { 
			List<MemberVO> memberList=memberDAO.listMembers(); //��û�� ���� ȸ�� ������ ��ȸ  //DAO���� ���� ������ ����
			request.setAttribute("memberList", memberList);
			nextPage="/listMembers.jsp";
		}else if(action.equals("/memberForm.do")) { //ȸ�������ϱ� ������ �� ���� ��
			nextPage="/memberForm.jsp";
		}else if(action.equals("/addMember.do" )) {//memberForm.jsp ���� ȸ������ �ϱ� ������ ����
			String id=request.getParameter("id"); //DAO�� addMember���� �Է� �޾ƿ� ��
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
			MemberVO memberVO=memberDAO.findMember(id); //findMember�޼��带 �������Ѵ� ������ �ڷḦ �޾Ƽ� ����
			request.setAttribute("memberInfo", memberVO); //findmember���� ã�� ���̵� memberInfo�� �־��ش�.
			nextPage="/modMemberForm.jsp"; //jsp�� ���� 
		}else if(action.equals("/modMember.do")) {
			String id=request.getParameter("id");
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			MemberVO memberVO=new MemberVO(id, pwd, name, email);
			memberDAO.modMember(memberVO); //memberDAO���� modMember �޼��� ����
			request.setAttribute("msg", "modified"); //
			nextPage="/member/listMembers.do";
		}else if(action.equals("/delMember.do")) {
			String id=request.getParameter("id");
			memberDAO.delMember(id); //memberDAO���� delMember�޼��� ����
			request.setAttribute("msg", "deleted");
			nextPage="/member/listMembers.do";
		}//���ǿ� �´� �ſ� ���� ������ ���ش�.
		RequestDispatcher dispatcher=request.getRequestDispatcher(nextPage); //�ּҴ� �ٲ����ʴµ� ������ ���ش�
		dispatcher.forward(request, response);//������ view jsp�� �Ѱ��ִ°�
	}

}
