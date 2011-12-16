$( document ).ready( function(){
	$( "#insert" ).click( function(){
		$( "#content1" ).css( "display", "none" );
		$( "#content2" ).css( "display", "block" );
		
		$( "#button_insert").click( function(){
			Android.insert( 
					$( "#insert_application" ).val(),
					$( "#insert_user" ).val(),
					$( "#insert_password" ).val() );
			
			$( "#content2" ).css( "display", "none" );
			$( "#content1" ).css( "display", "block" );			
		});
		$( "#button_back").click( function(){
			$( "#content2" ).css( "display", "none" );
			$( "#content1" ).css( "display", "block" );			
		});
	});
	
	$( "#read" ).click( function(){
		$( "#content1" ).css( "display", "none" );
		$( "#content3" ).css( "display", "block" );
		
		$( "#content3" ).html( "" );
		JSON.parse( String(Android.read()), function( key, value ){
			if ( typeof value != 'object' ){
				if ( key == 'application' )
					$( "#content3" ).append( "<div class='password_application'>"+value );
				if ( key == 'user' )
					$( "#content3" ).append( "<div class='password_content'>user: "+value+"</div>" );
				if ( key == 'password' )
					$( "#content3" ).append( "<div class='password_content'>password: "+value+"</div></div>" );
			}
		});
		$( "#content3" ).append( "<div id='read_back' class='button'>Voltar</div>" );
		
		$( "#read_back" ).click( function(){
			$( "#content3" ).css( "display", "none" );
			$( "#content1" ).css( "display", "block" );
		});
	});
});