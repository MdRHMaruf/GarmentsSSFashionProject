function btnsaveAction(v){
	
		
/*	var buyerId = $("#buyername").val().trim();
	var itemId = $("#itemname").val().trim();
	var styleNo = $("#styleno").val().trim();
	var userId = $("#userId").val();
	var data=$('#data').val();*/
	

    var form = $('#myForm')[0];
    var data = new FormData(form);
	
    $.ajax({  
    	url: './btnSaveAction',
    	method:"POST",  
        enctype: 'multipart/form-data',
        data:data,
        success: function (data) {
        	alert(data);
        },
	    error: function (e) {
	        alert("ERROR : ", e);
	    }
   });  
    

	
/*	var paperElement = document.getElementById("modalPapers");

	  if (!paperElement.value) {
	    console.log("No files selected.")
	    return;
	  }
	  var form = document.getElementById("myForm");
	  var formData = new FormData(form);
	  var xhr = getXMLHTTP();
	  xhr.open('POST', "submitFiles");
	  xhr.onreadystatechange = function() {
	    if (xhr.readyState == 4 && xhr.status == 405) {
	      console.log("Files Uploaded")
	    }
	  }
	  xhr.send(formData);*/
	
/*	var buyerId = $("#buyername").val().trim();
	var itemId = $("#itemname").val().trim();
	var styleNo = $("#styleno").val().trim();
	var userId = $("#userId").val();
	var data=$('#data').val();
	
	var fronImage = new FormData($('#uploadFrontImage')[0]);
	var backImage = new FormData($('#uploadBackImage')[0]);
	

	
    $.ajax({
        url: './btnSaveAction',
        type: "POST",
        dataType: 'json',
        contentType: false,
        processData: false,
        cache: false,
        data: fronImage,
        success: function (data) {
        	alert(data);
        }
      });*/
}


function setData(styleItemAutoId) {

	//alert("styleItemAutoId "+styleItemAutoId );

	  document.getElementById("styleItemAutoId").value = styleItemAutoId;
	  document.getElementById("styleNo").value = document.getElementById("styleNo" + styleItemAutoId).innerHTML;
	  document.getElementById("itemId").value = document.getElementById("itemId" + styleItemAutoId).value;
	  /*  document.getElementById("mobile").value = document.getElementById("mobile" + merchendiserId).value;
	  document.getElementById("fax").value = document.getElementById("fax" + merchendiserId).value;
	  document.getElementById("email").value = document.getElementById("email" + merchendiserId).value;
	  document.getElementById("skype").value = document.getElementById("skype" + merchendiserId).value;
	  document.getElementById("address").value = document.getElementById("address" + merchendiserId).value;*/
	 
	  document.getElementById("btnSave").disabled = true;
	  document.getElementById("btnEdit").disabled = false;

	}

$(document).ready(function () {
	  $("input:text").focus(function () { $(this).select(); });
	});

	$(document).ready(function () {
	  $("#search").on("keyup", function () {
	    var value = $(this).val().toLowerCase();
	    $("#dataList tr").filter(function () {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});


