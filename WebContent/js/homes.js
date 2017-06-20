$.ajax({
    type: "post",
    url: "SessionCheck.do",
    dataType: "json",
    success: function(data) {
        if(data[0].result == "s"){
            location.href='home.html';
        }
    }
});