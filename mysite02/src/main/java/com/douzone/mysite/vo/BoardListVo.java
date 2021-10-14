package com.douzone.mysite.vo;

public class BoardListVo {
	private Long rownum;
	private Long no;
	private String title;
	private Long hit;
	private String regDate;
	private Long groupNo;
	private Long orderNo;
	private Long depth;
	private Long userNo;
	private String userName;
	
	
	
	public Long getRownum() {
		return rownum;
	}
	public void setRownum(Long rownum) {
		this.rownum = rownum;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Long getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(Long groupNo) {
		this.groupNo = groupNo;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getDepth() {
		return depth;
	}
	public void setDepth(Long depth) {
		this.depth = depth;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "BoardListVo [no=" + no + ", title=" + title + ", hit=" + hit + ", regDate=" + regDate + ", groupNo="
				+ groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", userNo=" + userNo + ", userName="
				+ userName + "]";
	}
	
	
}
