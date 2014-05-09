$(function() { //Document Ready   
    $('#signoutButton').click(logOut);
    $('#uploadButton').click(displayUploadBox);
    $('#lightbox').click(hideUploadBox);
    $('#signinButton').attr('style', 'display: inline');
    
    infiniScroll(0);
    
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
        document.getElementById('uploadButton').setAttribute('style', 'display: inline-block');
        document.getElementById('avatar').setAttribute('style', 'display: inline-block');
        idPass(authResult['code'],authResult['id_token'],authResult['access_token'],$('meta[name=google-state]').attr('content'));
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
        
        var results = $.parseJSON(json);
        $( "#username" ).text( results.username );
        $( "#smallavatar" ).attr("src", results.avatarURL );
        $( "#profilelink" ).attr("href", results.profileURL );
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
        url: "imageload",
        type:'POST',
        data: pageNumber, 
        success: function(imageJSON){
            //DO THINGS HERE
        }
    });
    */
    $("#contentList").append('<li class="polaroid"><a href="javascript:void(0)" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="javascript:void(0)" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="javascript:void(0)" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="javascript:void(0)" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="javascript:void(0)" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li><li class="polaroid"><a href="javascript:void(0)" title="Panda!"><img src="images/demo.jpg" alt="Panda!" /></a></li>');
    $(".polaroid").draggable({scroll: true,revert: true,stack:".polaroid"});
    return false;
}
    
function logOut() {
    gapi.auth.signOut();
    location.reload();
}

function displayUploadBox() {
    $("#uploadBox").fadeIn(500);
    $("#lightbox").fadeIn(500);
}

function hideUploadBox() {
    $("#uploadBox").fadeOut(500);
    $("#lightbox").fadeOut(500);
}