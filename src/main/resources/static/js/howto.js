/**
 * 
 */

$(document).ready(function(){
	
	if(localStorage.getItem('showGuide')==0){
		$('#background').addClass('hide');
	}
	
	$('#submit-off-guide').click(function(){
		$('#background').addClass('hide');
		if($('#check-off').prop('checked')){
			localStorage.setItem('showGuide',0);
		}
	})
	
	$('#showGuide').click(function(){
		
		$('#background').removeClass('hide');
	})
	
})