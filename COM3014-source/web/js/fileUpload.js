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
    });
});

