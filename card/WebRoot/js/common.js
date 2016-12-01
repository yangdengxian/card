$(function(){

	$("#imageBtn").click(function(){
		createCode();	
	});

	function createCode(){
		var name=comp=dept=title=addr=mob=tel=fax=email=rem=str="";
		if($("#name").val().length>0){
			name="FN:"+$("#name").val()+"\n";
		}
		if($("#company").val().length>0){
			if($("#dept").val().length>0){
				dept="ORG:"+$("#company").val()+"（"+$("#dept").val()+"）\n";
			}else{
				dept="ORG:"+$("#company").val()+"（"+$("#dept").val()+"）\n";
			}
		}
		if($("#title").val().length>0){
			title="TITLE:"+$("#title").val()+"\n";
		}
		if($("#address").val().length>0){
			addr="ADR;WORK:"+$("#address").val()+"\n";
		}
		if($("#mobile").val().length>0){
			mob="TEL;CALL:"+$("#mobile").val()+"\n";
		}
		if($("#telphone").val().length>0){
			tel="TEL;CALL:"+$("#telphone").val()+"\n";
		}
		if($("#fax").val().length>0){
			fax="TELL;WORK;FAX:"+$("#fax").val()+"\n";
		}
		if($("#email").val().length>0){
			email="EMAIL;WORK:"+$("#email").val()+"\n";
		}
		if($("#remark").val().length>0){
			rem="NOTE:"+$("#remark").val()+"\n";
		}
		str = "BEGIN:VCARD\n"+name+dept+title+addr+mob+tel+fax+email+rem+"END:VCARD";
		

		if ($("#picSelectVal").val() == "svg") {
			var qrcode = {};
			$("#svgDiv").show();
			$(".img_item").hide();
			qrcode = new QRCode(document.getElementById("qrcode"), {
			    width : 255,
			    height : 255,
			    correctLevel: 1,
			    useSVG: true
			});
			qrcode.makeCode(str);
		} else {
			$("#svgDiv").hide();
			$(".img_item").show();
			qrcodeServlet(str,$("#picSelectVal").val());
		}
	}

	function qrcodeServlet(str,picType){
		$.ajax({
			type:"post",
			url:"ECardServlet",
			data:{content:str,picType:picType},
			success:function(data){
				$("#qrcodeImage").attr("src",data);
			}
		});
	}
});