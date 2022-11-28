package homPageBoard.ex02;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;

public class ArticleVO {
	private int level; //������ ����
	private int articleNo; //�۹�ȣ
	private int parentNo; //�θ� �۹�ȣ
	private String title;
	private String content;
	private String imageFileName; //���ε��� �̹��� �̸�
	private String id;
	private Date writeDate;
	
	public ArticleVO() {
		
	}

	public ArticleVO(int level, int articleNo, int parentNo, String title, String content, String imageFileName,
			String id) {
		super();
		this.level = level;
		this.articleNo = articleNo;
		this.parentNo = parentNo;
		this.title = title;
		this.content = content;
		this.imageFileName = imageFileName;
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public int getParentNo() {
		return parentNo;
	}

	public void setParentNo(int parentNo) {
		this.parentNo = parentNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageFileName() {
		try {
			if(imageFileName != null && imageFileName.length() != 0 ) {
				imageFileName=URLDecoder.decode(imageFileName,"utf-8");
			}
		}catch (UnsupportedEncodingException e) {
			System.out.println("�̹��� �����̸� �ҷ����� �� ����");
		}
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		try {
			if(imageFileName != null && imageFileName.length() != 0 ) {
				this.imageFileName = URLEncoder.encode(imageFileName,"utf-8");
			}else {
				this.imageFileName=null;
			}
		}catch (UnsupportedEncodingException e) {
			System.out.println("�̹��� �����̸� ���� �� ����");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	
	
}
