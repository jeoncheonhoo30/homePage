package homePage.ex01;

import java.sql.Date;

public class MemberVO {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date joinDate;
	
	public MemberVO() {
		System.out.println("MemberVO 생성자 호출");
	}

	public MemberVO(String id, String pwd, String name, String email) {
		super(); //위에 Source에서 constructor using에서 빠르게 생성 가능
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
	}
	//가입 할때는 가입일을 적지 않으니 정보 볼때 쓰기위해 생성자를 하나더 생성한다 joinDate때문에
	public MemberVO(String id, String pwd, String name, String email, Date joinDate) {
		super(); //위에 Source에서 constructor using에서 빠르게 생성 가능
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.joinDate=joinDate;
	}
	
	//private 접근 제한자로 줬을때 source에서 getter와 setter로 빠르게 생성 가능
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
