Dropzone.options.uploader = {
  paramName: "imageFile",
  maxFilesize: 5,
  uploadMultiple: false,
  acceptedFiles:"image/*",
  maxFiles: 1,
  accept: function(file, done) {
    done();
  }
};

$(function() { //Document Ready  
    var uploadingBay = new Dropzone("#uploader");
    
    uploadingBay.on("drop", function(){
       $('#uploadMessage').attr("style","display: none");     
    });
    
    uploadingBay.on("complete", function(){
        uploadingBay.removeAllFiles(true);
        hideUploadBox();
        $('#uploadMessage').attr("style","display: block");
        
        $.ajax({
            url: "rest/getMoreImages/0",
            type:'GET',
            success: function(imageJSON){
                
                uploadCount = uploadCount+1;

                var imageResults = $.parseJSON(imageJSON);
                getGPlusName(imageResults.images[0].userID);
                $("#contentList").prepend(
                        '<li class="polaroid">'+
                            '<a href="javascript:void(0)" title="'+document.plusName+'">'+
                                    '<img src="uploads/'+imageResults.images[0].filename+'" alt="'+document.plusName+'" />'+
                            '</a>'+
                        '</li>');
                }
        });
    });
});

