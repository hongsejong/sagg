<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>전적 상세 보기 - SuddenStats</title>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/profile.css">
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<header>
  <h1>SuddenStats</h1>
</header>

<main>
  <div class="summary">
    <div>
 
      <h2>${nickname}</h2>
      <p>칭호: ${user.title_name}</p>
      <p>클랜: ${user.clan_name}</p>
      <p>캐릭터 생성일: ${user.user_date_create}</p>
      <p>매너등급: ${user.manner_grade}</p>
    </div>
    <div>
      <h2>최근동향</h2>
      <p>승률: <fmt:formatNumber value="${trend.recent_win_rate }" type="number" maxFractionDigits="2" />%</p>
      <p>킬데스: <fmt:formatNumber value="${trend.recent_kill_death_rate}" type="number" maxFractionDigits="2" />%</p>
      <p>돌격: <fmt:formatNumber value="${trend.recent_assault_rate }" type="number" maxFractionDigits="2" />%</p>
      <p>저격: <fmt:formatNumber value="${trend.recent_sniper_rate }" type="number" maxFractionDigits="2" />%</p>
      <p>특수: <fmt:formatNumber value="${trend.recent_special_rate }" type="number" maxFractionDigits="2" />%</p>
    </div>
    
    <div>
      <!-- <h2>플레이 정보</h2> -->
      <canvas id="trendRadar" width="400" height="300"></canvas>

    </div>
  </div>
  <div class="match-tabs">
    <button class="tab-btn" data-mode="개인전">개인전</button>
    <button class="tab-btn" data-mode="데스매치">데스매치</button>
    <button class="tab-btn" data-mode="폭파미션">폭파미션</button>
    <button class="tab-btn" data-mode="진짜를모아라">진짜를모아라</button>
  </div>
  <div class="match-history">
<c:forEach var="match" items="${matches}" varStatus="status">
    <div class="match-box">
        <p>매치 타입: ${match.match_type} / 날짜: ${match.date_match} / 매치 모드 : ${match.match_mode}         <button class="detail-btn" data-match-id="${match.match_id}">상세보기</button></p>

        <div class="detail-result" id="detail-${status.index}"></div>
    </div>
</c:forEach>
  </div>
</main>

<footer>
  © 2025 SuddenStats.
</footer>

<script src="${pageContext.request.contextPath}/resources/js/profile.js"></script>
<script>
  document.addEventListener("DOMContentLoaded", function () {
    const canvas = document.getElementById('trendRadar');
    if (!canvas) {
      console.error('trendRadar 차트를 찾을 수 없습니다.');
      return;
    }
    const ctx = canvas.getContext('2d');  

    const trendRadar = new Chart(ctx, {
      type: 'radar',
      data: {
        labels: ['승률', '돌격', '저격', '특수총', 'K/D'],
        datasets: [{
          label: '플레이 성향 분석',
          data: [
            ${trend.recent_win_rate },
            ${trend.recent_assault_rate },
            ${trend.recent_sniper_rate },
            ${trend.recent_special_rate },
            ${trend.recent_kill_death_rate }
          ],
          backgroundColor: 'rgba(96, 225, 203, 0.2)',
          borderColor: '#60E1CB',
          pointBackgroundColor: '#60E1CB',
          borderWidth: 2
        }]
      },
      options: {
        responsive: false,
        scales: {
          r: {
            beginAtZero: true,
            max: 100,
            ticks: {
              display:false
            },
            pointLabels: {
              font: {
                size: 14
              }
            }
          }
        },
        plugins: {
          legend: {
            display: false
          },
          tooltip: {
            callbacks: {
              label: function (context) {
                const label = context.label;
                const value = context.parsed.toFixed(1);
                return `${label}: ${value}%`;
              }
            }
          }
        }
      }
    });
  });
</script>




</body>
</html>
