$(function() { //Document Ready   
    $('#signoutButton').click(logOut);
    $('#uploadButton').click(displayUploadBox);
    $('#lightbox').click(hideUploadBox);
    $('#signinButton').attr('style', 'display: inline');
    
    infiniScroll(0);
    
    $(window).scroll(function(){
        
        if  ($(window).scrollTop() === $(document).height() - $(window).height()){
              
              if(streamOK === true) {
                  infiniScroll(count)
              };
              count++;
        }
        });
});

$(document).bind('DOMSubtreeModified', function () {
    $(".polaroid").draggable({scroll: true,revert: true,stack:".polaroid"});
});

var streamOK = true;
var count = 1; //Page Count
var plusName = "";
var uploadCount = 0;

function signinCallback(authResult) {
      if (authResult['status']['signed_in']) {
        $('#signinButton').attr('style', 'display: none');
        $('#signoutButton').attr('style', 'display: inline-block');
        $('#uploadButton').attr('style', 'display: inline-block');
        $('#avatar').attr('style', 'display: inline-block');
        
        idPass(authResult['code'],authResult['id_token'],authResult['access_token'],$('meta[name=google-state]').attr('content'));
      } else {
        $('#signinButton').attr('style', 'display: inline');
        $('#signoutButton').attr('style', 'display: none');
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
    }
});
}

function infiniScroll(pageNumber) {
        
    $.ajax({
        url: "rest/getMoreImages/"+pageNumber+"/"+uploadCount,
        type:'GET',
        success: function(imageJSON){
            
            var imageResults = $.parseJSON(imageJSON);
            for(var i=0;i<6;i++) {                
                if(imageResults.images[i].userID !== "") {
                    
                    getGPlusName(imageResults.images[i].userID);
                    
                    $("#contentList").append(
                            '<li class="polaroid">'+
                                '<a href="javascript:void(0)" title="'+plusName+'">'+
                                        '<img src="uploads/'+imageResults.images[i].filename+'" alt="'+plusName+'" />'+
                                '</a>'+
                            '</li>');
                } else {
                    streamOK = false;
                }
            }
            
        }
    });
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

function getGPlusName(userID) {
    $.ajax({
        url: "https://www.googleapis.com/plus/v1/people/"+userID+"?key=AIzaSyAc4RcTyZ-faVwuPvmfXp67y-ubIWSyjj4&fields=displayName",
        type:'GET',
        async: false,
        success: function(plusJSON){
            window.plusName = plusJSON.displayName;
            document.plusName = plusJSON.displayName;
        }
    });
}