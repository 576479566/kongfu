$(function(){
    //id必须和页面上定义的ms-controller名字相同，否则无法控制页面
    $id: "viewmodel",
    datalist: {},
    text: "请求数据",

    request: function () {
        $.ajax({
            type: "post",
            url: "./test/data",    //向springboot请求数据的url
            data: {},
            success: function (data) {
                $('button').removeClass("btn-primary").addClass("btn-success").attr('disabled', true);

               datalist = data;

                text = "数据请求成功，已渲染";
            }
        });
    }
});