$(function() { //Document Ready
    $('#signoutButton').click(logOut);
    $("#showLogin").click(buttonTest);
    
    document.getElementById('signinButton').setAttribute('style', 'display: inline');
    
    $(window).scroll(function(){
        var count = 1; //Page Count
        if  ($(window).scrollTop() === $(document).height() - $(window).height()){
              console.log('Bottom of Page! Appending Content.');
              infiniScroll(count);
              count++;
        }
        });
});

function signinCallback(authResult) {
      if (authResult['status']['signed_in']) {
        document.getElementById('signinButton').setAttribute('style', 'display: none');
        document.getElementById('signoutButton').setAttribute('style', 'display: inline-block');
        idPass(authResult['code'],authResult['id_token'],authResult['access_token'],$("meta[property='google-state']").attr('content'));
        console.log('User Token: ' + authResult['id_token']);
        
        
      } else {
        document.getElementById('signinButton').setAttribute('style', 'display: inline');
        document.getElementById('signoutButton').setAttribute('style', 'display: none');
        console.log('Sign-in state: ' + authResult['error']);
      }
    }
    
function idPass(code,id,access,state) {
    $.ajax({
    url: "postuid.htm",
    data: {
        code: code,
        id: id,
        access: access,
        state: state
    },
    type: "POST",
    dataType : "text",
    success: function( json ) {
        $( "#username" ).text( json.title ).text(json);
    },
    error: function( xhr, status, errorThrown ) {
        alert( "Something went wrong!" );
        console.log( "Error: " + errorThrown );
        console.log( "Status: " + status );
        console.dir( xhr );
    },
 
    // code to run regardless of success or failure
    complete: function( xhr, status ) {
        //alert( "The request is complete!" );
    }
});
}

function infiniScroll(pageNumber) {
    /*
    $.ajax({
        url: "page",
        type:'POST',
        data: pageNumber, 
        success: function(html){
            $("#dashboard").append("Hello");    // This will be the div where our content will be loaded
        }
    });
    */
    $("#contentList").append('<li class="polaroid"><a href="#" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="#" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="#" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="#" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="#" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="#" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li>');
    return false;
}
    
function logOut() {
    gapi.auth.signOut();
    location.reload();
}

function buttonTest() {
    document.getElementById('signinButton').setAttribute('style', 'display: inline');
}