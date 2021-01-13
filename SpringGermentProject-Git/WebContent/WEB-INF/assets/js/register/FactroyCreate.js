
window.onload = ()=>{
	document.title = "Factory Create";
	maxFactoryId();
} 

$("#id").attr('disabled', true);


function maxFactoryId(){
	
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './max_factoryId',
		data: {},
		success: function (data) {
			$("#id").val(data);
			getAllFactories();

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


function FactoryCreate(){
	let user=$("#user_hidden").val();

	let factoryid=$("#id").val();
	let factoryname=$("#factory_name").val();
	let telephone=$("#telphone").val();
	let mobile=$("#mobile").val();
	let fax=$("#fax").val();	
	let email=$("#e_mail").val();		
	let bondlicense=$("#bond_license").val();
	let skypeid=$("#skype_id").val();
	let address=$("#address").val();	
	
	
	let bankname=$("#bank_name").val();
	let bankaddress=$("#bank_address").val();
	let aaccounts_no=$("#account_no").val();
	let swiftcode=$("#swift_code").val();
	let accounts_name=$("#account_name").val();	
	let bankcountry=$("#bankcountry").val();
	bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);
	
	console.log("user "+user);
	console.log("bcountry "+bankcountry);

	if (factoryname=='') {
		alert("Factory Name Cannot be Empty");
	}else if (address=='') {
		alert("Factory Address Cannot be Empty");
	}else if (telephone=='' && mobile=='') {
		alert("Telephone and Mobile Cannot be Empty");
	}else{
		$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './insertFactory',
			data: {
				 user:user,
				 factoryid:factoryid,
				 factoryname:factoryname,				 
				 telephone:telephone,
				 mobile:mobile,
				 email:email,
				 fax:fax,
				 skypeid:skypeid,
				 bondlicense:bondlicense,
				 factoryaddress:address,
				 bankname:bankname,
				 bankaddress:bankaddress,
				 accountno:aaccounts_no,
				 swiftcode:swiftcode,
				 accountname:accounts_name,
				 bankcountry:bankcountry
			},
			success: function (data) {
				console.log(data);
				if(data==true){
					alert("Factory Created Successfully");
					reloadPage();
				}else{
					alert("Factory Creation Failed. Could be Duplicate Factory Name Problem");
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


function factorylist(v){

	let value=$(v).val();
	console.log(value);
	$(v).autocomplete({
		source: function (request, response) {
			$.ajax({
				url: "./factorysearch/"+value,
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


function factoryDetails(value){

	//let value=$("#search").val();
	//console.log(value);

	if (value=='') {
		alert("Select Buyer")
	}else{
		//value=value.substring(value.lastIndexOf("*")+1,value.length);
		$.ajax({
			url: "./factoryDetails/"+value,
			type: 'POST',
			dataType: "json",
			data: {

			},
			success: function (data) {

				console.log(data);
				setData(data);
				$("#save").hide();
				$("#edit").show();
			}
		});
	}


}


function setData(data){
	$("#id").val(data[0]);
	$("#factory_name").val(data[1]);
	
	$("#telphone").val(data[2]);
	$("#mobile").val(data[3]);
	$("#fax").val(data[4]);
	$("#e_mail").val(data[5]);
	$("#skype_id").val(data[6]);
	
	$("#address").val(data[7]);
	$("#bond_license").val(data[8]);

	

	$("#bank_name").val(data[9]);
	$("#bank_address").val(data[10]);
	$("#account_no").val(data[11]);
	$("#account_name").val(data[12]);
	$("#swift_code").val(data[13]);
	$("#bankcountry").val(data[14]);
	
	

}


function editFactory(){let user=$("#user_hidden").val();

let factoryid=$("#id").val();
let factoryname=$("#factory_name").val();
let telephone=$("#telphone").val();
let mobile=$("#mobile").val();
let fax=$("#fax").val();	
let email=$("#e_mail").val();		
let bondlicense=$("#bond_license").val();
let skypeid=$("#skype_id").val();
let address=$("#address").val();	


let bankname=$("#bank_name").val();
let bankaddress=$("#bank_address").val();
let aaccounts_no=$("#account_no").val();
let swiftcode=$("#swift_code").val();
let accounts_name=$("#account_name").val();	
let bankcountry=$("#bankcountry").val();
bankcountry=bankcountry.substring(bankcountry.lastIndexOf("*")+1,bankcountry.length);

console.log("user "+user);
console.log("bcountry "+bankcountry);

if (factoryname=='') {
	alert("Factory Name Cannot be Empty");
}else if (address=='') {
	alert("Factory Address Cannot be Empty");
}else if (telephone=='' && mobile=='') {
	alert("Telephone and Mobile Cannot be Empty");
}else{
	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: './editFactory',
		data: {
			 user:user,
			 factoryid:factoryid,
			 factoryname:factoryname,				 
			 telephone:telephone,
			 mobile:mobile,
			 email:email,
			 fax:fax,
			 skypeid:skypeid,
			 bondlicense:bondlicense,
			 factoryaddress:address,
			 bankname:bankname,
			 bankaddress:bankaddress,
			 accountno:aaccounts_no,
			 swiftcode:swiftcode,
			 accountname:accounts_name,
			 bankcountry:bankcountry


		},
		success: function (data) {
			console.log(data);
			if(data==true){
				
				alert("Factory Update Successfully");
				reloadPage();
			}else{
				alert("Factory Creation Failed. Could be Duplicate Factory Name Problem");
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
}}

function getAllFactories(){
	//$("#itemtable").addClass('ac_loading');




	$.ajax({

		type:'POST',
		dataType:'json',
		url:'./getFactories',
		success:function(data)
		{
			
			$("#factorytable").empty();
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

	$("#factorytable").append(rows);
}

function drawRow(rowData,c) {

	//alert(rowData.aquisitionValue);

	let row = $("<tr />")
	row.append($("<td>" + rowData.id+ "</td>"));
	row.append($("<td>" + rowData.name+ "</td>"));
	row.append($("<td>" + rowData.code+ "</td>"));
	row.append($("<td ><i class='fa fa-edit' onclick=\"factoryDetails(" + rowData.id + ")\" class=\"btn btn-primary\" data-toggle=\"modal\"data-target=\"#exampleModalCenter\"> </i></td>"));

	

	return row;
}

$(document).ready(function () {
	  $("#search").on("keyup", function () {
	    let value = $(this).val().toLowerCase();
	    $("#factorytable tr").filter(function () {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});


function reloadPage(){
	location.reload();
}
