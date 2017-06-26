/*   로그인후 세션 존제 유무 확인*/
$.ajax({
    type: "post",
    url: "SessionCheck.do",
    dataType: "json",
    success: function(data) {
        if(data[0].result == "s"){
            $.ajax({
                type: "post",
                url: "PersonalServlet.do",
                dataType: "json",
                success: function(data) {
                    console.log(data);
                    var id = data[0].MEMBER_ID;
                    var photo = data[0].MEMBER_IMG;
                    $('#user_Id').html(id);
                    $('#user_Img').attr('src',photo);
                }
            });
        }else{
            alert("잘못된 접근 입니다.");
            location.href='index.html';
        }
    
    
    }
});

        //중앙 정렬
        window.onload = function(){
        if((window.innerHeight-900)/2 > 1){
            $("#newWrap").css("margin-top",(window.innerHeight-900)/2 );
        }else{
            $("#newWrap").css("margin-top", 0 );
        }

        }
    
    $(window).resize(function(){
        if((window.innerHeight-900)/2 > 1){
            $("#newWrap").css("margin-top",(window.innerHeight-900)/2 );
        }else{
            $("#newWrap").css("margin-top", 0 );
        }

    });


            
    /* 로그아웃 처리*/
    function logout(){
         $.ajax({
                type: "post",
                url: "Logout.do",
                dataType: "json",
                success: function(data) {
                    if(data[0].result == "s"){
                        alert("로그아웃 되었습니다.");
                        location.href='index.html';
                    }
                }
            });
    }