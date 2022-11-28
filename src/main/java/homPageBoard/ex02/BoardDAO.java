package homPageBoard.ex02;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	private DataSource dataFactory;
	Connection conn;
	PreparedStatement pstmt;
	
	public BoardDAO() {
		try {
			Context ctx=new InitialContext(); //servers���� context.xml�� �̿��ؼ�
			Context envContext=(Context)ctx.lookup("java:/comp/env"); //�ڹٿ� ����
			dataFactory=(DataSource) envContext.lookup("jdbc/oracle"); //����Ŭ�� ����ϰڴ� mysql�� ��밡�� ������ ����������� ����Ŭ ����� sql�� ����
		}catch (Exception e) {
			System.out.println("DB ���� �� ����");
		}
	}
	
	//�ش� select�� �ش� pageNum�� �������� �� �������� �ִ� ��� 10��
	public List selectAllArticles(Map pagingMap) {
		List articleList = new ArrayList();
		int section=(Integer)pagingMap.get("section");
		int pageNum=(Integer)pagingMap.get("pageNum");
		try {
			conn=dataFactory.getConnection();
			String query="SELECT * FROM (SELECT ROWNUM AS recNum, LVL, articleNo," +
			"parentNo, title, id, writeDate FROM (SELECT LEVEL AS LVL, articleNo," +
			"parentNo, title, id, writeDate FROM board_rtbl START WITH parentNo=0" +
			" CONNECT BY PRIOR articleNo=parentNo ORDER SIBLINGS BY articleNo DESC))" +
			"where recNum between (?-1)*100 + (?-1)*10+1 and (?-1)*100+?*10";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, section);
			pstmt.setInt(2, pageNum);
			pstmt.setInt(3, section);
			pstmt.setInt(4, pageNum);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				int level=rs.getInt("LVL");
				int articleNo=rs.getInt("articleNo");
				int parentNo=rs.getInt("parentNo");
				String title=rs.getString("title");
				String id=rs.getString("id");
				Date writeDate=rs.getDate("writeDate");
				ArticleVO article=new ArticleVO();
				article.setLevel(level);
				article.setArticleNo(articleNo);
				article.setParentNo(parentNo);
				article.setTitle(title);
				article.setId(id);
				article.setWriteDate(writeDate);
				articleList.add(article);
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB ����¡ ó�� �� ����" + e.getMessage());
		}
		return articleList;
	}
	
	//��ü �� ��� ��
	public int selectTotArticles() {
		try {
			conn=dataFactory.getConnection();
			String query="select count(articleNo) from board_rtbl";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("DB ��ü �� ��� ó�� �� ����");
		}
		return 0;
	}
	
	//�Խ��� �� ���
	public List<ArticleVO> selectAllArticles() {
		List<ArticleVO> articleList=new ArrayList();
		try {
			conn=dataFactory.getConnection();
			String query="SELECT LEVEL, articleNo, parentNo, title, content, id, writeDate" + 
			" from board_rtbl START WITH parentNo=0 CONNECT BY PRIOR articleNo=parentNo" + 
			" ORDER SIBLINGS BY articleNO DESC"; //LEVEL�� ����Ŭ���� ������ �÷�
			System.out.println(query); //�۾������ level1 ����� 2 ����� ����� 3 �̷� ���
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) { 
				int level=rs.getInt("level");
				int articleNo=rs.getInt("articleNO");
				int parentNo=rs.getInt("parentNo");
				String title=rs.getString("title");
				String content=rs.getString("content");
				String id=rs.getString("id");
				Date writeDate=rs.getDate("writeDate");
				ArticleVO article=new ArticleVO();
				article.setLevel(level);
				article.setArticleNo(articleNo);
				article.setParentNo(parentNo);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWriteDate(writeDate);
				articleList.add(article);
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("DB �� ��� ��ȸ �� ����");
		}
		return articleList;
	}
	
	//�۹�ȣ �����޼���
	private int getNewArticleNo() {
		try {
			conn=dataFactory.getConnection();
			String query="SELECT max(articleNo) from board_rtbl";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt(1)+1); //�۹�ȣ +1 ���־�� ��
			}
		}catch (Exception e) {
			System.out.println("DB ��ȣ ���� �� ����");
		}
		return 0;
	}
	
	//�� ���� ���� �޼���
	public int insertNewArticle(ArticleVO article) {
		int articleNo=getNewArticleNo();
		try {
			conn=dataFactory.getConnection();			
			int parentNo=article.getParentNo();
			String title=article.getTitle();
			String content=article.getContent();
			String id=article.getId();
			String imageFileName=article.getImageFileName();
			String query="INSERT INTO board_rtbl(articleNo, parentNo, title, content," +
			"imageFileName, id) values(?,?,?,?,?,?)";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articleNo);
			pstmt.setInt(2, parentNo);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFileName);
			pstmt.setString(6, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB �۾��� ���� �� ����"+e.getMessage());
		}
		return articleNo;
	}
	
	//���õ� �� �� �ڷ�
	public ArticleVO selectArticle(int articleNo) {
		ArticleVO article=new ArticleVO();
		try {
			conn=dataFactory.getConnection();
			String query="select articleNo, parentNo, title, content, " +
			"NVL(imageFileName, 'null') as imageFileName, id, writeDate" + 
			" from board_rtbl where articleNo=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articleNo);
			ResultSet rs=pstmt.executeQuery();
			rs.next();
			int _articleNo=rs.getInt("articleNo");
			int parentNo=rs.getInt("parentNo");
			String title=rs.getString("title");
			String content=rs.getString("content");
			String imageFileName=URLEncoder.encode(rs.getString("imageFileName"),"utf-8");
			if(imageFileName.equals("null")) {
				imageFileName=null;
			}
			String id=rs.getString("id");
			Date writeDate=rs.getDate("writeDate");
			article.setArticleNo(_articleNo);
			article.setParentNo(parentNo);
			article.setTitle(title);
			article.setContent(content);
			article.setImageFileName(imageFileName);
			article.setId(id);
			article.setWriteDate(writeDate);
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("�� �� ��ȸ �� ����" + e.getMessage());
		}
		return article;
	}
	
	//�� �� �����ϱ�
	public void updateArticle(ArticleVO article) {
		int articleNo=article.getArticleNo();
		String title=article.getTitle();
		String content=article.getContent();
		String imageFileName=article.getImageFileName();
		try {
			conn=dataFactory.getConnection();
			String query="update board_rtbl set title=?, content=?";
			if(imageFileName != null && imageFileName.length() != 0) {
				query+=", imageFileName=?";
			}
			query+=" where articleNo=?";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			if(imageFileName != null && imageFileName.length() != 0) {
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, articleNo);
			}else {
				pstmt.setInt(3, articleNo);
			}
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			System.out.println("�� �� ���� �� ����");
		}
	}
	
	//������ �� ��ȣ ����Ʈ ArrayList��ü�� ����
	public List<Integer> selectRemoveArticles(int articleNo) {
		List<Integer> articleNoList=new ArrayList<Integer>();
		try { 
			conn=dataFactory.getConnection();
			String query="SELECT articleNo FROM board_rtbl START WITH articleNo=?" + 
			" CONNECT BY PRIOP articleNo=parentNo";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articleNo);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				articleNo=rs.getInt("articleNo");
				articleNoList.add(articleNo);
			}
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("������ �� ��ȣ ����Ʈ ó���� ����"+ e.getMessage());
		}
		return articleNoList;
	}
	
	//�� ���� �޼���
	public void deleteArticle(int articleNo) {
		try {
			conn=dataFactory.getConnection();
			String query="DELETE FROM board_rtbl WHERE articleNo in (SELECT articleNo" + 
			" FROM board_rtbl START WITH articleNo=? CONNECT BY PRIOR articleNo=parentNo)";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, articleNo);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("���� ó���� ����" + e.getMessage());
		}
	}
}
