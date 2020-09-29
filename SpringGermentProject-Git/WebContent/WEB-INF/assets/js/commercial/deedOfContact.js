


function buyerWisePoLoad(){
	var buyerId=$("#buyerId").val();
	if(buyerId!=0){		

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './buyerWisePoLoad/'+buyerId,
			success: function (data) {
				console.log("dt "+data.result)
				loadPoNo(data.result);


			},
			error: function (jqXHR, textStatus, errorThrown) {
				//alert("Server Error");
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found.');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error.');
				} else if (errorThrown === 'parsererror') {
					alert('Requested JSON parse failed');
				} else if (errorThrown === 'timeout') {
					alert('Time out error');
				} else if (errorThrown === 'abort') {
					alert('Ajax request aborted ');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}

			}
		});
	}
}

function loadPoNo(data){

	var itemList = data;
	var options = "<option id='purchaseOrder' value='0' selected>Select Purchase Order</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='purchaseOrder' value='"+itemList[i].id+"'>"+itemList[i].name+"</option>";
	};
	document.getElementById("purchaseOrder").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	//$('#purchaseOrder').val(poNoValue).change();
	poNoValue=0;
}



function poWiseStyles(){

	var po=$("#purchaseOrder").val();

	console.log("po "+po)
	if(po!=0){		

		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './poWiseStyles/'+po,
			data: {




			},
			success: function (data) {
				console.log("dt "+data.result)
				loadStyles(data.result);


			},
			error: function (jqXHR, textStatus, errorThrown) {
				//alert("Server Error");
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found.');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error.');
				} else if (errorThrown === 'parsererror') {
					alert('Requested JSON parse failed');
				} else if (errorThrown === 'timeout') {
					alert('Time out error');
				} else if (errorThrown === 'abort') {
					alert('Ajax request aborted ');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}

			}
		});
	}
}

function loadStyles(data){

	var itemList = data;
	var options = "<option id='itemType' value='0' selected>Select Style</option>";
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='itemType' value='"+itemList[i].id+"'>"+itemList[i].name+"</option>";
	};
	document.getElementById("styleNo").innerHTML = options;
	$('.selectpicker').selectpicker('refresh');
	
	//$('#styleNo').val(styleValue).change();
	styleValue=0;

}

function styleWiseItems(){


	var buyerorderid=$("#purchaseOrder").val();
	var style=$("#styleNo").val();

/*	alert("buyerorderid "+buyerorderid);
	alert("style "+style);*/

	if(style!=0 && buyerorderid!='0'){		

		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './stylewiseitems',
			data: {
				buyerorderid:buyerorderid,
				style:style

			},
			success: function (data) {

				loatItems(data.result);


			},
			error: function (jqXHR, textStatus, errorThrown) {
				//alert("Server Error");
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found.');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error.');
				} else if (errorThrown === 'parsererror') {
					alert('Requested JSON parse failed');
				} else if (errorThrown === 'timeout') {
					alert('Time out error');
				} else if (errorThrown === 'abort') {
					alert('Ajax request aborted ');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}

			}
		});


	}



}


function loatItems(data){

	var itemList = data;
	var options = "<option id='itemType' value='0' selected>Select Item Type</option>";
	
	
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='itemType'  value='"+itemList[i].id+"'>"+itemList[i].name+"</option>";
	};
	document.getElementById("itemName").innerHTML = options;
	//$('.selectpicker').selectpicker('refresh');
	
	//itemValue=0;
	
	
	var options1 = "<option id='goodsDescription' value='0' selected>Select Item Type</option>";
	for(var i=0;i<length;i++) {
		options1 += "<option id='goodsDescription'  value='"+itemList[i].id+"'>"+itemList[i].name+"</option>";
	};
	document.getElementById("goodsDescription").innerHTML = options1;
	$('.selectpicker').selectpicker('refresh');
	//$('#itemName').val(itemValue).change();
	//$('#goodsDescription').val(itemValue).change();
	itemValue=0;

}

function styleItemsWiseColor(){
	//var buyerorderid=$("#purchaseOrder").val();
	var styleId=$("#styleNo").val();
	var itemId =$('#itemName').val();
	
	
	if(itemId!='0'){
		$.ajax({
			type: 'GET',
			dataType: 'json',
			url: './getStyleItemWiseColor/',
			data:{
				//buyerorderid:buyerorderid,
				styleId: styleId,
		        itemId: itemId
			},
			success: function (data) {
				//alert(data);
				loadColors(data)
			},
			error: function (jqXHR, textStatus, errorThrown) {
				//alert("Server Error");
				if (jqXHR.status === 0) {
					alert('Not connect.\n Verify Network.');
				} else if (jqXHR.status == 404) {
					alert('Requested page not found.');
				} else if (jqXHR.status == 500) {
					alert('Internal Server Error.');
				} else if (errorThrown === 'parsererror') {
					alert('Requested JSON parse failed');
				} else if (errorThrown === 'timeout') {
					alert('Time out error');
				} else if (errorThrown === 'abort') {
					alert('Ajax request aborted ');
				} else {
					alert('Uncaught Error.\n' + jqXHR.responseText);
				}

			}
		});
	}

}


function loadColors(data){


	var itemList = data.colorList;
	var options = "<option id='itemType' value='0' selected>Select Color</option>";
	
	
	var length = itemList.length;
	for(var i=0;i<length;i++) {
		options += "<option id='itemColor'  value='"+itemList[i].colorId+"'>"+itemList[i].colorName+"</option>";
	};
	document.getElementById("itemColor").innerHTML = options;

	$('.selectpicker').selectpicker('refresh');
	$('#itemColor').val(itemValue).change();
	
	itemValue=0;


}

function insert() {
	
  var userid = $("#userId").val();
  var buyerId = $("#buyerId").val();
  var contractId = $("#contractId").val();
  var receivedDate = $("#receivedDate").val();
  var purchaseOrder = $("#purchaseOrder").val();
  var expiryDate =new Date($("#expiryDate").val()).toLocaleDateString('fr-CA')
  var styleNo = $("#styleNo").val();
  var ammendmentDate = $("#ammendmentDate").val();
  var courieer = $("#courieer").val();
  var itemName = $("#itemName").val();
  var extendedDate = new Date($("#extendedDate").val()).toLocaleDateString('fr-CA');  
  var forwardAddress = $("#forwardAddress").val();
  var itemColor = $("#itemColor").val();
  var exportDate = new Date($("#exportDate").val()).toLocaleDateString('fr-CA'); ;
  
  
  
  var goodsDescription = $("#goodsDescription").val();
  var rollQty = $("#rollQty").val();  
  var invoiceNumber = $("#invoiceNumber").val();
  var unMakeingDate = new Date($("#unMakeingDate").val()).toLocaleDateString('fr-CA');
  var ctnQty = $("#ctnQty").val();
  var invoiceDate = new Date($("#invoiceDate").val()).toLocaleDateString('fr-CA');
  var unAmmendment = $("#unAmmendment").val();
  var grossWeight = $("#grossWeight").val();
  var awbNumber = $("#awbNumber").val();
  var unSubmitDate = $("#unSubmitDate").val();
  var netWeight = $("#netWeight").val();
  var blDate = new Date($("#blDate").val()).toLocaleDateString('fr-CA');;
  var UNReceivedDate = new Date($("#UNReceivedDate").val()).toLocaleDateString('fr-CA');;
  var unit = $("#unit").val();  
  var trackingNumber = $("#trackingNumber").val();
  var unHoverDate = new Date($("#unHoverDate").val()).toLocaleDateString('fr-CA');;
  var unitPrice = $("#unitPrice").val();
  var shipperAddress = $("#shipperAddress").val();
  var birthingDate =new Date( $("#birthingDate").val()).toLocaleDateString('fr-CA');;
  
  
  
  var currency = $("#currency").val();
  var amount = $("#amount").val();
  var consignAddress = $("#consignAddress").val();  
  var etdDate =new Date( $("#etdDate").val()).toLocaleDateString('fr-CA');;
  var cfHandoverDate =new Date( $("#cfHandoverDate").val()).toLocaleDateString('fr-CA');;
  var masterLC = $("#masterLC").val(); 
  var etaDate = new Date($("#etaDate").val()).toLocaleDateString('fr-CA');; 
  var cfAddress = $("#cfAddress").val();
  var bblc = $("#bblc").val();
  var etcDate = new Date($("#etcDate").val()).toLocaleDateString('fr-CA');;
  var telephone = $("#telephone").val();
  var vvsselName = $("#vvsselName").val();
  var clearDate = new Date($("#clearDate").val()).toLocaleDateString('fr-CA');;
  var mobile = $("#mobile").val();
  var invoiceQty = $("#invoiceQty").val();  
  var contactNo = $("#contactNo").val();
  var faxNo = $("#faxNo").val();
  var onBoardDate =new Date( $("#onBoardDate").val()).toLocaleDateString('fr-CA');;
  var readyDate =new Date( $("#readyDate").val()).toLocaleDateString('fr-CA');;
  var contactPerson = $("#contactPerson").val();  
  var submitDate =new Date( $("#submitDate").val()).toLocaleDateString('fr-CA');;
  


 
    $.ajax({
      type: 'POST',
      dataType: 'json',
      url: './insert',
      data: {
    	  
    	   userid: userid,
    	   buyerId: buyerId,
    	   contractId:contractId,
    	   receivedDate: receivedDate,
    	   purchaseOrder:purchaseOrder,
    	   expiryDate:expiryDate,
    	   styleNo: styleNo,
    	   ammendmentDate: ammendmentDate,
    	   courieer: courieer,
    	   itemName:itemName,
    	   extendedDate: extendedDate,
    	   forwardAddress:forwardAddress,
    	   itemColor: itemColor,
    	   exportDate: exportDate,
    	  
    	  
    	  
    	   goodsDescription:goodsDescription,
    	   rollQty:rollQty ,
    	   invoiceNumber:invoiceNumber,
    	   unMakeingDate: unMakeingDate,
    	   ctnQty:ctnQty,
    	   invoiceDate:invoiceDate,
    	   unAmmendment:unAmmendment,
    	   grossWeight: grossWeight,
    	   awbNumber:awbNumber,
    	   unSubmitDate:unSubmitDate,
    	   netWeight: netWeight,
    	   blDate :blDate,
    	   UNReceivedDate:UNReceivedDate,
    	   unit : unit,
    	   trackingNumber:trackingNumber,
    	   unHoverDate:unHoverDate,
    	   unitPrice :unitPrice,
    	   shipperAddress:shipperAddress,
    	   birthingDate:birthingDate,
    	  
    	  
    	  
    	   currency:currency,
    	   amount:amount,
    	   consignAddress:consignAddress,
    	   etdDate:etdDate,
    	   cfHandoverDate:cfHandoverDate,
    	   masterLC:masterLC,
    	   etaDate:etaDate,
    	   cfAddress:cfAddress,
    	   bblc:bblc,
    	   etcDate:etcDate,
    	   telephone:telephone,
    	   vvsselName: vvsselName,
    	   clearDate:clearDate,
    	   mobile:mobile,
    	   invoiceQty:invoiceQty,
    	   contactNo:contactNo,
    	   faxNo:faxNo,
    	   onBoardDate:onBoardDate,
    	   readyDate:readyDate,
    	   contactPerson: contactPerson,
    	   submitDate:submitDate,
    	  
        
      },
      success: function (data) {
    	  
      }
    });
 

}



function deedOfContractReport(contractid) {
	
	console.log(" contractid "+contractid)
		
	  $.ajax({
	    type: 'POST',
	    dataType: 'json',
	    url: './deedofcontract/'+contractid,
	    data: {
	    	
	    },
	    success: function (data) {
	    	if(data=='yes'){
				var url = "deedofcontractview";
        		window.open(url, '_blank');
			}
	    }
	  });
	}
