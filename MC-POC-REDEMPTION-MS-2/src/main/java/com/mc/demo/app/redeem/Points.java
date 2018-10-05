package com.mc.demo.app.redeem;


public class Points {
	
	private double pointsvalue ;
	private String opertaion;
	private String message;
	private String totalPoints;
	private String cardNumber;
	
	/**
	 * @return the pointsvalue
	 */
	public double getPointsvalue() {
		return pointsvalue;
	}
	/**
	 * @param pointsvalue the pointsvalue to set
	 */
	public void setPointsvalue(double pointsvalue) {
		this.pointsvalue = pointsvalue;
	}
	/**
	 * @return the opertaion
	 */
	public String getOpertaion() {
		return opertaion;
	}
	/**
	 * @param opertaion the opertaion to set
	 */
	public void setOpertaion(String opertaion) {
		this.opertaion = opertaion;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(String totalPoints) {
		this.totalPoints = totalPoints;
	}
	

}
