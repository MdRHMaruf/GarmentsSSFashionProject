var itemid=0;
var id;
function styleWiseItemLoad() {
	
	
	console.log(" style wise item")
  var styleId = $("#styleNo").val();

  if (styleId != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getStyleWiseItem',
      data: {
        styleId: styleId
      },
      success: function (data) {

        var itemList = data.itemList;
        var options = "<option id='itemName' value='0' selected>Select Item Name</option>";
        var length = itemList.length;
        for (var i = 0; i < length; i++) {
          options += "<option id='itemName' value='" + itemList[i].itemId + "'>" + itemList[i].itemName + "</option>";
        };
        document.getElementById("itemName").innerHTML = options;
        $('.selectpicker').selectpicker('refresh');
        $('#itemName').val(itemid).change();
        itemid = 0;
      }
    });
  } else {
    var options = "<option id='itemName' value='0' selected>Select Item Name</option>";
    $("#itemName").html(options);
    $('#itemName').selectpicker('refresh');
    $('#itemName').val(0).change();
    itemIdForSet = 0;
  }

}




function insertParcel() {
	
	var user = $("#userId").val();
  var styleNo = $("#styleNo").val();
  var itemName = $("#itemName").val();
  var sampletype = $("#sampletype").val();
  
  
  var dispatchedDate = new Date($("#dispatchedDate").val()).toLocaleDateString('fr-CA') ;
  
  
  var courierName = $("#courierName").val();
  var trackingNo = $("#trackingNo").val();
  var grossWeight = $("#grossWeight").val();
  var unit = $("#unit").val();
  var totalQty = $("#totalQty").val();
  var parcel = $("#parcel").val();
  var rate = $("#rate").val();
  var amount = $("#amount").val();
  var deiveryDate = new Date($("#deiveryDate").val()).toLocaleDateString('fr-CA') ;;
  var delieryTime = $("#delieryTime").val();
  var deliveryTo = $("#deliveryTo").val();
  

  

  if (styleNo != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './insertParcel',
      data: {
    	  user:user,
    	   styleNo:styleNo,
    	   itemName:itemName,
    	   sampletype:sampletype,
    	   dispatchedDate:dispatchedDate,
    	   courierName:courierName,
    	   trackingNo:trackingNo,
    	   grossWeight:grossWeight,
    	   unit:unit,
    	   totalQty:totalQty,
    	   parcel:parcel,
    	   rate:rate,
    	   amount:amount,
    	   deiveryDate:deiveryDate,
    	   delieryTime:delieryTime,
    	   deliveryTo:deliveryTo,
      },
      success: function (data) {
    	  if(data=='success'){
    		  alert("Successfully Inserted")
    	  }else{
    		  alert("Parcel Insertion Failed")
    	  }
      }
    });
  } else {
    
	  alert("Select Style");
  }

}



function getParcelDetails(id) {
	
	
  

  
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './getParcelDetails/'+id,
      data: {
    	  
      },
      success: function (data) {
    	 setData(data)
    	 
      }
    });
 
}

function setData(data){
	console.log(' item name '+data[0].itemName)
	itemid=data[0].itemName;
	 $("#styleNo").val(data[0].styleNo).change();
	 id=data[0].id;
		
	$("#itemName").val(data[0].itemName).change();
 	 $("#sampletype").val(data[0].sampletype).change();    	  
 	 $("#dispatchedDate").val(data[0].dispatchedDate)    	  
 	  $("#courierName").val(data[0].courierName).change();
 	 $("#trackingNo").val(data[0].trackingNo);
 	$("#grossWeight").val(data[0].grossWeight);
 	 $("#unit").val(data[0].unit);
 	  $("#totalQty").val(data[0].totalQty);
 	 $("#parcel").val(data[0].parcel);
 	 $("#rate").val(data[0].rate);
 	 $("#amount").val(data[0].amount);
 	 $("#deiveryDate").val(data[0].deiveryDate)
 	 $("#delieryTime").val(data[0].delieryTime);
 	 $("#deliveryTo").val(data[0].deliveryTo);
	  $('#exampleModal').modal('hide');
	  
	  
	  $("#save").attr('disabled', true);
	  $("#edit").attr('disabled', false);

}


function editParcel() {
	
	var user = $("#userId").val();
  var styleNo = $("#styleNo").val();
  var itemName = $("#itemName").val();
  var sampletype = $("#sampletype").val();
  
  
  var dispatchedDate = new Date($("#dispatchedDate").val()).toLocaleDateString('fr-CA') ;
  
  
  var courierName = $("#courierName").val();
  var trackingNo = $("#trackingNo").val();
  var grossWeight = $("#grossWeight").val();
  var unit = $("#unit").val();
  var totalQty = $("#totalQty").val();
  var parcel = $("#parcel").val();
  var rate = $("#rate").val();
  var amount = $("#amount").val();
  var deiveryDate = new Date($("#deiveryDate").val()).toLocaleDateString('fr-CA') ;;
  var delieryTime = $("#delieryTime").val();
  var deliveryTo = $("#deliveryTo").val();
  

  

  if (styleNo != 0) {
    $.ajax({
      type: 'GET',
      dataType: 'json',
      url: './editParcel',
      data: {
    	  id:id,
    	   styleNo:styleNo,
    	   itemName:itemName,
    	   sampletype:sampletype,
    	   dispatchedDate:dispatchedDate,
    	   courierName:courierName,
    	   trackingNo:trackingNo,
    	   grossWeight:grossWeight,
    	   unit:unit,
    	   totalQty:totalQty,
    	   parcel:parcel,
    	   rate:rate,
    	   amount:amount,
    	   deiveryDate:deiveryDate,
    	   delieryTime:delieryTime,
    	   deliveryTo:deliveryTo,
      },
      success: function (data) {
    	  if(data=='success'){
    		  alert("Successfully Updated")
    		  $("#save").attr('disabled', false);
    		  $("#edit").attr('disabled', true);
    	  }else{
    		  alert("Parcel Update Failed")
    	  }
      }
    });
  } else {
    
	  alert("Select Style");
  }

}



function parcelReport(id) {
		
	  $.ajax({
	    type: 'POST',
	    dataType: 'json',
	    url: './parcelRepor/'+id,
	    data: {
	    	
	    },
	    success: function (data) {
	    	if(data=='yes'){
				var url = "parcelReportView";
        		window.open(url, '_blank');
			}
	    }
	  });
	}

