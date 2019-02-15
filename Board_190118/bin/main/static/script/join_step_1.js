$(document).ready(function(){
    //최상단 체크박스 클릭
    $("#all_check").click(function(){
        //클릭되었으면
        if($("#all_check").prop("checked")){
            //input태그의 name이 chk인 태그들을 찾아서 checked옵션을 true로 정의
            $("input[name=use_rule_check]").prop("checked",true);
            $("input[name=private_info_check]").prop("checked",true);
            $("input[name=advertise_check]").prop("checked",true);
            //클릭이 안되있으면
        }else{
            //input태그의 name이 chk인 태그들을 찾아서 checked옵션을 false로 정의
            $("input[name=use_rule_check]").prop("checked",false);
            $("input[name=private_info_check]").prop("checked",false);
            $("input[name=advertise_check]").prop("checked",false);
        }
    })
})