package homePage.ex01;

import java.sql.Date;

public class MemberVO {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date joinDate;
	
	public MemberVO() {
		System.out.println("MemberVO ������ ȣ��");
	}

	public MemberVO(String id, String pwd, String name, String email) {
		super(); //���� Source���� constructor using���� ������ ���� ����
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
	}
	//���� �Ҷ��� �������� ���� ������ ���� ���� �������� �����ڸ� �ϳ��� �����Ѵ� joinDate������
	public MemberVO(String id, String pwd, String name, String email, Date joinDate) {
		super(); //���� Source���� constructor using���� ������ ���� ����
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.joinDate=joinDate;
	}
	
	//private ���� �����ڷ� ������ source���� getter�� setter�� ������ ���� ����
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	
}