package homPageBoard.ex02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService { //중간역활 
	BoardDAO boardDAO;
	
	public BoardService() {
		boardDAO=new BoardDAO();
	}
	
	public Map listArticles(Map<String, Integer> pagingMap) {//map을 돌려받는 메서드
		Map articleMap=new HashMap();
		List<ArticleVO> articleList=boardDAO.selectAllArticles(pagingMap);
		int totArticles=boardDAO.selectTotArticles();
		articleMap.put("articleList", articleList);
		//articleMap.put("totArticles", totArticles);
		articleMap.put("totArticles", 155);
		return articleMap;
	}
	
	public List<ArticleVO> listArticles() { //리스트를 돌려받는 메서드
		List<ArticleVO> articleList=boardDAO.selectAllArticles();
		return articleList;
	}
	
	public int addArticle(ArticleVO article) {
		return boardDAO.insertNewArticle(article);
	}
	
	public ArticleVO viewArticle(int articleNo) {
		ArticleVO article=null;
		article=boardDAO.selectArticle(articleNo);
		return article;
	}
	
	public void modArticle(ArticleVO article) {
		boardDAO.updateArticle(article);
	}
	
	public List<Integer> removeArticle(int articleNo) {
		List<Integer> articleNoList=boardDAO.selectRemoveArticles(articleNo);
		boardDAO.deleteArticle(articleNo);
		return articleNoList;
	}
	
	public int addReply(ArticleVO article) {
		return boardDAO.insertNewArticle(article);
	}
}

