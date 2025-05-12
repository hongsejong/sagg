package sagg.test.kr.main.controller;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sagg.test.kr.main.dto.MatchDetail;
import sagg.test.kr.main.dto.MatchUser;
import sagg.test.kr.main.dto.RecentMatch;
import sagg.test.kr.main.dto.Trend;
import sagg.test.kr.main.dto.UserInfo;

@Controller
public class MainController {

    private static final String API_KEY = "live_563d65d5e8425e7a755230044913bf3be4b0add572edc736d5b55d11c89fba20efe8d04e6d233bd35cf2fabdeb93fb0d";

    @GetMapping("/")
    public String mainForward() {
        return "common/main";
    }

    @PostMapping("/search")
    public String searchForward(@RequestParam("nickname") String nickname, Model model) {
        try {
            // 1. OUID 조회
            String url = "https://open.api.nexon.com/suddenattack/v1/id?user_name=" + nickname;

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-nxopen-api-key", API_KEY);
            headers.set("accept", "application/json");

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String body = response.getBody();
            String ouid = body.split(":")[1].replace("\"", "").replace("}", "").trim();

            model.addAttribute("nickname", nickname);
            model.addAttribute("ouid", ouid);

            // 2. 사용자 기본 정보
            String userInfoUrl = "https://open.api.nexon.com/suddenattack/v1/user/basic?ouid=" + ouid;
            ResponseEntity<UserInfo> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, UserInfo.class);
            UserInfo userInfo = userInfoResponse.getBody();

            // 날짜 포맷 변경
            String createdDate = userInfo.getUser_date_create();
            if (createdDate != null && createdDate.contains("T")) {
                createdDate = createdDate.split("T")[0];
            }
            userInfo.setUser_date_create(createdDate);

            // 클랜 처리
            if (userInfo.getClan_name() == null || userInfo.getClan_name().trim().isEmpty()) {
                userInfo.setClan_name("없음");
            }

            model.addAttribute("user", userInfo);

            // 3. 최근 매치 목록 조회 (개인전 고정)
            String matchUrl = "https://open.api.nexon.com/suddenattack/v1/match?ouid=" + ouid + "&match_mode=개인전";
            ResponseEntity<RecentMatch> matchResponse = restTemplate.exchange(matchUrl, HttpMethod.GET, entity, RecentMatch.class);
            RecentMatch matchList = matchResponse.getBody();

            if (matchList != null && matchList.getMatch() != null) {
                List<MatchDetail> allMatches = matchList.getMatch();

                // 날짜 포맷 변경
                DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분 ss초");

                for (MatchDetail detail : allMatches) {
                    OffsetDateTime odt = OffsetDateTime.parse(detail.getDate_match(), inputFormatter);
                    String formatted = odt.format(outputFormatter);
                    detail.setDate_match(formatted);
                }

                List<MatchDetail> top10Matches = allMatches.stream().limit(10).collect(Collectors.toList());
                model.addAttribute("matches", top10Matches);
            }
            String trendUrl = "https://open.api.nexon.com/suddenattack/v1/user/recent-info?ouid=" + ouid;
            ResponseEntity<Trend> trendResponse = restTemplate.exchange(trendUrl, HttpMethod.GET, entity, Trend.class);
            Trend trendData = trendResponse.getBody();

            model.addAttribute("trend", trendData);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "닉네임 조회 실패: " + e.getMessage());
        }

        return "common/profile";
    }

    // 모드별 매치 목록 AJAX 요청 처리
    @GetMapping("/search-mode")
    @ResponseBody
    public List<MatchDetail> searchByMode(
        @RequestParam("nickname") String nickname,
        @RequestParam("mode") String mode
    ) {
        try {
            // OUID 조회
            String url = "https://open.api.nexon.com/suddenattack/v1/id?user_name=" + nickname;

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-nxopen-api-key", API_KEY);
            headers.set("accept", "application/json");

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String ouid = response.getBody().split(":")[1].replace("\"", "").replace("}", "").trim();

            // 모드에 따라 매치 조회
            String matchUrl = "https://open.api.nexon.com/suddenattack/v1/match?ouid=" + ouid + "&match_mode=" + mode;
            ResponseEntity<RecentMatch> matchResponse = restTemplate.exchange(matchUrl, HttpMethod.GET, entity, RecentMatch.class);
            List<MatchDetail> allMatches = matchResponse.getBody().getMatch();

            // 날짜 포맷
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH시 mm분 ss초");

            for (MatchDetail detail : allMatches) {
                OffsetDateTime odt = OffsetDateTime.parse(detail.getDate_match(), inputFormatter);
                detail.setDate_match(odt.format(outputFormatter));
            }

            return allMatches.stream().limit(10).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    
    
    @GetMapping("/match-detail")
    @ResponseBody
    public List<MatchUser> getMatchDetail(@RequestParam("matchId") String matchId) {
        List<MatchUser> matchUsers = new ArrayList<>();

        try {
            String url = "https://open.api.nexon.com/suddenattack/v1/match-detail?match_id=" + matchId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-nxopen-api-key", API_KEY);
            headers.set("accept", "application/json");

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode root = response.getBody();

            JsonNode matchDetailNode = root.get("match_detail");
            if (matchDetailNode != null && matchDetailNode.isArray()) {
                ObjectMapper mapper = new ObjectMapper();
                matchUsers = mapper.readValue(matchDetailNode.toString(), new TypeReference<List<MatchUser>>() {});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return matchUsers;
    }

}
