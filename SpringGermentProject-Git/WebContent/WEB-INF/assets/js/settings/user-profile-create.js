window.onload = ()=>{

    document.title = "User Profile Create";

    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: './allEmployee',
        data: {
        },
        success: function (data) {
          let employeeCode = data.result.map(employee=> employee.EmployeeCode)

          $("#employeeId").autocomplete({
            source: employeeCode
        });
        }
      });
};

function employeeSearch(){
    let employeeId = $("#employeeId").val();
    if(employeeId != ''){
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: './getEmployeeInfoByEmployeeCode',
            data: {
                employeeCode : employeeId
            },
            success: function(data){
                console.log("employee =",data);
                let employeeInfo = data.employeeInfo;
                $("#name").val(employeeInfo.employeeName);
                $("#employeeAutoId").val(employeeInfo.autoId)
            }
        });
    }else{
        alert("Please Enter Employee ID");
    }
}


function toggleExtraDiv(){

    $("#extraDiv").fadeToggle("slow");
}