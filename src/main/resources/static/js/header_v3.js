// 공지 게시글 불러오기

const init_notice_post = () => {


    const notice_post_list_EL = document.querySelector('.notice-post-list');
    axios.get('/board/getnotice')
        .then(response => {
            console.log(response);

            if (response != null) {


                const resData = response.data;
                resData.forEach(data => {

                    notice_post_list_EL.appendChild(createNoticeList(data));


                })




            }


        })
        .catch(error => console.log(error));

    const createNoticeList = (data) => {


        var notice_post_El = document.createElement('div')
        notice_post_El.className = 'notice-post';
        notice_post_El.style.cursor = "pointer";

        var notice_icon_box_El = document.createElement('div')
        notice_icon_box_El.className = 'notice-icon-box';

        var notice_icon_El = document.createElement('div')
        notice_icon_El.className = 'notice-icon';
        notice_icon_El.textContent = "공지";

        var notice_content_El = document.createElement('div')
        notice_content_El.className = 'notice-content';

        var notice_title_El = document.createElement('div')
        notice_title_El.className = 'notice-title';
        notice_title_El.textContent = data.title;

        var notice_count_El = document.createElement('div')
        notice_count_El.className = 'notice-count';
        notice_count_El.textContent = data.count;

        var notice_time_El = document.createElement('div')
        notice_time_El.className = 'notice-time';
        notice_time_El.textContent = data.regdate;

        notice_post_El.appendChild(notice_icon_box_El);
        notice_post_El.appendChild(notice_content_El);

        notice_icon_box_El.appendChild(notice_icon_El);

        notice_content_El.appendChild(notice_title_El);
        notice_content_El.appendChild(notice_count_El);
        notice_content_El.appendChild(notice_time_El);

        //게시글 읽기 기능
        if (!notice_post_El.hasEventListener) {

            console.log("이벤트 등록")

            notice_post_El.addEventListener('click', function () {

                window.location.href = "/board/read?no="+data.no+"&pageNo=0";

            })
            notice_post_El.hasEventListener = true;

        }


        return notice_post_El;



    }
}

// 공지 게시글 불러오기
init_notice_post();
