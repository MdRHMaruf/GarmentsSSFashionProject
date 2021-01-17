var buyerid=$("#buyer").val();
console.log(" buyer con "+buyerid)

$('.selectpicker').selectpicker('refresh');
 $('#buyerId').val(buyerid).change();


function readFrontURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#blahFront')
                    .attr('src', e.target.result)
                    .width(150)
                    .height(200);
            };

            reader.readAsDataURL(input.files[0]);
        }
 }

function readBackURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#blahBack')
                .attr('src', e.target.result)
                .width(150)
                .height(200);
        };

        reader.readAsDataURL(input.files[0]);
    }
}

function btnsaveAction(v){
	console.log(" btnsave action")
	
	var i=0;
	var hexvalues = [];
	$('#itemId :selected').each(function(i, selectedElement) {
		 hexvalues[i]=$(selectedElement).val();
		var txt=$(selectedElement).text();
		i++;
	});
	
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
    

}


function setData(styleItemAutoId ) {

	
	$('#buyerId').val($('#hBuyerId'+styleItemAutoId).val()).change();
	$('.selectpicker').selectpicker('refresh');
	
	$('#styleNo').val($('#hStyleNo'+styleItemAutoId).val());
	
	$('#date').val($('#hDate'+styleItemAutoId).val());
	$('#size').val($('#hSize'+styleItemAutoId).val());
	
	$('#itemId').val($('#hItemId'+styleItemAutoId).val()).change();
	$('.selectpicker').selectpicker('refresh');
	
	
	$('#styleItemAutoId').val(styleItemAutoId);
	$('#styleid').val($('#hStyleId'+styleItemAutoId).val());
	$('#hbuyerId').val($('#hBuyerId'+styleItemAutoId).val());
	
	
	var styleItemId=$('#styleItemAutoId').val();
	var styleid=$('#styleid').val();
	
	console.log("styleItemId "+styleItemId);
	console.log("styleid "+styleid);
	 
	 
	  document.getElementById("btnSave").disabled = true;
	  document.getElementById("btnEdit").disabled = false;
	  
	  
	  getImage(styleItemAutoId)
	  
	 

	}



function getImage(id) {
	 


    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './getImages/',
      data: {
    	  styleItemAutoId:id
      },
      success: function (data) {
    	  
    	 
    	  
    	  $('#blahFront') .attr('src',"data:image/png;base64,"+data[0].frontimage).width(150).height(200);
    	  $('#blahBack') .attr('src',"data:image/png;base64,"+data[0].backImage).width(150).height(200);
    	 //document.getElementById('signnatureSet').src="data:image/jpeg;base64,"+data;
    	


    	  
      }
    });

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


