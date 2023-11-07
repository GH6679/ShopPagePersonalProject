
// window.onload = function() {
//   const kindWrap =  document.querySelector('.nav-bottom');
//   const slider = kindWrap.querySelector('.bottom-left');
//   const slideLis = slider.querySelectorAll('.bottom-li')
//   const moveButton = kindWrap.querySelector('.arrow');

//   /* ul 넓이 계산해 주기 */
//   const liWidth = slideLis[0].clientWidth;
//   const sliderWidth = liWidth * slideLis.length;
//   slider.style.width = `${sliderWidth}px` ;

//   /* 리스너 설치하기 */
//   let currentIdx = 0; // 슬라이드 현재 번호
//   let translate = 0; // 슬라이드 위치 값
//   moveButton.addEventListener('click', moveSlide);

//   function moveSlide(event) {
//     event.preventDefault();
//     if (event.target.className === '.next') {
//       if (currentIdx !== slideLis.length -1) {
//         translate -= liWidth;
//         slider.style.transform = `translateX(${translate}px)`;
//         currentIdx += 1;
//       }
//     } else if (event.target.className === '.prev') {
//         if (currentIdx !== 0) {
//           translate += liWidth;
//           slider.style.transform = `translateX(${translate}px)`;
//           currentIdx -= 1;
//         }
//       }
//   }

// }

//스크롤
const lorem = document.querySelector('.op');

lorem.addEventListener('scroll', function () {
    lorem.scrollHeight - 10 < lorem.scrollTop + lorem.clientHeight


});

// class가 "btn"인 모든 요소를 가져옵니다.
const buttons = document.querySelectorAll('.btn');

// 버튼을 클릭할 때 option-box를 토글합니다.
buttons.forEach(button => {
    button.addEventListener('click', (event) => {
        event.preventDefault(); // 기본 동작 막기
        const targetId = button.getAttribute('href').substring(1); // href 속성에서 ID 추출
        const targetBox = document.getElementById(targetId); // 해당 ID의 option-box 가져오기
        if (targetBox) {
            // 모든 option-box를 숨깁니다.
            document.querySelectorAll('.option-box').forEach(box => {
                box.style.display = 'none';
            });
            // 클릭한 버튼과 관련된 option-box를 표시합니다.
            targetBox.style.display = 'block';
        }
    });
});
