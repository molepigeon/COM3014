//Parameters for the Dropzone code
//  Allow only image MIME types, with a max size of 5MB
//  Only one file may be uploaded at a time
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

//Document Ready 
$(function() { 
    var uploadingBay = new Dropzone("#uploader");
    
    //When a file is dropped onto the box, hide the message.
    uploadingBay.on("drop", function(){
       $('#uploadMessage').attr("style","display: none");     
    });
    
    //When a file upload is complete, remove the files from the box
    //  reset the loading message and hide the upload box.
    //  Then refresh the picture list to include the new file.
    uploadingBay.on("complete", function(){
        uploadingBay.removeAllFiles(true);
        hideUploadBox();
        $('#uploadMessage').attr("style","display: block");
        
        $.ajax({
            url: "rest/getMoreImages/0/0",
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

