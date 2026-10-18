package domain;

import java.io.File;
import java.util.Date;

public class CreateSaleParameter {
	public String title;
	public String description;
	public int status;
	public float price;
	public Date pubDate;
	public File file;

	public CreateSaleParameter() {
		super();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public void setFile(File file) {
		this.file = file;
	}
}