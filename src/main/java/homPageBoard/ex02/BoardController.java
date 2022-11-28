package homPageBoard.ex02;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;


@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static String ART_IMAGE_REPO="C:\\ch_jeon\\server\\workspace9\\upload_images"; //���ε��� ���� ���
	HttpSession session;
	BoardService boardService;
	ArticleVO articleVO;
	
	public void init(ServletConfig config) throws ServletException {
		boardService=new BoardService();
		articleVO=new ArticleVO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage="";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action=request.getPathInfo(); //��û���� �����ɴϴ�.
		System.out.println("��û�� : "+action);
		List<ArticleVO> articleList=new ArrayList<ArticleVO>();
		if(action == null || action.equals("/listArticles.do")) { //�Խ��� �� ��� ����
			String _section=request.getParameter("section");
			String _pageNum=request.getParameter("pageNum");
			int section=Integer.parseInt((_section==null?"1":_section));//���׿����� ����?����1:����2 ���ǿ� ������ ����1 �ƴϸ� ����2
			int pageNum=Integer.parseInt((_pageNum==null?"1":_pageNum));
			Map<String, Integer> pagingMap=new HashMap<String, Integer>();
			pagingMap.put("section", section);
			pagingMap.put("pageNum", pageNum);
			Map articleMap=boardService.listArticles(pagingMap);
			articleMap.put("section", section);
			articleMap.put("pageNum", pageNum);
			request.setAttribute("articleMap", articleMap);
			nextPage="/boardView/listArticles.jsp";
		}else if(action.equals("/articleForm.do")) {
			nextPage="/boardView/articleForm.jsp";
		}else if(action.equals("/addArticle.do")) {
			int articleNo=0;
			Map<String, String> articleMap=upload(request, response);
			HttpSession session= request.getSession();
			String sessionID= (String)session.getAttribute("sessionID");
			String title=articleMap.get("title");
			String content=articleMap.get("content");
			String imageFileName=articleMap.get("imageFileName");
			articleVO.setParentNo(0);
			articleVO.setId(sessionID);
			articleVO.setTitle(title);
			articleVO.setContent(content);
			articleVO.setImageFileName(imageFileName);
			System.out.println("�̹��� ? : " + imageFileName);
			articleNo=boardService.addArticle(articleVO);
			if(imageFileName != null && imageFileName.length() != 0) {
				File srcFile=new File(ART_IMAGE_REPO+"\\temp\\" + imageFileName);
				File destDir=new File(ART_IMAGE_REPO+"\\"+articleNo);
				destDir.mkdir();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
			}
			PrintWriter pw=response.getWriter();
			pw.print("<script>" + "alert('������ �߰��߽��ϴ�.');" + 
			"location.href='" + request.getContextPath() + "/board/listArticles.do';" + 
			"</script>");
			return;
		}else if(action.equals("/viewArticle.do")) {
			String articleNo=request.getParameter("articleNo");
			articleVO=boardService.viewArticle(Integer.parseInt(articleNo));
			request.setAttribute("article", articleVO);
			nextPage="/boardView/viewArticle.jsp";
		}else if(action.equals("/modArticle.do")) {
			Map<String, String> articleMap=upload(request, response);
			int articleNo=Integer.parseInt(articleMap.get("articleNo"));
			articleVO.setArticleNo(articleNo); //�ٲ� ���� ����
			String title=articleMap.get("title");
			String content=articleMap.get("content");
			String imageFileName=articleMap.get("imageFileName");
			articleVO.setTitle(title);
			articleVO.setContent(content);
			articleVO.setImageFileName(imageFileName);
			boardService.modArticle(articleVO); //���弭�񽺿��� ����ƼŬ �޼���� update�ϰ��
			if(imageFileName != null && imageFileName.length() != 0) {
				String originalFileName=articleMap.get("originalFileName");
				File srcFile=new File(ART_IMAGE_REPO+"\\temp\\"+imageFileName);
				File destDir=new File(ART_IMAGE_REPO+"\\"+articleNo);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				File oldFile=new File(ART_IMAGE_REPO+"\\"+articleNo+"\\"+ originalFileName);
				oldFile.delete();
			}
			PrintWriter pw=response.getWriter();
			pw.print("<script>" + "alert('���� �����߽��ϴ�.');" + 
			"location.href='" + request.getContextPath() + "/board/viewArticle.do?" +
			"articleNo=" + articleNo + "';</script>");
			return;
		}else if(action.equals("/removeArticle.do")) {
			int articleNo=Integer.parseInt(request.getParameter("articleNo"));
			List<Integer> articleNoList=boardService.removeArticle(articleNo);
			for(int ano:articleNoList) {
				File imgDir=new File(ART_IMAGE_REPO + "\\" + ano);
				if(imgDir.exists()) {
					FileUtils.deleteDirectory(imgDir);
				}
			}
			PrintWriter pw=response.getWriter();
			pw.print("<script>" + "alert('���� �����߽��ϴ�.');" + 
			"location.href='" + request.getContextPath() + "/board/listArticles.do';" + 
			"</script>");
			return;
		}else if(action.equals("/replyForm.do")) {
			int parentNo=Integer.parseInt(request.getParameter("parentNo"));
			session=request.getSession();
			session.setAttribute("parentNo", parentNo);
			nextPage="/boardView/replyForm.jsp";
		}else if(action.equals("/addReply.do")) {
			session=request.getSession();
			int parentNo=(Integer)session.getAttribute("parentNo");
			session.removeAttribute("parentNo");
			HttpSession session1= request.getSession();
			String sessionID= (String)session1.getAttribute("sessionID");
			Map<String, String> articleMap=upload(request, response);
			String title=articleMap.get("title");
			String content=articleMap.get("content");
			String imageFileName=articleMap.get("imageFileName");
			articleVO.setParentNo(parentNo);
			articleVO.setId(sessionID);
			articleVO.setTitle(title);
			articleVO.setContent(content);
			articleVO.setImageFileName(imageFileName); //�̹����� ÷������ ������ �� ���� ��
			int articleNo=boardService.addReply(articleVO);
			if(imageFileName != null && imageFileName.length() != 0) {
				File srcFile=new File(ART_IMAGE_REPO + "\\temp\\" + imageFileName);
				File destDir=new File(ART_IMAGE_REPO + "\\" + articleNo);
				destDir.mkdir();
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
			}
			PrintWriter pw=response.getWriter();
			pw.print("<script>" + "alert('����� �߰��߽��ϴ�.');" + 
			"location.href='" + request.getContextPath() + "/board/viewArticle.do?" +
			"articleNo=" + articleNo + "';</script>");
			return;
		}
		RequestDispatcher dispatcher=request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

	//�۾������ �ڷ�� �̹��� ���ε�ó�� �޼���
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> articleMap=new HashMap<String, String>();
		String encoding="utf-8";
		File currentDirPath=new File(ART_IMAGE_REPO);//���ε��� ������ ���
		DiskFileItemFactory factory=new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024*1024);
		ServletFileUpload upload=new ServletFileUpload(factory);
		try {
			List items=upload.parseRequest(request);
			for(int i=0; i<items.size(); i++) {
				FileItem fileItem=(FileItem)items.get(i);
				if(fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName() + " : " + fileItem.getString(encoding));
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				}else {
					System.out.println("�Ķ���� �̸� : " + fileItem.getFieldName());
					System.out.println("�����̸� : " + fileItem.getName());
					System.out.println("����ũ�� : " + fileItem.getSize() + "bytes");
					if(fileItem.getSize() > 0) {
						int idx=fileItem.getName().lastIndexOf("\\");
						if(idx == -1) {
							idx=fileItem.getName().lastIndexOf("/");
						}
						String fileName=fileItem.getName().substring(idx+1);
						articleMap.put(fileItem.getFieldName(), fileName);
						File uploadFile=new File(currentDirPath + "\\temp\\" + fileName);
						fileItem.write(uploadFile); //���� ���ε�
					}
				}
			}
		}catch (Exception e) {
			System.out.println("���ε� ��� ó�� �� ����");
		}
		return articleMap;
	}
}
