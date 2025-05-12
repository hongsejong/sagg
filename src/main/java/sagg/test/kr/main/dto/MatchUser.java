package sagg.test.kr.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchUser {
    @JsonProperty("team_id")
    private String teamId;

    @JsonProperty("match_result")
    private String matchResult;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("season_grade")
    private String seasonGrade;

    @JsonProperty("guild_name")
    private String clanName;

    @JsonProperty("kill")
    private long kill;

    @JsonProperty("death")
    private long death;

    @JsonProperty("headshot")
    private long headshot;

    @JsonProperty("damage")
    private double damage;

    @JsonProperty("assist")
    private long assist;

    // Getter / Setter
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSeasonGrade() {
        return seasonGrade;
    }

    public void setSeasonGrade(String seasonGrade) {
        this.seasonGrade = seasonGrade;
    }

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

    public long getKill() {
        return kill;
    }

    public void setKill(long kill) {
        this.kill = kill;
    }

    public long getDeath() {
        return death;
    }

    public void setDeath(long death) {
        this.death = death;
    }

    public long getHeadshot() {
        return headshot;
    }

    public void setHeadshot(long headshot) {
        this.headshot = headshot;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public long getAssist() {
        return assist;
    }

    public void setAssist(long assist) {
        this.assist = assist;
    }
}
