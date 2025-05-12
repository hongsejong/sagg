console.log("연결확인");
function bindDetailButtons() {
    document.querySelectorAll(".detail-btn").forEach(btn => {
      btn.addEventListener("click", () => {
        const matchId = btn.dataset.matchId;
  
        // 1. 우선 가까운 .match-box 내에 .detail-result 찾기
        let resultDiv = btn.closest(".match-box")?.querySelector(".detail-result");
  
        // 2. 만약 못 찾으면 id 기반으로 다시 시도 (서버에서 렌더된 초기 구조 대응)
        if (!resultDiv && btn.dataset.index) {
          resultDiv = document.getElementById(`detail-${btn.dataset.index}`);
        }
  
        if (!resultDiv) {
          console.error("상세결과 div를 찾을 수 없습니다.");
          return;
        }
  
        fetch(`/match-detail?matchId=${matchId}`)
          .then(res => res.json())
          .then(users => {
            let detailHTML = '<ul>';
            users.forEach(user => {
              detailHTML += `
                <li>${user.user_name} | ${user.kill}킬 / ${user.death}데스 / ${user.damage}데미지 / ${user.assist}어시스트</li>
              `;
            });
            detailHTML += '</ul>';
            resultDiv.innerHTML = detailHTML;
            
          })
          .catch(err => {
            resultDiv.innerHTML = '<p>상세 조회 실패</p>';
            console.error("상세조회 실패", err);
          });
      });
    });
  }
  
document.addEventListener("DOMContentLoaded", () => {
    const nickname = document.querySelector("h2").textContent.trim();
  
    // 모드 탭 클릭 시 매치 리스트 불러오기
    document.querySelectorAll(".tab-btn").forEach(btn => {
      btn.addEventListener("click", () => {
        const mode = btn.dataset.mode;
        fetch(`/search-mode?nickname=${nickname}&mode=${encodeURIComponent(mode)}`)
          .then(res => res.json())
          .then(matches => {
            const matchHistoryDiv = document.querySelector(".match-history");
            let html = '';
            

            if(matches.length ===0){
                html =`<div class="nomatch">매치 정보가 없습니다</div>`;
            }else{

                matches.forEach((match, index) => {
                  html += `
                    <div class="match-box">
                      <p>매치 타입: ${match.match_type} / 날짜: ${match.date_match} / 매치 모드 : ${match.match_mode}   <button class="detail-btn" data-match-id="${match.match_id}" data-index="${index}">상세보기</button></p>

                      <div class="detail-result" id="detail-${index}"></div>
                    </div>
                  `;
                });
            }
  
  
            matchHistoryDiv.innerHTML = html;
            bindDetailButtons(); // 상세보기 버튼 바인딩
          })
          .catch(err => {
            console.error("매치 조회 실패", err);
          });
      });
    });
  
    // 상세보기 버튼 바인딩
    function bindDetailButtons() {
        document.querySelectorAll(".detail-btn").forEach(btn => {
          btn.addEventListener("click", () => {
            const matchId = btn.dataset.matchId;
      
            // 가까운 .match-box 내의 .detail-result 찾기
            let resultDiv = btn.closest(".match-box")?.querySelector(".detail-result");
      
            // 못 찾으면 id 기반으로 찾기 (초기 렌더 대응)
            if (!resultDiv && btn.dataset.index) {
              resultDiv = document.getElementById(`detail-${btn.dataset.index}`);
            }
      
            if (!resultDiv) {
              console.error("상세결과 div를 찾을 수 없습니다.");
              return;
            }

              // 이미 열려 있으면 닫기
            if (resultDiv.innerHTML.trim() !== "") {
                resultDiv.innerHTML = "";
                return;
            }

      
            fetch(`/match-detail?matchId=${matchId}`)
              .then(res => res.json())
              .then(users => {
                console.log(users);
                console.log(Object.keys(users[0]));
                //mvp 계산
                const mvpUser = users.reduce((top, current) => {
                    const topScore = top.kill * 100 + top.assist * 50 + top.damage;
                    const currScore = current.kill * 100 + current.assist * 50 + current.damage;
                    return currScore > topScore ? current : top;
                  }, users[0]);
      
                let winHTML = '';
                let loseHTML = '';
                let drawHTML = '';
      
                users.forEach(user => {
                    const isMvp = user.user_name === mvpUser.user_name;

                  let resultText = "";
                  let resultClass = "";
                  let targetHTML = "";
      
                  switch (user.match_result) {
                    case "1":
                      resultText = "승리";
                      resultClass = "win";
                      targetHTML = "win";
                      break;
                    case "2":
                      resultText = "패배";
                      resultClass = "lose";
                      targetHTML = "lose";
                      break;
                    case "3":
                      resultText = "무승부";
                      resultClass = "draw";
                      targetHTML = "draw";
                      break;
                    default:
                      resultText = "알 수 없음";
                      resultClass = "unknown";
                  }
      
                  const listItem = `
                    <li>
                      ${isMvp ? '<span class="mvp-badge">🏅</span>' : ''}
                      <span class="match-result ${resultClass}">${resultText}</span> |
                      ${user.user_name} 
                      ${user.kill}킬 / ${user.death}데스 / ${user.damage}데미지 / ${user.assist}어시스트 |
                      시즌계급 : ${user.season_grade}
                    </li>
                  `;
      
                  if (targetHTML === "win") winHTML += listItem;
                  else if (targetHTML === "lose") loseHTML += listItem;
                  else drawHTML += listItem;
                });
      
                // 최종 결과 HTML 조립
                resultDiv.innerHTML = `
                  <div class="team-section">
                    <h3>🏆 승리 팀</h3>
                    <ul class="team-win">${winHTML}</ul>
                  </div>
                  <div class="team-section">
                    <h3>💔 패배 팀</h3>
                    <ul class="team-lose">${loseHTML}</ul>
                  </div>
                  ${drawHTML ? `
                  <div class="team-section">
                    <h3>🤝 무승부</h3>
                    <ul class="team-draw">${drawHTML}</ul>
                  </div>` : ''}
                `;
              })
              .catch(err => {
                resultDiv.innerHTML = '<p>상세 조회 실패</p>';
                console.error("상세조회 실패", err);
              });
          });
        });
      }
      
  
    // 페이지 로딩 직후 기존 상세보기 버튼도 동작하도록
    bindDetailButtons();
  });
  

