package org.koreait.diary.member;

/**
 * 로그인 회원 정보
 *
 */
public class Member {

    private long memNo;
    private String memId;
    private String memNm;
    private String mobile;

    public long getMemNo() {
        return memNo;
    }

    public void setMemNo(long memNo) {
        this.memNo = memNo;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getMemNm() {
        return memNm;
    }

    public void setMemNm(String memNm) {
        this.memNm = memNm;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memNo=" + memNo +
                ", memId='" + memId + '\'' +
                ", memNm='" + memNm + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
