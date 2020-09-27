var totalFileLength, totalUploaded, fileCount, filesUploaded, percentage=0;

var purpose="";

 
    //To log everything on console
    function debug(s) {
        var debug = document.getElementById('debug');
        if (debug) {
            debug.innerHTML = debug.innerHTML + '<br/>' + s;
        }
    }
 
    //Will be called when upload is completed
    function onUploadComplete(e) {
        totalUploaded += document.getElementById('files').files[filesUploaded].size;
        filesUploaded++;
       // debug('complete ' + filesUploaded + " of " + fileCount);
      //  debug('totalUploaded: ' + totalUploaded);
        if (filesUploaded < fileCount) {
            uploadNext();
        } else {
            var bar = document.getElementById('bar');
            bar.style.width = '100%';
            bar.innerHTML = '100% complete';
            //notification();
        }
        
       
        
        
    }
    
    function notification(){
    	if (percentage==100) {
    		alert("Files Uploaded")
    	}
    }
 
    
    
    //Will be called when user select the files in file control
    function onFileSelect(e) {
    	 var bar = document.getElementById('bar');
         bar.style.width = 0 + '%';
         bar.innerHTML = 0 + ' % complete';
    	
        var files = e.target.files; // FileList object
        var output = [];
        fileCount = files.length;
        totalFileLength = 0;
        for (var i = 0; i < fileCount; i++) {
            var file = files[i];
            output.push(file.name, ' (', file.size, ' bytes, ', file.lastModifiedDate.toLocaleDateString(), ')');
            output.push('<br/>');
            debug('add ' + file.size);
            totalFileLength += file.size;
        }
       // document.getElementById('selectedFiles').innerHTML = output.join('');
        debug('totalFileLength:' + totalFileLength);
    }
 
    //This will continueously update the progress bar
    function onUploadProgress(e) {
        if (e.lengthComputable) {
            var percentComplete = parseInt((e.loaded + totalUploaded) * 100 / totalFileLength);
            var bar = document.getElementById('bar');
            bar.style.width = percentComplete + '%';
            bar.innerHTML = percentComplete + ' % complete';
            console.log(" bar prog "+percentComplete)
            
           percentage=percentComplete;
            
            console.log(" percentage prog "+percentage)
        } else {
            debug('unable to compute');
        }
    }
 
    //the Ouchhh !! moments will be captured here
    function onUploadFailed(e) {
        alert("Error uploading file");
    }
 
    //Pick the next file in queue and upload it to remote server
    function uploadNext() {
    	if ($("#purpose").val()=="") {
    	  	alert("Insert Purpose");
      	}else{
      	  purpose=$("#purpose").val();
      	
        var xhr = new XMLHttpRequest();
        var fd = new FormData();
        var file = document.getElementById('files').files[filesUploaded];
        fd.append("multipartFile", file);
        xhr.upload.addEventListener("progress", onUploadProgress, false);
        xhr.addEventListener("load", onUploadComplete, false);
        xhr.addEventListener("error", onUploadFailed, false);
        
        var user=$("#userId").val();
        
        xhr.open("POST", "save-product/"+purpose+"/"+user);
        debug('uploading ' + file.name);
        xhr.send(fd);
        
      	}
        
    }
 
    //Let's begin the upload process
    function startUpload() {
    	
        totalUploaded = filesUploaded = 0;
        uploadNext();
        
        
    }
    
    
    function find1(){
    	var start=$("#formDate").val();
    	start=new Date(start).toLocaleDateString('fr-CA') 
    	
    	var end=$("#endDate").val();
    	end=new Date(end).toLocaleDateString('fr-CA') 
    	
    	find(start, end)
    }
    
    function find(start, end){
    	
    	
    	
    	
    	var user=$("#userId").val();
    	$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './findfile/'+start+"/"+end+"/"+user,
			data: {
				
					

			},
			success: function (data) {
				$("#filetable").empty();
				patchData(data.result);

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
    
    
    function patchData(data){
    	var rows = [];


    	for (var i = 0; i < data.length; i++) {
    		//ert("ad "+data[i].aquisitionValue);
    		rows.push(drawRow(data[i],i+1));

    	}

    	$("#filetable").append(rows);
    	
    }
    
    function drawRow(rowData,c) {

    	//alert(rowData.aquisitionValue);

    	var row = $("<tr />")
    	row.append($("<td>" + rowData.id+ "</td>"));
    	row.append($("<td>" + rowData.filename+ "</td>"));
    	row.append($("<td>" + rowData.upby+ "</td>"));
    	row.append($("<td>" + rowData.upIp+ "</td>"));
    	row.append($("<td>" + rowData.upDateTime+ "</td>"));
    	row.append($("<td>" + rowData.purpose+ "</td>"));
    	row.append($("<td>" + rowData.DownBy+ "</td>"));
    	row.append($("<td>" + rowData.DownMachine+ "</td>"));
    	row.append($("<td>" + rowData.DownDatetime+ "</td>"));
    	row.append($("<td ><i class='fa fa-download' onclick=download('" + encodeURIComponent(rowData.filename) + "')  class=\"btn btn-primary\"> </i></td>"));
    	row.append($("<td ><i class='fa fa-trash' onclick=del('" + encodeURIComponent(rowData.filename) + "')  class=\"btn btn-primary\"> </i></td>"));
    	

    	return row;
    }
    
    
    function del(filename) {
    	
        var file=decodeURIComponent(filename);	
        var user=$("#userId").val();

    	$.ajax({
			type: 'POST',
			dataType: 'json',
			url: './delete/'+file,
			data: {
				
					

			},
			success: function (data) {
				if (data==true) {
					find1();
					alert("Successfully Deleted")
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
   
    
    
    function download(filename) {
    	
     var file=decodeURIComponent(filename);	
     var user=$("#userId").val();
     var xhr = new XMLHttpRequest();
     xhr.open("POST", "download/"+file+'/'+user);
     xhr.responseType = 'arraybuffer';
     xhr.onload = function () {
      if (this.status === 200) {
       var filename = "";
       var disposition = xhr.getResponseHeader('Content-Disposition');
       if (disposition && disposition.indexOf('attachment') !== -1) {
        var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
        var matches = filenameRegex.exec(disposition);
        if (matches != null && matches[1]) {
         filename = matches[1].replace(/['"]/g, '');
        }
       }
       var type = xhr.getResponseHeader('Content-Type');var blob = typeof File === 'function'? new File([this.response], filename, { type: type }): new Blob([this.response], { type: type });
       if (typeof window.navigator.msSaveBlob !== 'undefined') {
        // IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. 
        // These URLs will no longer resolve as the data backing the URL has been freed."
        window.navigator.msSaveBlob(blob, filename);
       } else {
        var URL = window.URL || window.webkitURL;
        var downloadUrl = URL.createObjectURL(blob);if (filename) {
         // use HTML5 a[download] attribute to specify filename
         var a = document.createElement("a");
         // safari doesn't support this yet
         if (typeof a.download === 'undefined') {
          window.location = downloadUrl;
         } else {
          a.href = downloadUrl;
          a.download = filename;
          document.body.appendChild(a);
          a.click();
         }
        } else {
         window.location = downloadUrl;
        }
        setTimeout(function () { URL.revokeObjectURL(downloadUrl); }, 100); // cleanup
       }
      }
     };
     xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
     
    xhr.send($.param({
      
     }));
    }
 
    
   
    

  
  
    //Event listeners for button clicks
    window.onload = function() {
        document.getElementById('files').addEventListener('change', onFileSelect, false);
        document.getElementById('uploadButton').addEventListener('click', startUpload, false);
        document.getElementById('find').addEventListener('click', find1, false);
    }