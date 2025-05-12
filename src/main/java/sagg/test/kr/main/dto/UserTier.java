package sagg.test.kr.main.dto;

public class UserTier {
	 private String userName;                 // 닉네임
	    private int soloRankMatchTier;           // 솔로 랭크 매치 티어
	    private int soloRankMatchScore;          // 솔로 랭크 점수
	    private int partyRankMatchTier;          // 파티 랭크 매치 티어
	    private int partyRankMatchScore;         // 파티 랭크 점수

	    // Getter / Setter
	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public int getSoloRankMatchTier() {
	        return soloRankMatchTier;
	    }

	    public void setSoloRankMatchTier(int soloRankMatchTier) {
	        this.soloRankMatchTier = soloRankMatchTier;
	    }

	    public int getSoloRankMatchScore() {
	        return soloRankMatchScore;
	    }

	    public void setSoloRankMatchScore(int soloRankMatchScore) {
	        this.soloRankMatchScore = soloRankMatchScore;
	    }

	    public int getPartyRankMatchTier() {
	        return partyRankMatchTier;
	    }

	    public void setPartyRankMatchTier(int partyRankMatchTier) {
	        this.partyRankMatchTier = partyRankMatchTier;
	    }

	    public int getPartyRankMatchScore() {
	        return partyRankMatchScore;
	    }

	    public void setPartyRankMatchScore(int partyRankMatchScore) {
	        this.partyRankMatchScore = partyRankMatchScore;
	    }
}
