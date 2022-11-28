package homPageBoard.ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/download.do")
public class FileDownloadController extends HttpServlet {
	private static String ART_IMAGE_REPO="C:\\ch_jeon\\server\\workspace9\\upload_images"; //���ε��� ���� ���
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; chartset=utf-8");
		String imageFileName=request.getParameter("imageFileName");
		String articleNo=request.getParameter("articleNo");
		System.out.println("�̹����̸� : " + imageFileName);
		OutputStream outputS=response.getOutputStream();
		String path=ART_IMAGE_REPO + "\\" + articleNo + "\\" + imageFileName;
		File imageFile=new File(path);
		response.setHeader("Cache-Control", "no-cach");
		response.addHeader("Content-disposition", "attachement;fileName=" + imageFileName);
		FileInputStream fileIn=new FileInputStream(imageFile);
		byte[] buffer=new byte[1024*8];//�ѹ��� 8k���� ���ڰ� ������ Ƚ���� �������� ���ڰ� ������ �ӵ��� ����
		while(true) {
			int count=fileIn.read(buffer);
			if(count == -1) { //���̻� ������ ������ -1�� ����������
				break;
			}
			outputS.write(buffer,0,count);
		}
		fileIn.close();
		outputS.close();
	}

}
