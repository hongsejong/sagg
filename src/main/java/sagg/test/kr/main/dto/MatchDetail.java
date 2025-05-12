package sagg.test.kr.main.dto;


public class MatchDetail {
    private String match_id;
    private String match_type;
    private String match_mode;
    private String date_match;

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public String getMatch_mode() {
        return match_mode;
    }

    public void setMatch_mode(String match_mode) {
        this.match_mode = match_mode;
    }

    public String getDate_match() {
        return date_match;
    }

    public void setDate_match(String date_match) {
        this.date_match = date_match;
    }
}