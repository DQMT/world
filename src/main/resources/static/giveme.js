// Ajax 文件下载
//当不用传参时，可以将data去掉
jQuery.download = function(url, data, method){ // 获得url和data
    if( url && data ){
        // data 是 string 或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data); // 把参数组装成 form的 input
        var inputs = '';
        jQuery.each(data.split('&'), function(){
            var pair = this.split('=');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />';
        }); // request发送请求
        jQuery('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>').appendTo('body').submit().remove();
    };
};

function addLink(file) {
    const p = $("#tar").html();
    console.log('p=' + p);
    console.log('file=' + file);
    var np = p+'</br>'+'<a class="link" onclick="rec('+"'"+file+"'"+');" href="javascript:void(0);">'+file+'</a>';
    console.log('np=' + np);
    $("#tar").html(np);
}

function getProcess(reqUrl) {
    $.ajax({
        url: "/giveme/process",
        method:'GET',
        async: true,
        data: {"path": reqUrl},
        success: function (data) {
            console.log('res process = ' + data);
            $("#proc").val(data);
        },
        error: function (e) {
            alert(e);
        }
    })
}
function req() {
    const url = $("#fp").val();
    console.log(url);
    var t1 = window.setInterval(getProcess(url),500);
    $.ajax({
        url: "/giveme/req",
        method:'GET',
        async: true,
        data: {"path": url},
        success: function (data) {
            console.log('res data = ' + data);
            addLink(data);
        },
        error: function (e) {
            alert(e);
        }
    })
}

function rec(file) {
    console.log('file=' + file);
    $.download('/giveme/rec','file='+file,'post' );
    /*$.ajax({
        url: "/world/giveme/rec",
        method:'GET',
        async: true,
        data: {"file": file},
        success: function (data, status, xhr) {
            console.log("Download file DONE!");
            console.log(data);
            console.log(status);
            console.log(xhr);
            console.log("=====================");
        },
        error: function (e) {
            alert(e);
        }
    })*/
}