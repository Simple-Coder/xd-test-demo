/**
 * kindeditor 扩展
 */
var Kind = {
	// 默认kind显示按钮
	kindeditorOptions : {
		allowImageUpload : false,
		allowFlashUpload : false,
		allowFileUpload : false,
		allowMediaUpload : false,
		items : [ 'source', 'fullscreen', '|', 'undo', 'redo', '|', 'cut',
				'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft',
				'justifycenter', 'justifyright', 'justifyfull',
				'insertorderedlist', 'insertunorderedlist', 'indent',
				'outdent', 'subscript', 'superscript', 'clearhtml',
				'quickformat', 'selectall', '|', '/', 'formatblock',
				'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
				'bold', 'italic', 'underline', 'strikethrough', 'lineheight',
				'removeformat', '|', 'table', 'hr', 'anchor' ]
	},
	// 只读kind显示按钮
	kindeditorOptionsReadOnly : {
		allowImageUpload : false,
		allowFlashUpload : false,
		allowFileUpload : false,
		allowMediaUpload : false,
		useContextmenu : false,
		items : [ 'fullscreen' ],
	},
	/**
	 * 初始化kindeditor textAreaId textarea框ID readonlyMode 是否只读显示 options 自定义按钮项
	 * 
	 * @return kindeditor 对象
	 */
	init : function(textAreaId, readonlyMode, options) {
		var config = null;
		if (options) {
			config=options;
			if (readonlyMode != null && readonlyMode) {
				config.readonlyMode = readonlyMode;
			}
		}else{
			config = Kind.kindeditorOptions;
			if (readonlyMode != null && readonlyMode) {
				config = Kind.kindeditorOptionsReadOnly;
				config.readonlyMode = readonlyMode;
			}
		}
		var editor = KindEditor.create('#' + textAreaId, config);
		return editor;
	},
	/**
	 * 设置kindeditor值 editor kindeditor 对象 htmlContent 显示内容 isEnc
	 * 是否需要进行解码（base64)，默认进行编码
	 */
	setValue : function(editor, htmlContent, isEnc) {
		if (isEnc == null)
			isEnc = true;
		if (htmlContent) {
			if (isEnc) {
				var html = utf8to16(decode64(htmlContent));
				editor.html(html);
			} else {
				editor.html(htmlContent);
			}
		}
	},
	/**
	 * 获取kindeditor值 editor kindeditor 对象 isEnc 是否需要进行解码（base64)，默认进行编码
	 */
	getValue : function(editor, isEnc) {
		if (isEnc == null)
			isEnc = true;
		editor.sync();
		var content = editor.html();
		if (content.length > 0 && isEnc) {
			return encode64(utf16to8(content));
		}
		return content;
	},
	/**
	 * 判断是否为空
	 * editor kindeditor 对象
	 */
	isEmpty:function(editor){
		return editor.isEmpty();
	}
	
}
