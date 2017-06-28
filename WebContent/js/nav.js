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



        window.onload = function(){
            
            //중앙 정렬
            if((window.innerHeight-800)/2 > 1){
                $("#newWrap").css("margin-top",(window.innerHeight-800)/2 );
            }else{
                $("#newWrap").css("margin-top", 0 );
            }

            //네브바 옆 체크표시 애니매이션 ㅋㅋ 귀욥
                
            $('#navHere').animate({
                    width: 12,
                }, 500);
                for(var j=0;j <4;j++){
                    if(j%2==0){
                        $('#navHere').animate({
                            width: 8,
                        }, 300);
                    }else{
                        $('#navHere').animate({
                            width: 12,
                        }, 300);
                    }
            }
    
        }
    $(window).resize(function(){
        if((window.innerHeight-800)/2 > 1){
            $("#newWrap").css("margin-top",(window.innerHeight-800)/2 );
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
                        location.href='index.html';
                    }
                }
            });
    }