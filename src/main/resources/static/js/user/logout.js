// 수정예정
//             /*<![CDATA[*/
//                    let username = '[[${#authentication.name}]]';
//                    //사용자의 provider꺼내오기
//                    let provider = '[[${#authentication.getPrincipal().getUser().getProvider()}]]';
//                    alert("provider",provider);
//                    /*]]>*/
//

            function logoutAndRedirect() {
                  if(true){
                      // 새 창 열기
                       var newWindow = window.open('/user/logout', '_blank','width=450,height=450,top=1,right=100%');
                       setTimeout(function() {
                         // 1.5초후 창닫기
                          newWindow.close();

                         // 특정 지점으로 리다이렉션
                         window.location.href = '/user/login';
                       }, 1500);
                   }

             }
