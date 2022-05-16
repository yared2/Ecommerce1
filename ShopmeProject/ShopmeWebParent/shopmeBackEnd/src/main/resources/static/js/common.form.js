$(document).ready(function(){
	$("#buttonCancel").on("click",function(){
		window.location=modeluURL;
	});
	
	$("#fileImage").change(function(){
		fileSize= this.files[0].size;
		if(fileSize > 102400){
			this.setCustomValidity("You must choose an image less than 100KB!");
			this.reportValidity();
		}else{
			this.setCustomValidity("");
			showImageThumbnail(this);
		}
	
	});
	
});

function showImageThumbnail(fileInput){
	var file =fileInput.files[0];
	var reader = new FileReader();
	reader.onload=function(e){
		$("#thumbnail").attr("src",e.target.result);
		
	};
	reader.readAsDataURL(file);
}

function showModelDialog(title,  message){
		$("#modalTitle").text(title);
		$("#modalBody").text(message);
		$("#modalDialog").modal();
	}
	
	function showErrorModal(message){
		showModelDialog("Error",message)
		
	}
	function showWarningModal(message){
		showModelDialog("Warning",message)
		
	}