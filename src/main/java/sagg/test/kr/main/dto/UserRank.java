package sagg.test.kr.main.dto;

public class UserRank {
	 private String userName;              // 닉네임
	    private String grade;                 // 통합 계급
	    private long gradeExp;               // 통합 계급 경험치
	    private long gradeRanking;           // 통합 계급 랭킹
	    private String seasonGrade;          // 시즌 계급
	    private long seasonGradeExp;         // 시즌 계급 경험치
	    private long seasonGradeRanking;     // 시즌 계급 랭킹

	    // Getter / Setter
	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public String getGrade() {
	        return grade;
	    }

	    public void setGrade(String grade) {
	        this.grade = grade;
	    }

	    public long getGradeExp() {
	        return gradeExp;
	    }

	    public void setGradeExp(long gradeExp) {
	        this.gradeExp = gradeExp;
	    }

	    public long getGradeRanking() {
	        return gradeRanking;
	    }

	    public void setGradeRanking(long gradeRanking) {
	        this.gradeRanking = gradeRanking;
	    }

	    public String getSeasonGrade() {
	        return seasonGrade;
	    }

	    public void setSeasonGrade(String seasonGrade) {
	        this.seasonGrade = seasonGrade;
	    }

	    public long getSeasonGradeExp() {
	        return seasonGradeExp;
	    }

	    public void setSeasonGradeExp(long seasonGradeExp) {
	        this.seasonGradeExp = seasonGradeExp;
	    }

	    public long getSeasonGradeRanking() {
	        return seasonGradeRanking;
	    }

	    public void setSeasonGradeRanking(long seasonGradeRanking) {
	        this.seasonGradeRanking = seasonGradeRanking;
	    }
}
