
$(document).ready(function(){
		$("#logoutLink").on("click",function(e){
			e.preventDefault();
			document.logoutForm.submit();
		})
		customizeDropDownMenu()
	})

	function customizeDropDownMenu(){
		
		$(".navbar .dropdown").hover(
			function(){
				$(this).find('.dropdown-menu').first().stop(true,true).delay(500).slideDown(500);
			},
			function(){
				$(this).find('.dropdown-menu').first().stop(true,true).delay(500).slideUp(200);
			}
		)
		
		$(".dropdown  > a").click(function(){
			location.href=this.href
		})
	}