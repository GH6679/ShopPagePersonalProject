
    function logoutAndRedirect() {
    console.log('logout and redirect')
    // 새 창 열기
    var newWindow = window.open('/logout', '_blank','width=450,height=450,top=1,right=100%');
    setTimeout(function() {
    // 1.5초후 창닫기
    newWindow.close();

    // 특정 지점으로 리다이렉션
    window.location.href = '/login';
}, 1500);

}
