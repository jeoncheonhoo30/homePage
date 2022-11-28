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
			Context ctx=new InitialContext(); //servers에서 context.xml를 이용해서
			Context envContext=(Context)ctx.lookup("java:/comp/env"); //자바와 연동
			dataFactory=(DataSource) envContext.lookup("jdbc/oracle"); //오라클을 사용하겠다 mysql도 사용가능 교육용 무료버전으로 오라클 사용증 sql은 무료
		}catch (Exception e) {
			System.out.println("DB 연결 중 에러");
		}
	}
	
	//해당 select와 해당 pageNum을 조건으로 한 페이지에 있는 목록 10개
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
			System.out.println("DB 페이징 처리 중 에러" + e.getMessage());
		}
		return articleList;
	}
	
	//전체 글 목록 수
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
			System.out.println("DB 전체 글 목록 처리 중 에러");
		}
		return 0;
	}
	
	//게시판 글 목록
	public List<ArticleVO> selectAllArticles() {
		List<ArticleVO> articleList=new ArrayList();
		try {
			conn=dataFactory.getConnection();
			String query="SELECT LEVEL, articleNo, parentNo, title, content, id, writeDate" + 
			" from board_rtbl START WITH parentNo=0 CONNECT BY PRIOR articleNo=parentNo" + 
			" ORDER SIBLINGS BY articleNO DESC"; //LEVEL은 오라클에서 가상의 컬럼
			System.out.println(query); //글쓴사람은 level1 댓글은 2 댓글의 댓글은 3 이런 방식
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
			System.out.println("DB 글 목록 조회 중 에러");
		}
		return articleList;
	}
	
	//글번호 생성메서드
	private int getNewArticleNo() {
		try {
			conn=dataFactory.getConnection();
			String query="SELECT max(articleNo) from board_rtbl";
			System.out.println(query);
			pstmt=conn.prepareStatement(query);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt(1)+1); //글번호 +1 해주어야 함
			}
		}catch (Exception e) {
			System.out.println("DB 번호 생성 중 에러");
		}
		return 0;
	}
	
	//글 쓰기 저장 메서드
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
			System.out.println("DB 글쓰기 저장 중 에러"+e.getMessage());
		}
		return articleNo;
	}
	
	//선택된 글 상세 자료
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
			System.out.println("글 상세 조회 중 에러" + e.getMessage());
		}
		return article;
	}
	
	//상세 글 수정하기
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
			System.out.println("상세 글 수정 중 에러");
		}
	}
	
	//삭제할 글 번호 리스트 ArrayList객체에 저장
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
			System.out.println("삭제할 글 번호 리스트 처리중 에러"+ e.getMessage());
		}
		return articleNoList;
	}
	
	//글 삭제 메서드
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
			System.out.println("삭제 처리중 에러" + e.getMessage());
		}
	}
}
