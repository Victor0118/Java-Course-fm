import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.Iterator;

public class TechSupport {
	
	Map<String, Vector<String>> Knowledge;
	private Scanner in;
	private String myInput;
	private Vector<String> ListOfResponse;
	private String myKeyWord;
	private String myResponse;
	private Object mySubject;
	private String myLastInput;
	private boolean ByeBye;
	
	public TechSupport() {
		
		Knowledge = new HashMap<String, Vector<String>>();
		myInput = new String();
		ListOfResponse  = new Vector<String>();
		myResponse = new String();
		mySubject = new String();
		in = new Scanner(System.in);
		ByeBye = false;
		
        loadDatabase();
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		System.out.println("Nice to meet you");
		try {
			TechSupport tech = new TechSupport();
	        tech.login();
			
	        while (!tech.quit()) {
				tech.getInput();
				tech.respond();
			}
		} catch (Exception e) {
			System.err.println("Uncaught exception - " + e.getMessage());
	        e.printStackTrace(System.err);
		}
	}
	
	private void loadDatabase() {
	    Scanner scriptfile = null;
		try {
			scriptfile = new Scanner(new File("script.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Vector<String> tempResponses = new Vector<String>();    
	    
	    String str;
	    Vector<String> keyList = new Vector<String>();
	    
	    while(scriptfile.hasNextLine())
	    {
	    	str = scriptfile.nextLine();
	        char Symbol = str.charAt(0);
	        str = str.substring(1);
	        switch(Symbol)
	        {
	            case 'K':
	                keyList.add(str);
	                break;
	            case 'R':
	                tempResponses.add(str);
	                break;
	            case '#':
	            {
	                Iterator<String> iter = keyList.iterator();
	                
	                while (iter.hasNext()) {
	                    Knowledge.put(iter.next(),tempResponses) ;
	                }
	                tempResponses = new Vector<String>();    	        	    
	        	    keyList = new Vector<String>();
	                
	                //放进容器的是引用啊引用啊引用啊不能这么玩
	                //keyList.clear();
	                //tempResponses.clear();
	            }
	                break;
	                default: break;
	        }
	    }
		// TODO Auto-generated method stub
		
	}

	private void respond() {
	    
	    if (myInput.length() == 0 && myLastInput.length() != 0)
	    {
	        run("NULL INPUT**");
	    }
	    else if(myInput.length() == 0 && myLastInput.length() == 0)
	    {
	        run("NULL INPUT REPETITION**");
	    }
	    else if((myLastInput.length() > 0)&&(myInput.length() > 0  && myInput.equals(myLastInput)))
	    {
		    run("REPETITION T**");
	    }
	    else
	    {
	        findMatch();
	    }
	    
	    if(myInput.contains("BYE"))
	    {
	    	ByeBye = true; 
	    }
	    
	    if(ListOfResponse.size() == 0)
	    {
	        run("DON'T UNDERSTAND**");
	    }
	    
	    if(ListOfResponse.size() > 0)
	    {
	        selectResp();
	        if(myResponse.contains("*"))
		    {
		        findSubject();
		        mySubject = transpose(mySubject);
		        myResponse = myResponse.replace("*", " " + mySubject);
		    }
	        if(myResponse.length() > 0) 
	        {
	 	       System.out.println(myResponse);
	 	    }
	    }
		// TODO Auto-generated method stub
	}

	private void findMatch() {
		
	    ListOfResponse = new Vector<String>();
	    String bestKeyWord;
	    Vector<String> ListOfWord = new Vector<String>();
	    
	    if (myInput.contains("**")) {
			myKeyWord = myInput;
		}
	    else {
	 	   String buffer = new String();
	 	    for(int i = 0; i < myInput.length(); ++i) {
	 	        if(myInput.charAt(i)!='?' && myInput.charAt(i)!=' ' && myInput.charAt(i) != '.') {
	 	            buffer += myInput.charAt(i);
	 	        } else if(!buffer.isEmpty()) {
	 	            ListOfWord.add(buffer);
	 	            buffer="";
	 	        }
	 	    }
	 	   if((ListOfWord.isEmpty() && !buffer.isEmpty()) || !buffer.isEmpty()) {
		        ListOfWord.add(buffer);
		    } 
		   bestKeyWord = findBestKey(ListOfWord);
		   myKeyWord = bestKeyWord;
		}   
	   
	    if(Knowledge.containsKey(myKeyWord)){
	    	ListOfResponse = Knowledge.get(myKeyWord);
	    }
		// TODO Auto-generated method stub
	}

	private String findBestKey(Vector<String> listOfWord) {
		 String buffer = new String();
		 int iSize = listOfWord.size();
		    for( int i = 0, j = iSize; i < iSize && j >= 1; ++i, --j )
		    {
		        for( int k = 0; (k + j) <= iSize; ++k )
		        {
		            buffer = getSubphrase(listOfWord, k, k + j);
		            if(Knowledge.containsKey(buffer))
		            {
		            	return buffer;
		            }
		            else
		            {
		            	if (k==0) {
							buffer = "_"+buffer;
						}
		            	if(Knowledge.containsKey(buffer))
		                {
		                	return buffer;
		                }
		            	if (k+j==iSize) {
							buffer = buffer + "_";
						}
		                if(Knowledge.containsKey(buffer))
		                {
		                	return buffer;
		                }
		            }
		        }
		    }
		    return buffer;
		// TODO Auto-generated method stub
	}

	private String getSubphrase(Vector<String> listOfWord, int k, int i) {
		String buffer = new String();
		for (int j = k; j < i; j++) {
			buffer += listOfWord.get(j);
			if ( j!=i-1) {
				buffer += " "; 
			}
		}
		// TODO Auto-generated method stub
		return buffer;
	}

	private void getInput() {	
		System.out.print(">>");
        myLastInput = new String(myInput);
		myInput = in.nextLine();
    	myInput=myInput.toUpperCase(); 	
		// TODO Auto-generated method stub
	}

	private boolean quit() {
		return ByeBye;
		// TODO Auto-generated method stub
	}

	private void login() {
		
		run("SIGNON**");
	    selectResp();
	    if(myResponse.length() > 0) {
	       System.out.println(myResponse);
	    }
	    // TODO Auto-generated method stub
	}

	private String transpose(Object str) {
		
		String[][] transposList = new String[][]{
			    {" MYSELF ", " YOURSELF "},
			    {" DREAMS ", " DREAM "},
			    {" WEREN'T ", " WASN'T "},
			    {" AREN'T ", " AM NOT "},
			    {" I'VE ", " YOU'VE "},
			    {" MINE ", " YOURS "},
			    {" MY ", " YOUR "},
			    {" WERE ", " WAS "},
			    {" MOM ", " MOTHER "},
			    {" I AM ", " YOU ARE "},
			    {" I'M ", " YOU'RE "},
			    {" DAD ", " FATHER "},
			    {" MY ", " YOUR "},
			    {" AM ", " ARE "},
			    {" I'D ", " YOU'D "},
			    {" I ", " YOU "},
			    {" ME ", " YOU "}
			};
		String buffer = " " + str + " ";
	    boolean bTransposed = false;
	    
	    for(int i = 0; i < transposList.length; ++i)
	    {
	        String first = transposList[i][0];
	        String second = transposList[i][1];
	        
	        String temp = new String(buffer);
	        buffer = buffer.replace(first, second);
	        if (temp.equals(buffer)) {
		        bTransposed = true;
			}
	    }
	    
	    if( !bTransposed )
	    {
	        for(int i = 0; i < transposList.length; ++i )
	        {
	        	String first = transposList[i][0];
	 	        String second = transposList[i][1];
		        buffer = buffer.replace(second, first);
	        }
	    }
	    buffer = buffer.trim();
	    return buffer;
		// TODO Auto-generated method stub
		
	}

	private void findSubject() {
		mySubject = ""; 
		if (myKeyWord.charAt(0) == '_') {
			myKeyWord = myKeyWord.substring(1);
		}
		else if (myInput.charAt(myKeyWord.length()-1)== '_') {
			myKeyWord = myKeyWord.substring(0, myKeyWord.length()-1);
		}
	    int pos = myInput.indexOf(myKeyWord);
	    if(pos != -1) 
	    {
	        mySubject = myInput.substring(pos + myKeyWord.length() + 1, myInput.length());
	    }
		// TODO Auto-generated method stub
	}

	private void selectResp() {
		if(ListOfResponse.size() > 0)
	    {
			final int i = (int)(System.currentTimeMillis()%ListOfResponse.size());
	        myResponse = ListOfResponse.get(i);
	    }
		// TODO Auto-generated method stub
	}

	private void run(String str) {
	    
        String tempInput = new String();
        tempInput = myInput;
        myInput = str;
	    findMatch();
	    myInput = tempInput;
		// TODO Auto-generated method stub
		
	}

}
