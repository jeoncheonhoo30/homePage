package homePage.ex01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private DataSource dataFactory; //데이터 소스변수 생성 
	private Connection conn; //db 연결
	private PreparedStatement pstmt; //서버 실행
	private ResultSet rs; // db 결과를 담는 객체
	public MemberDAO() {
		try {
			Context ctx=new InitialContext(); //servers에서 context.xml를 이용해서
			Context envContext=(Context)ctx.lookup("java:/comp/env"); //자바와 연동
			dataFactory=(DataSource) envContext.lookup("jdbc/oracle"); //오라클을 사용하겠다 mysql도 사용가능 교육용 무료버전으로 오라클 사용증 sql은 무료
		}catch (Exception e) {
			System.out.println("DB 연결 중 에러");
		}
	}
	// 로그인 확인 
		public int login(String id, String pwd) { // 어떤 계정에 대한 실제로 로그인을 시도하는 함수, 인자값으로 ID와 Password를 받아 login을 판단함.
			Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        String dbPWD=""; //db에서 꺼낸 비밀번호를 담을 변수
	        int x =-1;
			//String query = "SELECT pwd FROM member_list WHERE id = ?"; // 실제로 DB에 입력될 명령어를 SQL 문장으로 만듬.
			try {
				StringBuffer query = new StringBuffer();
	            query.append("select pwd from member_list where id=?");
	            
				conn=dataFactory.getConnection();
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, id);
				rs = pstmt.executeQuery(); // 어떠한 결과를 받아오는 ResultSet 타입의 rs 변수에 쿼리문을 실행한 결과를 넣어줌 
				
				if (rs.next()) {
					dbPWD = rs.getString("pwd");
					if (dbPWD.equals(pwd)) 
						  x = 1; // 넘겨받은 비번과 꺼내온 배번 비교. 같으면 인증성공
			                else                  
			              x = 0; // DB의 비밀번호와 입력받은 비밀번호 다름, 인증실패
			                
			            } else {
			                x = -1; // 해당 아이디가 없을 경우
			            }
				return x;
				
			} catch (Exception sqle) {
	            throw new RuntimeException(sqle.getMessage());
			}finally {
	            try{
	                if ( pstmt != null ){ pstmt.close(); pstmt=null; }
	                if ( conn != null ){ conn.close(); conn=null;    }
	            }catch(Exception e){
	                throw new RuntimeException(e.getMessage());
	            }
			}
			
		}
	
	//회원 조회 메서드
	public List<MemberVO> listMembers() {
		List<MemberVO> memberList=new ArrayList<MemberVO>();
		try {
			conn=dataFactory.getConnection(); // db연결
			String query="select * from member_list order by joinDate desc ";//레코드를 전부 가져올 건데 순서를 가입일자 최근 순으로
			//String query="SELECT MAX(id) FROM member_list;";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery(); //excuteQuery sql디벨로퍼에 시작 버튼 누르는 것 DB실행
			while (rs.next()) { //레코드에 있는 정보가 많으니 와일 문 사용
				String id=rs.getString("id"); //실제 DB에 컬럼 이름 id
				String pwd=rs.getString("pwd"); //DB에 컬럼명이 다르면 안된다
				String name=rs.getString("name");
				String email=rs.getString("email");
				Date joinDate=rs.getDate("joinDate");
				MemberVO memberVO=new MemberVO(id, pwd, name, email, joinDate); //db와 화면 중간 역활 memberVO 코딩 해야함
				memberList.add(memberVO);
			}// 와일문 다 사용한 후 닫아줘야 한다.
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("DB 조회 중 에러");
		}
		return memberList; //와일문 다 돌고 memberList로 리턴 해준다.
	}
	//회원 가입 메서드
	public void addMember(MemberVO mem) {
		try {
			conn=dataFactory.getConnection();//db연결
			String id=mem.getId();
			String pwd=mem.getPwd();
			String name=mem.getName();
			String email=mem.getEmail();
			String query="insert into member_list (id, pwd, name, email) values(?,?,?,?)";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("DB 추가 중 에러");
		}
	}
	//로그인 정보 출력
	public MemberVO newMember(MemberVO memberVO) { //id는 외부로부터 받은 매개변수 값
		MemberVO memberInfo=null;
		try {
			conn=dataFactory.getConnection();
			String query="select * from member_list where id=?";
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, memberVO.getId());
			System.out.println(query);
			ResultSet rs=pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString(1); //id는 찾은 데이터 값을 컬럼에서 가져온 값
				String pwd=rs.getString(2);
				String name=rs.getString(3);
				String email=rs.getString(4);
				memberInfo=new MemberVO(id, pwd, name, email);
				//Date joinDate=rs.getDate("joinDate");
			}
			pstmt.close();
			conn.close();
			rs.close();
		}catch (Exception e) {
			System.out.println("로그인 실패");
		}
		return memberInfo;
	}
	
	//수정할 회원정보 찾기
	public MemberVO findMember(String _id) { //_id는 외부로부터 받은 매개변수 값
		MemberVO memberInfo=null;
		try {
			conn=dataFactory.getConnection();
			String query="select * from member_list where id=?";
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, _id);
			System.out.println(query);
			ResultSet rs=pstmt.executeQuery();
			rs.next();
			String id=rs.getString("id"); //id는 찾은 데이터 값을 컬럼에서 가져온 값
			String pwd=rs.getString("pwd");
			String name=rs.getString("name");
			String email=rs.getString("email");
			Date joinDate=rs.getDate("joinDate");
			memberInfo=new MemberVO(id, pwd, name, email, joinDate);
			pstmt.close();
			conn.close();
			rs.close();
		}catch (Exception e) {
			System.out.println("DB에서 회원정보 찾는 중 에러");
		}
		return memberInfo;
	}
	
	//회원 정보 수정
	public void modMember(MemberVO memberVO) {
		String id=memberVO.getId();
		String pwd=memberVO.getPwd();
		String name=memberVO.getName();
		String email=memberVO.getEmail();
		try {
			conn=dataFactory.getConnection();
			String query="update member_list set pwd=?, name=?, email=? where id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, pwd);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, id); //where 조건에 있는 id값의 비밀번호,이름,이메일 수정할거야
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("DB에서 회원정보 수정 중 에러");
		}
	}
	
	//회원 정보 삭제
	public void delMember(String id) {
		try {
			conn=dataFactory.getConnection();
			String query="delete from member_list where id=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("DB에서 회원정보 삭제 중 에러");
		}
	}
}
