$("#save").show();
$("#edit").hide();
$("#buyer_id").attr('disabled', true);



window.onload = ()=>{
	document.title = "Buyer Create";
	maxbuyerId();
} 

function maxbuyerId(){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './max_buyerId',
		data: {},
		success: function (data) {
			$("#buyer_id").val(data);
			GetAllBuyers();

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

function CountriesSearch(v){

	let value=$(v).val();
	$(v).autocomplete({
		source: function (request, response) {
			$.ajax({
				url: "./countryNames/"+value,
				type: 'GET',
				dataType: "json",
				data: {
					key:value
				},
				success: function (data) {
					response(data);
				}
			});
			//   $("#tag").removeClass('ac_loading');
		},
		select: function (e, ui) {
		}
	});

}



function insertBuyer(){
	let user=$("#userId").val();
	console.log("user id=",user);
	let buyerid=$("#buyer_id").val();
	let buyername=$("#buyer_name").val();
	let buyercode=$("#buyer_code").val();
	buyercode==''?buyercode='':buyercode=$("#buyer_code").val();
	
	
	let buyeraddress=$("#buyer_address").val();
	let consigneeaddress=$("#consignee_address").val();
	let notifyaddress=$("#notify_address").val();
	
	let country=$("#countries1").val();

	country=country.substring(country.lastIndexOf("*")+1,country.length);
	
	let telephone=$("#telphone").val();
		
	
	let mobile=$("#mobile").val();
	let fax=$("#fax").val();
	let email=$("#e_mail").val();
	let skypeid=$("#skype_id").val();	
	let bankname=$("#bank_name").val();
	let bankaddress=$("#bank_address").val();
	let swiftcode=$("#swift_code").val();
	let bankcountry=$("#bank_country").val();

	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);


	if (buyername=='') {
		alert("Buyer Name Cannot be Empty");
	}else if (buyeraddress=='') {
		alert("Buyer Address Cannot be Empty");
	}else if (country=='') {
		alert("Country Cannot be Empty");
	}else if (telephone=='' && mobile=='') {
		alert("Telephone and Mobile Cannot be Empty");
	}else if (email=='') {
		alert("E-Mail Address Cannot be Empty");
	}else if (buyeraddress=='') {
		alert("Buyer Address Cannot be Empty");
	}else{
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './inserBuyer',
			data: {
				user:user,
				buyername:buyername,
				buyerid:buyerid,
				buyercode:buyercode,
				buyerAddress:buyeraddress,
				consigneeAddress:consigneeaddress,
				notifyAddress:notifyaddress,
				country:country,
				telephone:telephone,
				mobile:mobile,
				email:email,
				fax:fax,				
				skypeid:skypeid,
				bankname:bankname,
				bankaddress:bankaddress,
				swiftcode:swiftcode,
				bankcountry:bankcountry
			},
			success: function (data) {
				
				if(data==true){
					alert("Buyer Created Successfully");
					reloadPage();
				}else{
					alert("Buyer Creation Failed. Could be Duplicate Buyer Name Problem");
				}

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


function BuyerList(v){

	let value=$(v).val();
	
	$(v).autocomplete({
		source: function (request, response) {
			$.ajax({
				url: "./buerySearch/"+value,
				type: 'GET',
				dataType: "json",
				data: {

				},
				success: function (data) {
					response(data);
				}
			});
			//   $("#tag").removeClass('ac_loading');
		},
		select: function (e, ui) {
		}
	});

}

function BuyerDetails(value){

	if (value=='') {
		alert("Select Buyer")
	}else{
		//value=value.substring(value.lastIndexOf("*")+1,value.length);
		$.ajax({
			url: "./bueryDetails/"+value,
			type: 'POST',
			dataType: "json",
			data: {

			},
			success: function (data) {
				$("#save").hide();
				$("#edit").show();
				setData(data);
				

			}
		});
	}


}

function createNewEvent(){
	$("#save").show();
	$("#edit").hide();
}


function setData(data){
	console.log(data);
	$("#buyer_id").val(data[0]);
	$("#buyer_name").val(data[1]);
	$("#buyer_code").val(data[2]);
	$("#buyer_address").val(data[3]);
	$("#consignee_address").val(data[4]);
	$("#notify_address").val(data[5]);
	$("#countries1").val(data[6]);

	$("#telphone").val(data[7]);
	$("#mobile").val(data[8]);
	$("#fax").val(data[10]);
	$("#e_mail").val(data[9]);
	$("#skype_id").val(data[11]);

	$("#bank_name").val(data[12]);
	$("#bank_address").val(data[13]);
	$("#swift_code").val(data[14]);
	$("#bank_country").val(data[15]);
	
	

}


function editBuyer(){
	
	let user=$("#userId").val();

	let buyerid=$("#buyer_id").val();
	let buyername=$("#buyer_name").val();
	let buyercode=$("#buyer_code").val();
	let buyeraddress=$("#buyer_address").val();
	let consigneeaddress=$("#consignee_address").val();
	let notifyaddress=$("#notify_address").val();
	let country=$("#countries1").val();
	country=country.substring(country.lastIndexOf("*")+1,country.length);
	let telephone=$("#telphone").val();
	let mobile=$("#mobile").val();
	let fax=$("#fax").val();
	let email=$("#e_mail").val();
	let skypeid=$("#skype_id").val();

	let bankname=$("#bank_name").val();
	let bankaddress=$("#bank_address").val();
	let swiftcode=$("#swift_code").val();
	let bankcountry=$("#bank_country").val();
	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);

	if (buyername=='') {
		alert("Buyer Name Cannot be Empty");
	}else if (buyeraddress=='') {
		alert("Buyer Address Cannot be Empty");
	}else if (country=='') {
		alert("Country Cannot be Empty");
	}else if (telephone=='' && mobile=='') {
		alert("Telephone and Mobile Cannot be Empty");
	}else if (email=='') {
		alert("E-Mail Address Cannot be Empty");
	}else if (buyeraddress=='') {
		alert("Buyer Address Cannot be Empty");
	}else{
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './editBuyer',
			data: {
				user:user,
				buyername:buyername,
				buyerid:buyerid,
				buyercode:buyercode,
				buyerAddress:buyeraddress,
				consigneeAddress:consigneeaddress,
				notifyAddress:notifyaddress,
				country:country,
				telephone:telephone,
				mobile:mobile,
				email:email,
				fax:fax,				
				skypeid:skypeid,
				bankname:bankname,
				bankaddress:bankaddress,
				swiftcode:swiftcode,
				bankcountry:bankcountry
			},
			success: function (data) {	
				if(data==true){
					alert("Buyer Edited Successfully");		
					reloadPage();		
				}else{
					alert("Buyer Edition Failed. Could be Duplicate Buyer Name Problem");
				}
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


function GetAllBuyers(){
	//$("#itemtable").addClass('ac_loading');

	let user=$("#userId").val();


	$.ajax({

		type:'GET',
		dataType:'json',
		url:'./getAllBuyers/'+user,
		success:function(data)
		{
			
			$("#buyerstable").empty();
			patchdata(data.result);
		}


	});


}


function patchdata(data){
	let rows = [];


	for (let i = 0; i < data.length; i++) {
		//ert("ad "+data[i].aquisitionValue);
		rows.push(drawRow(data[i],i+1));

	}

	$("#buyerstable").append(rows);
}

function drawRow(rowData,c) {

	//alert(rowData.aquisitionValue);

	let row = $("<tr />")
	row.append($("<td>" + rowData.id+ "</td>"));
	row.append($("<td>" + rowData.name+ "</td>"));
	row.append($("<td>" + rowData.code+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=\"BuyerDetails(" + rowData.id + ")\" class=\"btn btn-primary\" data-toggle=\"modal\"data-target=\"#exampleModalCenter\"> </i></td>"));
	

	return row;
}


$(document).ready(function () {
	  $("#search").on("keyup", function () {
	    let value = $(this).val().toLowerCase();
	    $("#buyerstable tr").filter(function () {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});

function reloadPage(){
	location.reload();
}

let idListMicro = ["buyer_name","buyer_code","buyer_address","consignee_address","notify_address","countries1",
	"telphone","mobile","fax","e_mail","skype_id","bank_name","bank_address","swift_code","bank_country","btnSave"];
idListMicro.forEach((id,index)=>{
  $('#'+id).keyup(function(event){
    if(event.keyCode ===13){
      event.preventDefault();
      $("#"+idListMicro[index+1]).focus();
    }
  });
})

