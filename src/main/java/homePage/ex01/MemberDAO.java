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
	private DataSource dataFactory; //������ �ҽ����� ���� 
	private Connection conn; //db ����
	private PreparedStatement pstmt; //���� ����
	private ResultSet rs; // db ����� ��� ��ü
	public MemberDAO() {
		try {
			Context ctx=new InitialContext(); //servers���� context.xml�� �̿��ؼ�
			Context envContext=(Context)ctx.lookup("java:/comp/env"); //�ڹٿ� ����
			dataFactory=(DataSource) envContext.lookup("jdbc/oracle"); //����Ŭ�� ����ϰڴ� mysql�� ��밡�� ������ ����������� ����Ŭ ����� sql�� ����
		}catch (Exception e) {
			System.out.println("DB ���� �� ����");
		}
	}
	// �α��� Ȯ�� 
		public int login(String id, String pwd) { // � ������ ���� ������ �α����� �õ��ϴ� �Լ�, ���ڰ����� ID�� Password�� �޾� login�� �Ǵ���.
			Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        String dbPWD=""; //db���� ���� ��й�ȣ�� ���� ����
	        int x =-1;
			//String query = "SELECT pwd FROM member_list WHERE id = ?"; // ������ DB�� �Էµ� ��ɾ SQL �������� ����.
			try {
				StringBuffer query = new StringBuffer();
	            query.append("select pwd from member_list where id=?");
	            
				conn=dataFactory.getConnection();
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, id);
				rs = pstmt.executeQuery(); // ��� ����� �޾ƿ��� ResultSet Ÿ���� rs ������ �������� ������ ����� �־��� 
				
				if (rs.next()) {
					dbPWD = rs.getString("pwd");
					if (dbPWD.equals(pwd)) 
						  x = 1; // �Ѱܹ��� ����� ������ ��� ��. ������ ��������
			                else                  
			              x = 0; // DB�� ��й�ȣ�� �Է¹��� ��й�ȣ �ٸ�, ��������
			                
			            } else {
			                x = -1; // �ش� ���̵� ���� ���
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
	
	//ȸ�� ��ȸ �޼���
	public List<MemberVO> listMembers() {
		List<MemberVO> memberList=new ArrayList<MemberVO>();
		try {
			conn=dataFactory.getConnection(); // db����
			String query="select * from member_list order by joinDate desc ";//���ڵ带 ���� ������ �ǵ� ������ �������� �ֱ� ������
			//String query="SELECT MAX(id) FROM member_list;";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery(); //excuteQuery sql�𺧷��ۿ� ���� ��ư ������ �� DB����
			while (rs.next()) { //���ڵ忡 �ִ� ������ ������ ���� �� ���
				String id=rs.getString("id"); //���� DB�� �÷� �̸� id
				String pwd=rs.getString("pwd"); //DB�� �÷����� �ٸ��� �ȵȴ�
				String name=rs.getString("name");
				String email=rs.getString("email");
				Date joinDate=rs.getDate("joinDate");
				MemberVO memberVO=new MemberVO(id, pwd, name, email, joinDate); //db�� ȭ�� �߰� ��Ȱ memberVO �ڵ� �ؾ���
				memberList.add(memberVO);
			}// ���Ϲ� �� ����� �� �ݾ���� �Ѵ�.
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("DB ��ȸ �� ����");
		}
		return memberList; //���Ϲ� �� ���� memberList�� ���� ���ش�.
	}
	//ȸ�� ���� �޼���
	public void addMember(MemberVO mem) {
		try {
			conn=dataFactory.getConnection();//db����
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
			System.out.println("DB �߰� �� ����");
		}
	}
	//�α��� ���� ���
	public MemberVO newMember(MemberVO memberVO) { //id�� �ܺηκ��� ���� �Ű����� ��
		MemberVO memberInfo=null;
		try {
			conn=dataFactory.getConnection();
			String query="select * from member_list where id=?";
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, memberVO.getId());
			System.out.println(query);
			ResultSet rs=pstmt.executeQuery();
			if (rs.next()) {
				String id=rs.getString(1); //id�� ã�� ������ ���� �÷����� ������ ��
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
			System.out.println("�α��� ����");
		}
		return memberInfo;
	}
	
	//������ ȸ������ ã��
	public MemberVO findMember(String _id) { //_id�� �ܺηκ��� ���� �Ű����� ��
		MemberVO memberInfo=null;
		try {
			conn=dataFactory.getConnection();
			String query="select * from member_list where id=?";
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, _id);
			System.out.println(query);
			ResultSet rs=pstmt.executeQuery();
			rs.next();
			String id=rs.getString("id"); //id�� ã�� ������ ���� �÷����� ������ ��
			String pwd=rs.getString("pwd");
			String name=rs.getString("name");
			String email=rs.getString("email");
			Date joinDate=rs.getDate("joinDate");
			memberInfo=new MemberVO(id, pwd, name, email, joinDate);
			pstmt.close();
			conn.close();
			rs.close();
		}catch (Exception e) {
			System.out.println("DB���� ȸ������ ã�� �� ����");
		}
		return memberInfo;
	}
	
	//ȸ�� ���� ����
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
			pstmt.setString(4, id); //where ���ǿ� �ִ� id���� ��й�ȣ,�̸�,�̸��� �����Ұž�
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("DB���� ȸ������ ���� �� ����");
		}
	}
	
	//ȸ�� ���� ����
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
			System.out.println("DB���� ȸ������ ���� �� ����");
		}
	}
}
