
$("#add_submenu_module").change(function(){
	$("#add_submenu_main_menu").empty();
    $('#add_submenu_main_menu')
    .append($("<option></option>")
    .attr("value",0)
    .text('Select main menu'));

	
	var module=$("#add_submenu_module").val();
	$.ajax({
		url: "./showModuleWiseMenu/"+module,
		type: "GET",
		success: function(data) {
			var datalist = data.result;
			
			
			
	        $.each(datalist, function (index) {
	        	
	        $('#add_submenu_main_menu')
	          .append($("<option></option>")
	          .attr("value", datalist[index].id)
	          .text(datalist[index].menuname));
	        
	        });
	        
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
	});

function addWare() {	

	var name=$("#wname").val();
	var theme=$("#wtheme").val();
	var phone=$("#wphone").val();
	var vat=$("#wvat").val();
	var address=$("#waddress").val();

	$.ajax({
		url: "./addWare",
		data: {name:name, theme:theme, phone:phone, vat: vat,address: address},
		type: "POST",
		success: function(data) {
			alert("Warehouse create successfully");
			datasent();
			$("#modalWare").modal("hide");
			wareClear();

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

function datasent() {	

	var name=$("#wname").val();
	var theme=$("#wtheme").val();
	var phone=$("#wphone").val();
	var vat=$("#wvat").val();
	var address=$("#waddress").val();

	$.ajax({
		url: "./getNewStore",
		data: {name:name, theme:theme, phone:phone, vat: vat,address: address},
		type: "GET",
		success: function(data) {
			location.reload(true);

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


function wareClear() {
	$("#wname").val('');
	$("#wtheme").val('');
	$("#wphone").val('');
	$("#waddress").val('');
}

function addStore() {	

	var name=$("#s_name").val();
	var remark=$("#s_remarks").val();
	var ware=$("#s_ware").val();


	$.ajax({
		url: "./addStore",
		data: {name:name,  remark:remark, ware: ware},
		type: "POST",
		success: function(data) {
			alert("Store create successfully");
			$("#modalStore").modal("hide");
			storeClear();
			location.reload(true);
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

function storeClear() {
	$("#s_name").val('');
	$("#s_remarks").val('');
	$("#s_ware").val('');
}

$("#sp").click(function(){

	if($(this).is(":checked")){
		$("#password").attr('type','text');
	}
	else{
		$("#password").attr('type','password');
	}

});


function addModule() {	

	var name=$("#s_modulename").val();
	var active = $('input[name=module_active]:checked').val();
	var ware=$("#s_ware").val();
	var user=$("#user_hidden").val();

	$.ajax({
		url: "./addModule",
		data: {user:user,name:name,active:active, ware: ware},
		type: "POST",
		success: function(data) {
			alert("Module create successfully");
			$("#modalModule").modal("hide");
			moduleClear();
			location.reload(true);
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

function moduleClear(){
	$("#s_modulename").val('');
	$("#s_ware").val('');
}

function addMenu() {	


	var name=$("#main_menu_name").val();
	var module=$("#main_menu_module").val();
	var user=$("#user_hidden").val();



	$.ajax({
		url: "./addMenu",
		data: {user:user,name:name,module:module},
		type: "POST",
		success: function(data) {
			alert("Menu create successfully");
			$("#modalMenu").modal("hide");
			menuClear();
			location.reload(true);
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

function menuClear(){
	$("#main_menu_name").val('');
	$("#main_menu_module").val('');
}


function addSubMenu() {	

	var name=$("#submenu_name").val();
	var menu=$("#add_submenu_main_menu").val();
	var module=$("#add_submenu_module").val();
	var user=$("#user_hidden").val();
	var links=$("#submenu_link").val();

	$.ajax({
		url: "./addSubMenu",
		data: {user:user,name:name,links:links,menu:menu,module:module},
		type: "POST",
		success: function(data) {
			alert("SubMenu create successfully");
			$("#modalSubMenu").modal("hide");
			SubMenuClear();
			location.reload(true);
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

function SubMenuClear(){
	$("#submenu_name").val('');
	$("#add_submenu_main_menu").val('');
	$("#add_submenu_module").val('');
	$("#submenu_link").val('');
}

function seatlist(){
	alert("list");
}