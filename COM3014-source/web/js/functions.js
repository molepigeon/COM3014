$(function() { //Document Ready
    $('#signoutButton').click(logOut);
    $("#showLogin").click(buttonTest);
    
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
        console.log('User Token: ' + authResult['id_token']);
        
        
      } else {
        document.getElementById('signinButton').setAttribute('style', 'display: inline');
        document.getElementById('signoutButton').setAttribute('style', 'display: none');
        console.log('Sign-in state: ' + authResult['error']);
      }
    }
    
function idPass(idToken) {
    $.ajax({
    url: "post.php",
 
    // the data to send (will be converted to a query string)
    data: {
        id: 123
    },
 
    // whether this is a POST or GET request
    type: "GET",
 
    // the type of data we expect back
    dataType : "json",
 
    // code to run if the request succeeds;
    // the response is passed to the function
    success: function( json ) {
        $( "<h1/>" ).text( json.title ).appendTo( "body" );
        $( "<div class=\"content\"/>").html( json.html ).appendTo( "body" );
    },
 
    // code to run if the request fails; the raw request and
    // status codes are passed to the function
    error: function( xhr, status, errorThrown ) {
        alert( "Sorry, there was a problem!" );
        console.log( "Error: " + errorThrown );
        console.log( "Status: " + status );
        console.dir( xhr );
    },
 
    // code to run regardless of success or failure
    complete: function( xhr, status ) {
        alert( "The request is complete!" );
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
    //location.reload();
}

function buttonTest() {
    document.getElementById('signinButton').setAttribute('style', 'display: inline');
}