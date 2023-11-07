/*<![CDATA[*/
var bno = [[${boardDto.no}]];

const writername = "[[${boardDto.username}]]";
console.log("writername: " + writername);
let username = '[[${#authentication.name}]]';
console.log("username: " + username);

/*]]>*/


const update_btn_el = document.querySelector('.update_btn');
const delete_btn_el = document.querySelector('.delete_btn');


delete_btn_el.addEventListener('click', function () {
    const no = delete_btn_el.dataset.no;
    axios.delete("/board/delete?no=" + no)
        .then(
            response => {
                console.log(response);
                if (response.status === 200) {
                    location.href = "/board/list";
                }
            }
        )
        .catch(error => console.log(error));
});

update_btn_el.style.visibility = 'hidden'; // 수정 버튼 숨김
delete_btn_el.style.visibility = 'hidden'; // 삭제 버튼 숨김

//권한에 따라 게시글 수정,삭제 버튼 가시화


const getRole = () => {

    axios.get("/board/getRole/" + username)
        .then(response => {
            console.log(response);
            if (response != null) {
                let role = response.data;
                console.log("role: " + role)
                if (role === "ROLE_USER") {
                    //유저일 경우 글쓴이와 로그인한 계정이 같으면 수정,삭제 보임
                    if (writername === username) {
                        update_btn_el.style.visibility = 'visible'; // 수정 버튼 보임
                        delete_btn_el.style.visibility = 'visible'; // 삭제 버튼 보임
                    }

                } else if (role === "ROLE_ADMIN") {//관리자일 경우 게시글,댓글의 수정,삭제버튼 보임
                    update_btn_el.style.visibility = 'visible'; // 수정 버튼 보임
                    delete_btn_el.style.visibility = 'visible'; // 삭제 버튼 보임
                }
            }

        })
        .catch(error => console.log(error))


}
getRole();


//----------------------------------------------------------------
//댓글 추가
//----------------------------------------------------------------
const reply_btn_el = document.querySelector('.reply_btn');


reply_btn_el.addEventListener('click', function () {
    const reply_contents_el = document.querySelector('.reply_contents');
    console.log("BNO : ", bno);

    axios.get("/board/reply/add?bno=" + bno + "&contents=" + reply_contents_el.value + "&username=" + username)
        .then(response => {
            console.log(response);

            //기존list삭제
            const replyBody_items = document.querySelector('.reply-body .items');
            while (replyBody_items.firstChild) {
                replyBody_items.firstChild.remove()
            }

            //새로list불러오기
            getReplylist();

            //댓글 개수 가져오기
            axios.get("/board/reply/count?bno=" + bno)
                .then(response => {
                    let reply_count_el = document.querySelector('.reply--count');
                    reply_count_el.innerHTML = response.data;
                })
                .catch(error => {
                });

            //Input Clear
            reply_contents_el.value = "";

        })
        .catch(error => console.log(error));

});
//----------------------------------------------------------------
//엔터키 댓글 추가
//----------------------------------------------------------------
const reply_contents_el = document.querySelector('.reply_contents');
reply_contents_el.addEventListener('keydown', function (e) {

    if (e.keyCode == 13) {

        axios.get("/board/reply/add?bno=" + bno + "&contents=" + reply_contents_el.value + "&username=" + username)
            .then(response => {
                console.log(response);

                //기존list삭제
                const replyBody_items = document.querySelector('.reply-body .items');
                while (replyBody_items.firstChild) {
                    replyBody_items.firstChild.remove()
                }

                //새로list불러오기
                getReplylist();

                //댓글 개수 가져오기
                axios.get("/board/reply/count?bno=" + bno)
                    .then(response => {
                        let reply_count_el = document.querySelector('.reply--count');
                        reply_count_el.innerHTML = response.data;
                    })
                    .catch(error => {
                    });

                //Input Clear
                reply_contents_el.value = "";

            })
            .catch(error => console.log(error));

    }
});


//----------------------------------------------------------------
//댓글 리스트 가져오기
//----------------------------------------------------------------
const getReplylist = () => {

    /*<![CDATA[*/
    let bno = [[${boardDto.no}]];
    /*]]>*/

    console.log("getReplylist bno :", bno);
    axios.get("/board/reply/list?bno=" + bno)
        .then(response => {
            console.log(response);
            if (response.data != null) {

                //댓글 Item 만들기
                const reply_list = response.data;
                reply_list.forEach(
                    reply => {
                        CreateItem(reply);
                    }
                );

                //댓글 개수 가져오기
                axios.get("/board/reply/count?bno=" + bno)
                    .then(response => {
                        let reply_count_el = document.querySelector('.reply--count');
                        reply_count_el.innerHTML = response.data;
                    })
            }

        })
        .catch(error => console.log(error));
};
getReplylist();


//----------------------------------------------------------------
// Item 만들기
//----------------------------------------------------------------
const items_el = document.querySelector(".items");
const CreateItem = (reply) => {
    console.log("reply : ", reply);

    // div 엘리먼트 생성
    var divElement = document.createElement("div");
    divElement.className = "item";

    // header div 엘리먼트 생성
    var headerElement = document.createElement("div");
    headerElement.className = "header";

    // img 엘리먼트 생성
    var imgElement = document.createElement("img");
    imgElement.src = "/images/account.png";
    imgElement.alt = "";

    // header div에 img 엘리먼트 추가
    headerElement.appendChild(imgElement);

    // body div 엘리먼트 생성
    var bodyElement = document.createElement("div");
    bodyElement.className = "body";

    // 내용 엘리먼트 생성(계정 | 날짜 )
    var contentDiv = document.createElement("div");

    // 댓글 수정 삭제 div
    var update_delete_div = document.createElement("div");
    update_delete_div.style.display = "none";

    var span1 = document.createElement("span");
    span1.className = "reply_username me-2";
    span1.textContent = reply.username;
    var span2 = document.createElement("span");
    span2.className = "reply_regdate";
    span2.textContent = formatter(reply.regdate);

    // contents div 엘리먼트 생성
    var contentsDiv = document.createElement("div");
    contentsDiv.className = "contents";
    contentsDiv.textContent = reply.content;

    //댓삭

    var delete_a = document.createElement("a");
    delete_a.setAttribute("href", "/board/reply/delete/" + reply.bno + "/" + reply.rno);       //DELETE
    var delete_btn = document.createElement("button");
    delete_btn.className = "reply_delete_btn";
    delete_btn.textContent = "삭제";

    //댓글수정 추가

    var update_a = document.createElement("a");
    const update_btn = document.createElement("button");
    update_btn.id = "update_btn";
    update_btn.className = "reply_update_btn";
    update_btn.textContent = "수정"

    //댓글수정 폼(실시간으로 보이게 하려고 div로 변경함)
    var updateForm = document.createElement("div");
    updateForm.id = "updateForm";
    updateForm.style.display = 'none';

    //수정 버튼 클릭 시
    update_btn.addEventListener("click", function () {

        if (updateForm.style.display == 'none') {
            updateForm.style.display = 'block';
            update_btn.textContent = "취소";
            console.log("댓글 수정 클릭");
        } else {
            updateForm.style.display = 'none';
            update_btn.textContent = "수정";
            console.log("수정 취소 클릭");
        }

        //수정 버튼 클릭시 수정내용 적을 텍스트영역
        var contents_area = document.createElement("textarea");

        contents_area.className = "reply_text";
        contents_area.style.width = "300px";
        contents_area.style.height = "150px";
        contents_area.value = reply.content;

        //댓글 저장 버튼
        var save_btn = document.createElement("button");
        save_btn.className = "save_btn";
        save_btn.type = "submit";
        save_btn.textContent = "저장"


        //저장 버튼 클릭 시
        save_btn.addEventListener("click", function () {
            console.log("댓글 저장 클릭")

            // 텍스트영역의 내용 댓글내용에 저장
            reply.content = contents_area.value;
            //화면에 보이는 내용 변경
            contentsDiv.textContent = reply.content;

            //변경된 내용 데이터베이스에 저장
            axios.post("/board/reply/update", {
                rno: reply.rno,
                content: reply.content
            })
                .then(response => {
                    console.log(response);
                    if (response.status === 200) {
                        updateForm.style.display = 'none';
                        update_btn.textContent = "수정";

                    }
                })
                .catch(error => console.error(error));


        });


        updateForm.innerHTML = ''; // 기존 내용 초기화
        updateForm.appendChild(contents_area);
        updateForm.appendChild(save_btn);
    });

    //댓글 수정 삭제 버튼 댓글을 쓴 유저와 현재 로그인한 유저가 같으면 가시화
    if (username == reply.username)
        update_delete_div.style.display = "block";


    //권한에 따라 댓글쓰기와 댓글 수정,삭제 버튼 가시화
    if (reply !== null) {
        const getRole_reply = () => {

            axios.get("/board/getRole/" + username)
                .then(response => {
                    console.log(response);
                    if (response != null) {
                        let role = response.data;
                        console.log("role: " + role)

                        //회원일경우 role이 admin과 user가 아닐 경우 댓글쓰기 숨김
                        // if(role!=="ROLE_ADMIN"&&role!="ROLE_ADMIN"){
                        //     const reply_header=document.querySelector('.reply-header');
                        //     reply_header.style.display = 'none';
                        // }
                        if (role === "ROLE_ADMIN") {
                            update_delete_div.style.display = 'block'; // 댓글 수정,삭제 보임
                        }
                    }

                })
                .catch(error => console.log(error))
        }
        getRole_reply();
    }

    // 내용 엘리먼트 추가
    bodyElement.appendChild(contentDiv);


    delete_a.appendChild(delete_btn);
    update_a.appendChild(update_btn);

    contentDiv.appendChild(span1);
    contentDiv.appendChild(span2);

    // contents div 엘리먼트 추가
    bodyElement.appendChild(contentsDiv);

    // 좋아요 및 싫어요 링크 엘리먼트 생성
    var likeLink = document.createElement("a");
    var dislikeLink = document.createElement("a");

    likeLink.href = "/board/reply/thumbsup?bno=" + reply.bno + "&rno=" + reply.rno + "&username=" + username;
    dislikeLink.href = "/board/reply/thumbsdown?bno=" + reply.bno + "&rno=" + reply.rno;

    // 좋아요 아이콘과 개수 엘리먼트 생성
    var likeIcon = document.createElement("span");
    likeIcon.className = "material-symbols-outlined me-1";
    likeIcon.textContent = "thumb_up";
    var likeCount = document.createElement("span");
    likeCount.className = "me-1";
    likeCount.textContent = reply.likecount;

    // 싫어요 아이콘과 개수 엘리먼트 생성
    var dislikeIcon = document.createElement("span");
    dislikeIcon.className = "material-symbols-outlined me-1";
    dislikeIcon.textContent = "thumb_down";
    var dislikeCount = document.createElement("span");
    dislikeCount.className = "me-1";
    dislikeCount.textContent = reply.unlikecount;

    // 좋아요와 싫어요 엘리먼트를 좋아요 링크에 추가
    likeLink.appendChild(likeIcon);
    likeLink.appendChild(document.createTextNode(" "));
    likeLink.appendChild(likeCount);

    // 좋아요와 싫어요 엘리먼트를 싫어요 링크에 추가
    dislikeLink.appendChild(dislikeIcon);
    dislikeLink.appendChild(document.createTextNode(" "));
    dislikeLink.appendChild(dislikeCount);

    const ThumbsSpan = document.createElement("span");
    ThumbsSpan.append(likeLink);
    ThumbsSpan.append(dislikeLink);


    // 좋아요와 싫어요 링크 엘리먼트를 contentdiv에 추가
    contentDiv.appendChild(ThumbsSpan);

    //댓글 수정 삭제 update_delete_div에 추가
    update_delete_div.appendChild(update_a);
    update_delete_div.appendChild(delete_a);
    update_delete_div.appendChild(updateForm)


    //body에 update_delete_div
    bodyElement.appendChild(update_delete_div);


    // header와 body 엘리먼트를 div에 추가
    divElement.appendChild(headerElement);
    divElement.appendChild(bodyElement);

    // div 엘리먼트를 body에 추가
    items_el.appendChild(divElement);
}

//----------------------------------------------------------------
// LocalDateTime Formatter
//----------------------------------------------------------------
const formatter = (regDate) => {
    // 분리한 정보를 사용하여 Date 객체를 생성합니다.
    const year = parseInt(regDate[0]);
    const month = parseInt(regDate[1]) - 1; // 월은 0부터 시작하므로 1을 빼줍니다.
    const day = parseInt(regDate[2]);
    const hours = parseInt(regDate[3]);
    const minutes = parseInt(regDate[4]);
    const seconds = parseInt(regDate[5]);

    const date = new Date(year, month, day, hours, minutes, seconds);

    // Date 객체를 원하는 형식으로 포맷팅합니다.
    const formattedDate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;

    return formattedDate;

};



