
$("#sp").click(function(){


	if($(this).is(":checked")){
		$("#password").attr('type','text');
	}
	else{
		$("#password").attr('type','password');
	}

});

$(document).on("click",".permissionmoduleg",function(){

	type_per();


});



$(document).on("click",".menu_checked",function(){

	var mid=$(this).attr("data-id");
	if($(this).is(":checked")){
		$(".sub_menu_checked-"+mid).prop("checked",true);
	}
	else{
		$(".sub_menu_checked-"+mid).prop("checked",false);

	}

})



function type_per() {


	var id = $("#type").val();
	var type_u = $("#type_u").val();

	if (id == 3 || (typeof type_u != "undefined" && type_u == 3)) {



		var list = [];
		var i = 0;
		$(".permissionmoduleg").each(function () {
			if ($(this).is(":checked")) {
				var m = $(this).val();
				var moduleid=m.substring(m.indexOf(":")+1,m.length);
				list[i++] = [moduleid];
			}
		});


		var combineModuleList = JSON.stringify(list);

		if (i == 0) {
			$("#per").empty();
			alert("Please select at least one module....")
		}
		else {
			$(".img").show();
			var mydada= {
					user:1,
					id: id,
					combineModuleList: combineModuleList,
			};

			$.ajax({
				type: 'GET',
				url: './user_access_menu',
				data: mydada,
				success: function (data) {
					$("#per").empty();
					var count=0;
					$.each(data.result, function (key, val) {


						var unChecked = "checked";
						var addChecked = "checked";
						var editChecked = "checked";
						var deleteChecked = "checked";
						if (val.sub == '0' || val.sub == '') {
							unChecked = "";
							addChecked = "";
							editChecked = "";
							deleteChecked = "";
						}
						if (typeof isUpdate == "undefined" || isUpdate == '')
							unChecked = "checked";
						addChecked = "checked";
						editChecked = "checked";
						deleteChecked = "checked";

						var firstrow="";
						if(count==0){
							firstrow="<tr>" +
							"<th style='width:20%'>Menu</th>" +
							"<th style='width:40%;'>Access Sub Menu</th>" +
							"<th style='width:10%;'>Add</th>" +
							"<th style='width:10%;'>Edit</th>" +
							"<th style='width:10%;'>Delete</th>" +
							"</tr>";
						}
						else{
							firstrow="";
						}


						var before_stuff="<div class='row'>" +
						"<table class='table'>"+firstrow 
						/*								"<tr>" +
								"<th >Checked</th>" +
								"<th width='200px'>Access Sub Menu</th>" +
								"<th>Add</th>" +
								"<th>Edit</th>" +
								"<th>Delete</th>" +
								"</tr>" */

						var stuff="<tr>" +
						"<td style='width:20%' id='" + val.id + "p_menu_checked'></td>" +
						"<td style='width:40%;' id='" + val.id + "p'></td>" +
						"<td style='width:10%;' id='" + val.id + "p_add'></td>" +
						"<td style='width:10%;' id='" + val.id + "p_edit'></td>" +
						"<td style='width:10%;' id='" + val.id + "p_delete'></td>" +
						"</tr>" +
						"</table>"

						+ "</div>";


						$("#per").append(before_stuff+stuff);
						count++;


						s_stuff="";
						add_stuff="";
						edit_stuff="";
						menu_stuff="";
						delete_stuff="";
						menu_checked_stuff="";
						$.each(val.sub, function (key, sv) {
							// alert(sv.id);
							s_stuff = s_stuff + "<input  onclick=t2() class='sub_menu_checked-"+val.id+"' sub-data-id='"+sv.id+"' name='active' id='" + sv.id + "' value='"+ val.moduleid + ":" + val.id + ":" + sv.id+ "' type='checkbox' " + unChecked + " />" + sv.sub + ""+"</br>";
							add_stuff=add_stuff+"<input  onclick=t2() class='sub_menu_checked-add' sub-data-add-id='"+sv.id+"' name='active' id='" + sv.id + "' value='1' type='checkbox' " + addChecked + " />"+"</br>";
							edit_stuff=edit_stuff+"<input onclick=t2() class='sub_menu_checked-edit' sub-data-edit-id='"+sv.id+"' name='active' id='" + sv.id + "' value='" + val.id + ":" + sv.id + "' type='checkbox' " + editChecked + " />"+"</br>";
							delete_stuff=delete_stuff+"<input  onclick=t2() class='sub_menu_checked-delete' sub-data-delete-id='"+sv.id+"' name='active' id='" + sv.id + "' value='" + val.id + ":" + sv.id + "' type='checkbox' " + deleteChecked + " />"+"</br>";
							menu_checked_stuff="<input class='menu_checked' data-id='"+val.id+"'  value='" + val.id + ":0' name='active' id='" + val.id + "c' type='checkbox' " + unChecked + ">" + val.head ;

						});

						document.getElementById(val.id + "p_menu_checked").innerHTML = menu_checked_stuff;
						document.getElementById(val.id + "p").innerHTML = s_stuff;
						document.getElementById(val.id + "p_add").innerHTML = add_stuff;
						document.getElementById(val.id + "p_edit").innerHTML = edit_stuff;
						document.getElementById(val.id + "p_delete").innerHTML = delete_stuff;


						var uid = $("#user_hidden").val();
						if (typeof uid == "undefined") {
							uid = 0;
						}


						$(".img").hide();


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


		}


	}
	else {


		$("#per").empty();
	}

}

$(document).on("click", ".permissionmoduleg", function () {

	type_per();


});




function t2() {


	$("#sub").hide();
	$("#con").show();
}


//this functionn use for the usualy create and update the user in the function
function create_user() {


	var active = $('input[name=active]:checked').val();
	var user = $("#user").val();
	var userId = $("#userId").val();
	var pass = $("#password").val();
	var type = $("#type").val();
	var shop = $("#shop").val();
	var user_title = $("#user_title").val();



	//var module=$("#module").val();
	var permissionmoduleg = document.getElementsByName('permissionmoduleg');

	var permissionware = document.getElementsByName('permissionware');

	var selectedItemsModule = [];
	for (var i = 0; i < permissionmoduleg.length; i++) {
		if (permissionmoduleg[i].type == 'checkbox' && permissionmoduleg[i].checked == true) {
			selectedItemsModule.push(permissionmoduleg[i].value);
		}
	}
	

	var selectedItemsWare = [];
	for (var i = 0; i < permissionware.length; i++) {
		if (permissionware[i].type == 'checkbox' && permissionware[i].checked == true) {
			selectedItemsWare.push(permissionware[i].value);
		}
	}
	


	if (user == '' || pass == '' || shop == '') {

		alert('information not complete');


	}
	else {

		if (parseInt(type)==3) {

			var accesslist = [];
			var i = 0;

			$(".menu_checked").each(function () {
				if ($(this).is(":checked")) {

					var id=$(this).attr("data-id");

					$(".sub_menu_checked-"+id).each(function(){

						if($(this).is(":checked")){

							var headsub = $(this).val();

							var add=0;
							var edit=0;
							var del=0;
							var k=0;
							$(".sub_menu_checked-add").each(function(){

								if(k==i){
									add=":"+getAccess(this);
								}

								k++;
							});

							k=0;
							$(".sub_menu_checked-edit").each(function(){

								if(k==i){
									edit=":"+getAccess(this);
								}

								k++;
							});

							k=0;
							$(".sub_menu_checked-delete").each(function(){

								if(k==i){
									del=":"+getAccess(this);
								}

								k++;
							});

							var value=headsub+add+edit+del;
							accesslist[i++] = [value];
						}

					});
				}
			});


			var valuelist="["+accesslist+"]";
			var modulelist="["+selectedItemsModule+"]";
			var warelist="["+selectedItemsWare+"]";


			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: './addUser',
				data: {
					accesslist:valuelist,active: active, user: user,userId:userId, password: pass, type: type,
					shop: shop, selectedItemsModule: modulelist,selectedItemsWare:warelist,
					user_title: user_title,
				},
				success: function (data) {
					alert(data);
					window.location =  "user_create";

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

		else {


			$.ajax({
				type: 'POST',
				dataType: 'json',
				url: li + 'user_access/addUser',
				data: {
					active: active, user: user, pass: pass, type: type,
					shop: shop, selectedItemsModule: selectedItemsModule,
					user_title: user_title,
				},
				success: function (data) {
					if (data.id == 1) {

						alert('user name already created');

					}
					else {

						alert('inserted');

						window.location = li + "admin/create_user";

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



}


function getAccess(v){
	var a=0;
	if($(v).is(":checked")){

		a= 1;

	}
	else{
		a=0;
	}
	return a;
}

function getParent(id) {


	var lin = "";
	if (id == 0)
		lin = "user_access/menu/";

	else
		lin = "user_access/sub_menu/";

	var k = li + "" + lin;

	$("#modals").dialog({
		modal: true,
		dialogClass: 'noTitleStuff'
	});
	$(".img").show();

	var user_id = 0;

	var list = [];
	var i = 0;
	$(".permissionmoduleg").each(function () {
		if ($(this).is(":checked")) {
			var m = $(this).val();
			list[i++] = [m];
		}
	});

	var combineModuleList = JSON.stringify(list);


	$.ajax({
		type: 'POST',
		dataType: 'json',
		url: k,
		data: {id: id, user_id: user_id, combineModuleList: combineModuleList},
		success: function (data) {


			addData(data, id);

			$(".img").hide();
			$("#modals").dialog("close");

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


	//return incre;


}

function addData(data, id) {

	if (id == 0) {

		$.each(data.posts, function (key, val) {

			if (document.getElementById(val.id + "c").checked) {

				var c = $("#" + val.id + "c").val();
				t[incre] = c;
				getParent(val.id);

			}
			else {

				t[incre] = 0 + ":" + 0;


			}

			incre++;
		});

	}
	else {


		$.each(data.posts, function (key, val) {


			if (document.getElementById(val.id).checked) {

				var c = $("#" + val.id).val();


				t[incre] = c;

				//alert(c);


			}
			else {


				t[incre] = 0 + ":" + 0;

			}
			incre++;

		});
	}
}


