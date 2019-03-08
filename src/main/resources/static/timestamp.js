$(function () {
    function setTime(){
        $("#timestamp").val(new Date().getTime())
    }
    setInterval(setTime,1000);
});