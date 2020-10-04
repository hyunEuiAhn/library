package Client;

public class BookDTO {
 private String type, bookName, exist, returndate,rentID,bookAuth, bookDis,bookPub,bookPic;

 	public String getBookPic() {
 		return bookPic;
 	}
 	
 	public void setBookPic(String bookPic) {
 		this.bookPic = bookPic;
 	}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }
    
    
    public String getRentid() {
    	return rentID;
    }
    
    public void setRentid(String rentID) {
    	this.rentID=rentID;
    }
    
    public String getbookAuth() {
    	return bookAuth;
    }
    
    public void setbookAuth(String bookAuth) {
    	this.bookAuth = bookAuth;
    }
    
    public String getbookDis() {
    	return bookDis;
    }
    
    public void setbookDis(String bookDis) {
    	this.bookDis = bookDis;
    	bookDis = "이것은 책 설명 샘플입니다";
    }
    
    public void setbookPub(String bookPub) {
    	this.bookPub = bookPub;
    }
    public String getbookPub() {
    	return bookPub;
    }
    
}










