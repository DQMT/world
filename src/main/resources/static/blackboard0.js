function t(a, o) {
    return a + parseInt(o / 95) > 126 ? a + parseInt(o / 95) - 95 : a + parseInt(o / 95);
}

function r(a, o) {
    return a - parseInt(o / 95) < 32 ? a - parseInt(o / 95) + 95 : a - parseInt(o / 95);
}

function e(s, k) {
    let o = encodeURIComponent(s);
    console.log("after encodeURIComponent");
    console.log(o);
    let o2 = "";
    for (let i = 0; i < o.length; i++) {
        o2 += String.fromCharCode(t(o.charCodeAt(i), (k % 16) * (i % 3 + 2) + 1));
    }
    return encodeURIComponent(o2);
}

function d(s, k) {
    s = decodeURIComponent(s);
    let o = "";
    for (let i = 0; i < s.length; i++) {
        o += String.fromCharCode(r(s[i].charCodeAt(0), (k % 16) * (i % 3 + 2) + 1));
    }
    return decodeURIComponent(o);
}

$(function () {
    let k = document.getElementById('k').value.trim();
    let s = document.getElementById('s').value.trim();
    if (k !== "") {
        $("#t1").val(d(s, k));
    }
});

function gooo() {
    let s = document.getElementById('t1').value.trim();
    let t = parseInt(new Date().getTime() / (1000 * 60 * 11));
    s = e(s, t);
    let L = 5000;
    if (s.length > L) {
        s = "^" + s + "$";
        let sn = [];
        while (s.length > L) {
            sn.push(s.slice(0, L));
            s = s.substring(L);
        }
        sn.push(s);
        for (let i = 0; i < sn.length; i++) {
            $.ajax({
                url: "/blackboard/snk",
                async: false,
                data: {"s": sn[i]},
                success: function (e) {
                },
                error: function (e) {
                    alert(e);
                }
            })
        }
    }else{
        $.ajax({
            url: "/blackboard/go",
            async: true,
            data: {"s": s},
            success: function (e) {
            },
            error: function (e) {
                alert(e);
            }
        })
    }
}

function Trim(str, is_global) {
    let result;
    result = str.replace(/(^\s+)|(\s+$)/g, "");
    if (is_global.toLowerCase() === "g") {
        result = result.replace(/\s/g, "").replace(/[\r\n]/g, "");
    }
    return result;
}

function cooo() {
    let s = document.getElementById('t1').value.trim();
    $("#t1").val(Trim(s, "g"));
}

