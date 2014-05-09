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
    
    uploadingBay.on("drop", function(file){
       $('#uploadMessage').attr("style","display: none");     
    });
    
    uploadingBay.on("complete", function(file){
        uploadingBay.removeAllFiles(true);
        hideUploadBox();
        $('#uploadMessage').attr("style","display: block");
        
        $.ajax({
            url: "getMoreImages/0.htm",
            type:'GET',
            success: function(imageJSON){

                var imageResults = $.parseJSON(imageJSON);
                $("#contentList").append(
                        '<li class="polaroid">'+
                            '<a href="javascript:void(0)" title="'+imageResults.images[0].userID+'">'+
                                    '<img src="uploads/'+imageResults.images[0].filename+'" alt="'+imageResults.images[0].userID+'" />'+
                            '</a>'+
                        '</li>');
                }
        });
    });
});

