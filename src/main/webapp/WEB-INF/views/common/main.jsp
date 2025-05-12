<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>서든어택 전적 검색</title>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css">

  <!-- 광고 -->
  <script async src="https://securepubads.g.doubleclick.net/tag/js/gpt.js" crossorigin="anonymous"></script>
<script>
  window.googletag = window.googletag || { cmd: [] };

  googletag.cmd.push(() => {
    googletag
      .defineSlot("/6355419/Travel/Europe/France/Paris", [300, 250], "banner-ad")
      .addService(googletag.pubads());
    googletag.enableServices();
  });
</script>


</head>
<body>

  <header>
    <h1>SuddenStats</h1>
    <form action="/search" method="GET">
      <!-- <input type="text" name="nickname" placeholder="닉네임 검색..." required> -->
    </form>
  </header>
  

  <div class="ad-banner1">
    <img src="	https://img.sa.nexon.com/barracks/assets/images/home/logo.png" alt="">
  </div>

  <main>
    <div class="search-box">
      <h2> 전적 검색</h2>
      <form action="/search" method="POST">
        <input type="text" name="nickname" placeholder="닉네임을 입력하세요" required>
        <button type="submit">검색</button>
      </form>
    </div>
  </main>

  <div class="ad-banner">
    <div id="banner-ad" style="width: 300px; height: 250px; margin: 0 auto;"></div>
  </div>

  <footer>© 2025 SuddenStats. </footer>

  <script>
    googletag.cmd.push(() => {
      googletag.display("banner-ad");
    });
  </script>
</body>
</html>
