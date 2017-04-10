(function () {
  $.MsgBox = {
    Alert: function (title, msg) {
      GenerateHtml("alert", title, msg);
      btnOk(); //alert只是弹出消息，因此没必要用到回调函数callback
      btnNo();
    },
    Confirm: function (title, msg, callback) {
      GenerateHtml("confirm", title, msg);
      btnOk(callback);
      btnNo();
    }
  }
 
  //生成Html
  var GenerateHtml = function (type, title, msg) {
 
    var _html = "";
    _html += '<div id="mb_box"></div><div id="mb_con"><span id="mb_tit">' + title + '</span>';

    if (type == "alert") {
      _html += '<a id="mb_ico">x</a><div id="mb_msg" style="text-align:left;">' + msg + '</div><div id="mb_btnbox">';
      _html += '<input id="mb_btn_ok" type="button" value="确定" />';
    }
    if (type == "confirm") {
      _html += '<a id="mb_ico">x</a><div id="mb_msg" style="text-align:center;">' + msg + '</div><div id="mb_btnbox">';
      _html += '<input id="mb_btn_ok" type="button" value="确定" />';
      _html += '<input id="mb_btn_no" type="button" value="取消" />';
    }
    _html += '</div></div>';
 
    //必须先将_html添加到body，再设置Css样式
    $("body").append(_html); GenerateCss();
  }
 
  //生成Css
  var GenerateCss = function () {
 
    $("#mb_box").css({ width: '100%', height: '100%', zIndex: '99999', position: 'fixed',
      filter: 'Alpha(opacity=60)', backgroundColor: 'black', top: '0', left: '0', opacity: '0.6'
    });
 
    $("#mb_con").css({ zIndex: '999999', width: '400px', position: 'fixed',
      backgroundColor: 'White', borderRadius: '15px'
    });
 
    $("#mb_tit").css({ display: 'block', fontSize: '14px', color: '#444', padding: '10px 15px',
      backgroundColor: '#DDD', borderRadius: '15px 15px 0 0',
      borderBottom: '3px solid #009BFE', fontWeight: 'bold'
    });
 
    $("#mb_msg").css({ padding: '20px', lineHeight: '20px',
      borderBottom: '1px dashed #DDD', fontSize: '13px'
    });
 
    $("#mb_ico").css({ display: 'block', position: 'absolute', right: '10px', top: '9px',
      border: '1px solid Gray', width: '18px', height: '18px', textAlign: 'center',
      lineHeight: '16px', cursor: 'pointer', borderRadius: '12px', fontFamily: '微软雅黑'
    });
 
    $("#mb_btnbox").css({ margin: '15px 0 10px 0', textAlign: 'center' });
    $("#mb_btn_ok,#mb_btn_no").css({ width: '85px', height: '30px', color: 'white', border: 'none' });
    $("#mb_btn_ok").css({ backgroundColor: '#168bbb' });
    $("#mb_btn_no").css({ backgroundColor: 'gray', marginLeft: '20px' });
 
 
    //右上角关闭按钮hover样式
    $("#mb_ico").hover(function () {
      $(this).css({ backgroundColor: 'Red', color: 'White' });
    }, function () {
      $(this).css({ backgroundColor: '#DDD', color: 'black' });
    });
 
    var _widht = document.documentElement.clientWidth; //屏幕宽
    var _height = document.documentElement.clientHeight; //屏幕高
 
    var boxWidth = $("#mb_con").width();
    var boxHeight = $("#mb_con").height();
 
    //让提示框居中
    $("#mb_con").css({ top: (_height - boxHeight) / 2 + "px", left: (_widht - boxWidth) / 2 + "px" });
  }
 
 
  //确定按钮事件
  var btnOk = function (callback) {
    $("#mb_btn_ok").click(function () {
      $("#mb_box,#mb_con").remove();
      if (typeof (callback) == 'function') {
        callback();
      }
    });
  }
 
  //取消按钮事件
  var btnNo = function () {
    $("#mb_btn_no,#mb_ico").click(function () {
      $("#mb_box,#mb_con").remove();
    });
  }
})();


// 设置为主页 
function SetHome(obj,vrl){ 
	try{ 
	obj.style.behavior='url(#default#homepage)';obj.setHomePage(vrl); 
	} 
	catch(e){ 
	if(window.netscape) { 
	try { 
	netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect"); 
	} 
	catch (e) { 
		$.MsgBox.Alert("<t style='color:red'>X&nbsp;</t>设为首页失败","此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。"); 
	} 
	var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch); 
	prefs.setCharPref('browser.startup.homepage',vrl); 
	}else{ 
		$.MsgBox.Alert("<t style='color:red'>X&nbsp;</t>设为首页失败","您的浏览器不支持，请按照下面步骤操作：1.打开浏览器设置。2.点击设置网页。3.输入："+vrl+"点击确定。"); 
	} 
	} 
} 

// 加入收藏 兼容360和IE6 
function shoucang(sTitle, sURL) {
	try {
		window.external.addFavorite(sURL, sTitle);
	} catch (e) {
		try {
			window.sidebar.addPanel(sTitle, sURL, "");
		} catch (e) {
			$.MsgBox.Alert("<t style='color:red'>X&nbsp;</t>加入收藏失败","请使用Ctrl+D进行添加。");
		}
	}
}
