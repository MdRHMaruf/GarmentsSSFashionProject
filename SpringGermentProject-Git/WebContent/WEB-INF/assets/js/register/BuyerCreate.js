
$("#save").attr('disabled', false);
$("#edit").attr('disabled', true);
$("#buyer_id").attr('disabled', true);

function maxbuyerId(){
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './max_buyerId',
		data: {




		},
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

	var value=$(v).val();
	console.log(value);
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
					console.log("abc="+data)
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
	var user=$("#user_hidden").val();

	var buyerid=$("#buyer_id").val();
	var buyername=$("#buyer_name").val();
	var buyercode=$("#buyer_code").val();
	buyercode==''?buyercode='':buyercode=$("#buyer_code").val();
	
	
	var buyeraddress=$("#buyer_address").val();
	var consigneeaddress=$("#consignee_address").val();
	var notifyaddress=$("#notify_address").val();
	
	var country=$("#countries1").val();
	var country=country.substring(country.lastIndexOf("*")+1,country.length);
	
	var telephone=$("#telphone").val();
		
	
	var mobile=$("#mobile").val();
	
	var fax=$("#fax").val();
	
	var email=$("#e_mail").val();
	
	var skypeid=$("#skype_id").val();
	
	var bankname=$("#bank_name").val();
	
	var bankaddress=$("#bank_address").val();
	
	var swiftcode=$("#swift_code").val();
	
	var bankcountry=$("#bank_country").val();
	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);
	
	console.log("user "+user);
	console.log("bcountry "+bankcountry);

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
				console.log(data);
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

	var value=$(v).val();
	console.log(value);
	$(v).autocomplete({
		source: function (request, response) {
			$.ajax({
				url: "./buerySearch/"+value,
				type: 'GET',
				dataType: "json",
				data: {

				},
				success: function (data) {
					console.log("abc="+data)
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

	//var value=$("#buyer_search").val();
	//console.log(value);

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

				console.log(data);
				setData(data);
				$("#save").attr('disabled', true);
				$("#edit").attr('disabled', false);

			}
		});
	}


}


function setData(data){
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
	
	var user=$("#user_hidden").val();

	var buyerid=$("#buyer_id").val();
	var buyername=$("#buyer_name").val();
	var buyercode=$("#buyer_code").val();
	var buyeraddress=$("#buyer_address").val();
	var consigneeaddress=$("#consignee_address").val();
	var notifyaddress=$("#notify_address").val();
	var country=$("#countries1").val();
	var country=country.substring(country.lastIndexOf("*")+1,country.length);
	var telephone=$("#telphone").val();
	var mobile=$("#mobile").val();
	var fax=$("#fax").val();
	var email=$("#e_mail").val();
	var skypeid=$("#skype_id").val();

	var bankname=$("#bank_name").val();
	var bankaddress=$("#bank_address").val();
	var swiftcode=$("#swift_code").val();
	var bankcountry=$("#bank_country").val();
	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);

	console.log("user "+user);
	console.log("bcountry "+bankcountry);

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
				console.log(data);
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




	$.ajax({

		type:'POST',
		dataType:'json',
		url:'./getAllBuyers',
		success:function(data)
		{
			
			$("#buyerstable").empty();
			patchdata(data.result);
		}


	});


}


function patchdata(data){
	var rows = [];


	for (var i = 0; i < data.length; i++) {
		//ert("ad "+data[i].aquisitionValue);
		rows.push(drawRow(data[i],i+1));

	}

	$("#buyerstable").append(rows);
}

function drawRow(rowData,c) {

	//alert(rowData.aquisitionValue);

	var row = $("<tr />")
	row.append($("<td>" + rowData.id+ "</td>"));
	row.append($("<td>" + rowData.name+ "</td>"));
	row.append($("<td>" + rowData.code+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=\"BuyerDetails(" + rowData.id + ")\" class=\"btn btn-primary\" data-toggle=\"modal\"data-target=\"#exampleModalCenter\"> </i></td>"));
	

	return row;
}


$(document).ready(function () {
	  $("#search").on("keyup", function () {
	    var value = $(this).val().toLowerCase();
	    $("#buyerstable tr").filter(function () {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});

function reloadPage(){
	location.reload();
}