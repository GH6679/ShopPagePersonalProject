

    const delete_file_els = document.querySelectorAll('.delete_file');

    delete_file_els.forEach(delete_file_el=>{

    delete_file_el.addEventListener('click',function(){
        console.log(delete_file_el.dataset.filename);
        const filename = delete_file_el.dataset.filename;
        const no = delete_file_el.dataset.no;
        axios.put("/board/put/"+no+"/"+filename)
            .then(response=>{
                console.log(response.data);
                location.reload();
            })
            .catch(error=>console.log(error));


    });
})
