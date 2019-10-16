/**
 * 首页菜单
 */
var menuLiIdx = 0;
var tmpTm = null;
function showMenu(){
	var obj = $('#search-menu-input');
	var sm = $('#search-menu');
	var mi = obj.offset();
	var lf = mi.left;
	var tp = mi.tp;
	sm.css("left",lf);
	sm.css("top",tp+20);
	sm.width(obj.width()+7);
	$(".search-menu-li",sm).hover(
         function(){$(this).addClass("hov");},
         function(){$(this).removeClass("hov");}
     );
   	sm.show();
}

function search(icode){
	if(tmpTm){
		window.clearTimeout(tmpTm);
	}
	var evt = event || window.event;
	tmpTm = window.setTimeout(function(){
		var tp = icode.toLowerCase();
		var tmpArray = new Array();
		var isMore = false;
		for(var i=0;i<menuArray.length;i++){
			var func = menuArray[i];
			if(func.name.indexOf(icode)>=0){
				tmpArray.push(func);
			}else if(func.pinyin.indexOf(tp)>=0){
				tmpArray.push(func);
			}else if(func.allPinYin.indexOf(tp)>=0){
				tmpArray.push(func);
			}else if(func.id==tp){
				tmpArray.push(func);
			}
			if(tmpArray.length>9){
				isMore = true;
				break;
			}
		}
		var flen = tmpArray.length;
		if(flen>0){
			var smobj = $('#search-menu');
			smobj.empty();
			repMenuShowNm(tmpArray);
			for(var i=0;i<flen;i++){
				var func = tmpArray[i];
				var showNm = func.name;
				if(func.sign){
					showNm +=" ["+func.topName+"]";
				}
				smobj.append("<li id=\"search_menu_" + func.id + "\" func=\""+func.name+"\" class=\"search-menu-li\"  onclick=\"doWork('" + func.id + "','" + func.name + "','" + func.path+"');\">"+showNm+"</li>");
			}
			if(isMore){
				smobj.append("<li class=\"search-menu-more\" title=\"请输入更详细搜索条件\">更多...</li>");
			}
			showMenu();
			if(evt && evt.keyCode){
				var kc = evt.keyCode;
				if (kc == 38) { /*向上按钮*/  
	                if (menuLiIdx <= 0) {
	                	menuLiIdx = flen-1; //到顶了
	                }else{
	                	menuLiIdx--;
	                }
	            } else if (kc == 40) {/*向下按钮*/  
	                if (menuLiIdx >= (flen-1)){
	                	menuLiIdx = 0; //到底了   
	                }else{
	                	menuLiIdx++;
	                }
	            }
				if (kc == 13 || kc==9){
					var li = smobj.find("li:eq(" + menuLiIdx + ")"); 
					$('#search-menu-input').val(li.attr("func"));
					li.click();
					hiddenMenu(0);
				}  
			}
			if (menuLiIdx>(flen-1)) {
				menuLiIdx=0;
			}
            var li = smobj.find("li:eq(" + menuLiIdx + ")");  
            li.css("background", "#438eb9").siblings().css("background", "");
            li.css("color", "#ffffff").siblings().css("color", "");
		}else{
			hiddenMenu(0);
		}
	}, 100); 
}


function repMenuShowNm(tmpArray){
	var len = tmpArray.length;
	for(var i=0;i<len;i++){
		var oneNm = tmpArray[i].name;
		for(var j=0;j<len;j++){
			if(i!=j){
				var twNm = tmpArray[j].name;
				if(oneNm==twNm){
					tmpArray[i].sign = true;
					break;
				}
			}
		}
	}
}

function searchMenu(){
	var obj = $('#search-menu-input');
	var icode = obj.val();
	if(icode.length>1){
		search(icode);
	}else{
		hiddenMenu(0);
	}
}


function hiddenMenu(delay){
	if(tmpTm){
		window.clearTimeout(tmpTm);
	}
	//$('#search-menu').hide();
	window.setTimeout(function(){
		  $('#search-menu').hide();
	}, delay);
}