package com.thingword.alphonso.bean.db;

public class VideoConfigure {
	private int ID;
	private String invcode;
	private String w1;
	private String w2;
	private String w3;
	private String w4;
	private String w5;
	private String w6;
	private String w7;
	private String w8;
	private String w9;
	private String w10;
	private String w11;
	private String w12;
	private String w13;
	private String w14;
	private String w15;
	private String w16;
	private String w17;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getInvcode() {
		return invcode;
	}
	public void setInvcode(String invcode) {
		this.invcode = invcode;
	}
	public String getW1() {
		return w1;
	}
	public void setW1(String w1) {
		this.w1 = w1;
	}
	public String getW2() {
		return w2;
	}
	public void setW2(String w2) {
		this.w2 = w2;
	}
	public String getW3() {
		return w3;
	}
	public void setW3(String w3) {
		this.w3 = w3;
	}
	public String getW4() {
		return w4;
	}
	public void setW4(String w4) {
		this.w4 = w4;
	}
	public String getW5() {
		return w5;
	}
	public void setW5(String w5) {
		this.w5 = w5;
	}
	public String getW6() {
		return w6;
	}
	public void setW6(String w6) {
		this.w6 = w6;
	}
	public String getW7() {
		return w7;
	}
	public void setW7(String w7) {
		this.w7 = w7;
	}
	public String getW8() {
		return w8;
	}
	public void setW8(String w8) {
		this.w8 = w8;
	}
	public String getW9() {
		return w9;
	}
	public void setW9(String w9) {
		this.w9 = w9;
	}
	public String getW10() {
		return w10;
	}
	public void setW10(String w10) {
		this.w10 = w10;
	}
	public String getW11() {
		return w11;
	}
	public void setW11(String w11) {
		this.w11 = w11;
	}
	public String getW13() {
		return w13;
	}
	public void setW13(String w13) {
		this.w13 = w13;
	}
	public String getW12() {
		return w12;
	}
	public void setW12(String w12) {
		this.w12 = w12;
	}
	public String getW14() {
		return w14;
	}
	public void setW14(String w14) {
		this.w14 = w14;
	}
	public String getW15() {
		return w15;
	}
	public void setW15(String w15) {
		this.w15 = w15;
	}
	public String getW16() {
		return w16;
	}
	public void setW16(String w16) {
		this.w16 = w16;
	}
	public String getW17() {
		return w17;
	}
	public void setW17(String w17) {
		this.w17 = w17;
	}
	public VideoConfigure reducePath(){
		w1 = w1==null?null:w1.substring(w1.lastIndexOf("\\")+1,w1.length()-5);
		w2 = w2==null?null:w2.substring(w2.lastIndexOf("\\")+1,w2.length()-5);
		w3 = w3==null?null:w3.substring(w3.lastIndexOf("\\")+1,w3.length()-5);
		w4 = w4==null?null:w4.substring(w4.lastIndexOf("\\")+1,w4.length()-5);
		w5 = w5==null?null:w5.substring(w5.lastIndexOf("\\")+1,w5.length()-5);
		w6 = w6==null?null:w6.substring(w6.lastIndexOf("\\")+1,w6.length()-5);
		w7 = w7==null?null:w7.substring(w7.lastIndexOf("\\")+1,w7.length()-5);
		
		w8 = w8==null?null:w8.substring(w8.lastIndexOf("\\")+1,w8.length()-5);
		w9 = w9==null?null:w9.substring(w9.lastIndexOf("\\")+1,w9.length()-5);
		w10 = w10==null?null:w10.substring(w10.lastIndexOf("\\")+1,w10.length()-5);
		w11 = w11==null?null:w11.substring(w11.lastIndexOf("\\")+1,w11.length()-5);
		w12 = w12==null?null:w12.substring(w12.lastIndexOf("\\")+1,w12.length()-5);
		w13 = w13==null?null:w13.substring(w13.lastIndexOf("\\")+1,w13.length()-5);
		w14 = w14==null?null:w14.substring(w14.lastIndexOf("\\")+1,w14.length()-5);
		
		w15 = w15==null?null:w15.substring(w15.lastIndexOf("\\")+1,w15.length()-5);
		w16 = w16==null?null:w16.substring(w16.lastIndexOf("\\")+1,w16.length()-5);
		w17 = w17==null?null:w17.substring(w17.lastIndexOf("\\")+1,w17.length()-5);
		
//		w1 = w1==null?null:w1.substring(w1.lastIndexOf("\\")+1);
//		w2 = w2==null?null:w2.substring(w2.lastIndexOf("\\")+1);
//		w3 = w3==null?null:w3.substring(w3.lastIndexOf("\\")+1);
//		w4 = w4==null?null:w4.substring(w4.lastIndexOf("\\")+1);
//		w5 = w5==null?null:w5.substring(w5.lastIndexOf("\\")+1);
//		w6 = w6==null?null:w6.substring(w6.lastIndexOf("\\")+1);
//		w7 = w7==null?null:w7.substring(w7.lastIndexOf("\\")+1);
//		
//		w8 = w8==null?null:w8.substring(w8.lastIndexOf("\\")+1);
//		w9 = w9==null?null:w9.substring(w9.lastIndexOf("\\")+1);
//		w10 = w10==null?null:w10.substring(w10.lastIndexOf("\\")+1);
//		w11 = w11==null?null:w11.substring(w11.lastIndexOf("\\")+1);
//		w12 = w12==null?null:w12.substring(w12.lastIndexOf("\\")+1);
//		w13 = w13==null?null:w13.substring(w13.lastIndexOf("\\")+1);
//		w14 = w14==null?null:w14.substring(w14.lastIndexOf("\\")+1);
//		
//		w15 = w15==null?null:w15.substring(w15.lastIndexOf("\\")+1);
//		w16 = w16==null?null:w16.substring(w16.lastIndexOf("\\")+1);
//		w17 = w17==null?null:w17.substring(w17.lastIndexOf("\\")+1);
		return this;
	}
}
