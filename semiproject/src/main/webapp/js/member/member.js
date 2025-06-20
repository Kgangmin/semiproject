

function goEmailChange(ctx_Path){
	
	// 나의정보 수정하기 팝업창 띄우기
	   const url = `${ctx_Path}/changeEmail.hb`;
	//  또는
	//  const url = ctx_Path+"/member/memberEdit.up?userid="+userid;   
	   
	   // 너비 800, 높이 680 인 팝업창을 화면 가운데 위치시키기
	   const width = 600;
	   const height = 500;
	/*   
	   console.log("모니터의 넓이 : ",window.screen.width);
	   // 모니터의 넓이 :  1440
	   
	   console.log("모니터의 높이 : ",window.screen.height);
	   // 모니터의 높이 :  900
	   */   
       const left = Math.ceil((window.screen.width - width)/2);  // 정수로 만듬 
       const top = Math.ceil((window.screen.height - height)/2); // 정수로 만듬
      window.open(url, "goEmailChange", 
                  `left=${left}, top=${top}, width=${width}, height=${height}`);
	
}//function goEditMyInfo(userid, ctx_Path)