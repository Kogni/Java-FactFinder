package Control;

import java.net.URL;

import Objects.Object_Link;

public class Brain_Links_old {

	Controller Class_Controller;
	Object_Link LinkArray[];
	Object_Link SortedLinkArray[];
	int URLS = 0;
	
	public Brain_Links_old( Controller Class_Controller ) {
		
		this.Class_Controller = Class_Controller;
		LinkArray = new Object_Link[9999];
		SortedLinkArray = new Object_Link[9999]; 
	}
	
	public void InsertLink( String Adresse, int relationValue ) {
		if ( Adresse.length() == 0 ) {
			return;
		}
		//System.out.println( "InsertLink "+Adresse );
		/*
		if ( FindLink( Adresse ) == true ) {
			return;
		}*/
		//System.out.println( "--"+Adresse+" "+ FindLink( Adresse ) );
		//SortURLsAlpha();
		
		for ( int X = 0; X < LinkArray.length ; X++ ) {
			if ( LinkArray[X] == null ) {
				try {
					LinkArray[X] = new Object_Link( new URL( Adresse ), relationValue );
					if ( relationValue == 0 ) {
						LinkArray[X].SetSearched(true);
					}
					URLS ++;
					//System.out.println( "InsertLink Lagret url:"+Adresse+" #"+URLS+" value "+relationValue+" " +LinkArray[X].GetSearched() );
					if ( relationValue > this.Class_Controller.InterestBorder ) {
						System.out.println( System.currentTimeMillis()+" InsertLink Lagret url: "+Adresse+" #"+URLS+" value "+relationValue+" " +LinkArray[X].GetSearched() );
						//Class_Controller.SaveDatabase( LinkArray );
						//Class_Controller.SearchURL( LinkArray[X] );
					} else if ( URLS > 1 ) {
						//OrderLinkGathering();
					}
					if ( URLS > 1 ) {
						OrderLinkGathering();
					}
					
					return;
					//OrderLinkGathering();
				} catch ( Exception T ) {
					System.err.println("Brain_Links Kunne ikke lagre url");
					System.err.println( "InsertLink "+Adresse );
					System.err.println ( "Throwable message: " + T.getMessage ( ) );
					System.err.println ( "Throwable cause: " + T.getCause ( ) );
					System.err.println ( "Throwable class: " + T.getClass ( ) );
					if ( T.getStackTrace ( ) != null ){
						System.err.println ( "Exception origin: ");
						System.err.println ( "Class: " + T.getStackTrace ( )[0].getClassName ( ) );
						System.err.println ( "Method: " + T.getStackTrace ( )[0].getMethodName ( ) );
						System.err.println ( "Line: " + T.getStackTrace ( )[0].getLineNumber ( ) );
					}
					if ( T.getStackTrace ( ) != null ){
						System.err.println ( "Exception origin: ");
						System.err.println ( "Class: " + T.getStackTrace ( )[1].getClassName ( ) );
						System.err.println ( "Method: " + T.getStackTrace ( )[1].getMethodName ( ) );
						System.err.println ( "Line: " + T.getStackTrace ( )[1].getLineNumber ( ) );
					}
					/*for ( int y = 1 ; y < T.getStackTrace().length ; y++ ) {
						System.err.println (" ");
						System.err.println ( "Origin stack "+y+": ");
						System.err.println ( "Class: " + T.getStackTrace ( )[y].getClassName ( ) );
						System.err.println ( "Method: " + T.getStackTrace ( )[y].getMethodName ( ) );
						System.err.println ( "Line: " + T.getStackTrace ( )[y].getLineNumber ( ) );
					}*/
				}
				//PrintSortedURLr();
			} else {
				//System.out.println( "InsertLink "+X+" "+LinkArray[X].GetURL()+" "+LinkArray[X].relationValue+ " "+LinkArray[X].GetSearched() );
				if ( LinkArray[X].GetURL().toString().equals( Adresse ) ) {
					//System.out.println( "InsertLink fant link fra før");
					return;
				} else if ( LinkArray[X].GetURL().toString().compareToIgnoreCase( Adresse ) == 0 ) {
					//System.out.println( "InsertLink fant link fra før");
					return;
				}
			}
		}
		
	}
	
	private boolean FindLink( String Adresse ) {
		for ( int X = 0; X < LinkArray.length ; X++ ) {
			if ( LinkArray[X] != null ) {
				System.out.println( X+" "+LinkArray[X].GetURL().toString() + " vs "+Adresse+" "+LinkArray[X].GetURL().toString().equals( Adresse )+" "+ LinkArray[X].GetURL().toString().compareToIgnoreCase( Adresse ) );
				if ( LinkArray[X].GetURL().toString().equals( Adresse ) ) {
					return true;
				}
				if ( LinkArray[X].GetURL().toString().compareToIgnoreCase( Adresse ) == 0 ) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void SortURLs_Relation() {
		for ( int X = 0; X < LinkArray.length ; X++ ) {
			if ( LinkArray[X] != null ) {
				//System.out.println( LinkArray[X].GetURL()+" "+LinkArray[X].relationValue+ " "+LinkArray[X].GetSearched() );
				for ( int Y = X; Y < LinkArray.length ; Y++ ) {
					if ( LinkArray[Y] != null ) {
						
						if ( LinkArray[X].GetURL().equals( LinkArray[Y].GetURL() ) == false ) {
							if ( LinkArray[X].relationValue < LinkArray[Y].relationValue ) {
								//System.out.println( "*"+LinkArray[X].relationValue+" "+LinkArray[X].GetURL()+" btytter plass med "+LinkArray[Y].relationValue+" "+LinkArray[Y].GetURL() );
								Object_Link A = LinkArray[X];
								Object_Link B = LinkArray[Y];
								LinkArray[X] = B;
								LinkArray[Y] = A;
							}
						}
						
					}
				}
				//System.out.println( X+" "+LinkArray[X].GetURL()+" "+LinkArray[X].relationValue+ " "+LinkArray[X].GetSearched() );
			}
		}

	}

	public void OrderLinkGathering() {
		//System.out.println(System.currentTimeMillis()+": Orders link gathering "+LinkArray+" "+URLS);
		SortURLs_Relation();
		for ( int X = 0; X < LinkArray.length ; X++ ) {
			if ( LinkArray[X] != null ) {
				//System.out.println( "a "+LinkArray[X].GetURL()+" "+LinkArray[X].relationValue+ " "+LinkArray[X].GetSearched() );
				if ( LinkArray[X].GetSearched() == false ) {
					//System.out.println( "b "+LinkArray[X].GetURL()+" "+LinkArray[X].relationValue+ " "+LinkArray[X].GetSearched() );
					//if ( LinkArray[X].relationValue > Class_Controller.InterestBorder ) {
						//System.out.println( "Vil søke etter "+LinkArray[X].GetURL()+" "+LinkArray[X].relationValue+ " "+LinkArray[X].GetSearched() );
						Class_Controller.SearchURL( LinkArray[X] );
						Exit();
						//return;
					//}
				}
				//System.out.println( "Vil IKKE søke etter "+LinkArray[X].GetURL()+" "+LinkArray[X].relationValue+ " "+LinkArray[X].GetSearched() );
			}
		}
		//System.out.println(System.currentTimeMillis()+" Fant ingen flere interessante sider å undersøke" );
		Exit();
	}
	
	private void Exit() {
		if ( this.Class_Controller.ThreadsRunning == 0 ) {
			if ( Class_Controller.ThreadsCompleted >= URLS ) {
				if ( URLS > 3 ) {
					System.out.println( "Shutdown called" );
				
					System.out.println("Ingen flere url'er å sjekke?? "+URLS);
					PrintSortedURLr();
					LinkArray = null;
					System.exit(0);
				}
			}
		}
	}
	
	public void PrintSortedURLr() {
		SortURLsAlpha();
		for ( int X = 0; X < SortedLinkArray.length ; X++ ) {
			if ( SortedLinkArray[X] != null ) {
				System.out.println( SortedLinkArray[X].GetURL()+" "+SortedLinkArray[X].relationValue );
			}
		}
	}
	
	private void SortURLsAlpha() {
		for ( int X = 0; X < LinkArray.length ; X++ ) {
			if ( LinkArray[X] != null ) {
				SortedLinkArray[X] = LinkArray[X];
			}
		}
		for ( int X = 0; X < SortedLinkArray.length ; X++ ) {
			if ( SortedLinkArray[X] != null ) {
				for ( int Y = X; Y < SortedLinkArray.length ; Y++ ) {
					if ( SortedLinkArray[Y] != null ) {
						
						if ( SortedLinkArray[X].GetURL().toString().compareToIgnoreCase( SortedLinkArray[Y].GetURL().toString()) > 0 ) {
							Object_Link A = SortedLinkArray[X];
							Object_Link B = SortedLinkArray[Y];
							SortedLinkArray[X] = B;
							SortedLinkArray[Y] = A;
						}
						
					}
				}
				
			}
		}
	}
}
