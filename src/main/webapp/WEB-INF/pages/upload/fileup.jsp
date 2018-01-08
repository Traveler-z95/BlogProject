<%--
  Created by IntelliJ IDEA.
  User: Da
  Date: 2017/8/23
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>上传文件</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script>
        var totalFileLength,totalUploaded, fileCount, filesUploaded;
        function debug(s) {
            var debug = document.getElementById("debug");
            if (debug) {
                debug.innerHTML = debug.innerHTML + '<br/>' + s;
            }
        }
        function onUploadComplete(e) {
            totalUploaded += document.getElementById('files').files[filesUploaded].size;
            filesUploaded++;
            debug('complete ' + filesUploaded + " of " +fileCount);
            debug('totalUploaded: ' +totalUploaded);
            if (filesUploaded < fileCount){
                uploadNext();
            }else {
                var bar = document.getElementById('bar');
                bar.style.width = '100%';
                bar.innerHTML = '100% complete';
                alert('Finished uploading file(s)');
            }
        }
        function onFileSelect(e) {
            var files = e.target.files; //  FileList object
            var output = [];
            fileCount = files.length;
            totalFileLength = 0;
            for (var i=0; i<fileCount; i++){
                var file = files[i];
                output.push(file.name,'(',
                file.size, ' bytes, ',
                file.lastModifiedDate.toLocaleDateString(), ')'
                );
                output.push('<br/>');
                debug('add ' + file.size);
                totalFileLength +=file.size;
            }
            document.getElementById('selectedFiles').innerHTML =output.join('');
            debug('totalFileLength: ' + totalFileLength);
        }
        function onUploadProgress(e) {
            if (e.lengthComputable) {
                var percentComputable = parseInt(
                    (e.loaded + totalUploaded) * 100
                    / totalFileLength);
                var bar = document.getElementById('bar');
                bar.style.width = percentComputable + '%';
                bar.innerHTML = percentComputable + ' % complete';
            }else {
                debug('unable to compute');
            }
        }
        function onUploadFailed(e) {
            alert("Error uploading file");
        }
        function uploadNext() {
            var xhr = new XMLHttpRequest();
            var fd = new FormData();
            var file = document.getElementById('files').files[filesUploaded];
            fd.append("multipartFile", file);
            xhr.upload.addEventListener(
                "progress", onUploadProgress, false);
            xhr.addEventListener("load",onUploadComplete, false);
            xhr.addEventListener("error", onUploadFailed, false);
            xhr.open("POST", "file_upload");
            debug('uploading ' + file.name);
            xhr.send(fd);
        }
        function startUpload() {
            totalUploaded = filesUploaded = 0;
            uploadNext();
        }
        window.onload = function () {
            document.getElementById('files').addEventListener('change', onFileSelect, false);
            document.getElementById('uploadButton').addEventListener('click', startUpload, false);
        }
    </script>
</head>
<body>
<div id='progressBar' style='height: 20px;border: 2px solid green'>
    <div id='bar' style='height: 100%;background: #33dd33;width: 0%'>
    </div>
</div>
<form>
    <input type="file" id="files" multiple/>
    <br/>
    <output id="selectedFiles"></output>
    <input id="uploadButton" type="button" value="Upload"/>
</form>
<div id="debug" style="height:100px;border:2px solid green;overflow: auto">
</div>
</body>
</html>
