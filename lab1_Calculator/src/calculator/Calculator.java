package calculator;
//杨威 3130103723 计算机科学与技术 
//2015-10-22
import java.util.Scanner;
import java.util.Stack;


public class Calculator {

      private String valid_chars;    
      private String Lbraces; 
      private String Rbraces; 
      private Stack<Character> brace;
      private String Calc, x;
	  private boolean Exceptionflag;
	  private int changeFlag;  
       public Calculator()
       {
    	   changeFlag=0;
    	   Exceptionflag = false;
           valid_chars = "0123456789+-/*%(){}[]";
           Lbraces = "{[("; 
           Rbraces = "}])";
           Calc = " ";
           x = " ";
           brace = new Stack<Character>();
        }

        public int calc(String st)
        {
            st = st.replaceAll("\\s","");
            if (st.equals("")) {
				Exceptionflag = true;
				return 0;
			}
            if(st.charAt(0)== '-')
            {
                st = ("0").concat(st);
            }

            x = st.replaceAll("-","+-");

            char[] carr = x.toCharArray();          
            int len = carr.length, i = 0;
            String symbol = "";

            int indicate = -5;
            while(i < len)
            {
                int m = valid_chars.indexOf(carr[i]);
                int b = Lbraces.indexOf(carr[i]);
                if(m == -1)
                {
                    System.out.println("Error: "+i+"-th element is invalid");
                    Exceptionflag = true;
                	return 0;
                }
                if(b != -1)
                {
                    brace.push(carr[i]);
                }
                i++;
            }
            int left = 0, right=carr.length;

          char tps =' ', right_tps= ' ';
          int match_index = -5;

            String [] opString = {"/", "*", "+","%"};       
             while (brace.empty()==false) {

                   carr = x.toCharArray();
                   left= findLeft(carr);
                   tps = brace.peek();
                   match_index = Lbraces.indexOf(tps);
                    right_tps = Rbraces.charAt(match_index);                     
                    right = x.indexOf(right_tps);                        	
                    Calc = x.substring(left+1,right);

                   for(int j =0; j < 3; j++)
                   {             
                      symbol = opString[j];  
                      indicate = Calc.indexOf(symbol);
                      if (j==0) {
                          int indicate2 = Calc.indexOf("%");
                    	  if (indicate2 != -1 &&(indicate2 < indicate || indicate == -1)) {
							symbol = "%";
							indicate = indicate2;
						}
                      }
                      
                      while(indicate != -1){        
                    	  
                          indicate = calcPart(indicate, symbol, false);   
                          if (changeFlag == 1) symbol = "%";
                          else if(changeFlag == 2) symbol = "/";
                          if (Exceptionflag)   return 0;
                       }  
                      if(Calc == "N")   Calc = " ";

                   }
               x = x.substring(0,left) + Calc + x.substring(right+1);
               carr = x.toCharArray();
               left = findLeft(carr);           
               right_tps = Rbraces.charAt(match_index);
               right = x.indexOf(right_tps);
               brace.pop();
            
             }
            Calc = x;

            for(int j = 0; j< opString.length; j++)
            {
               symbol = opString[j];              
               indicate = Calc.indexOf(symbol);
               
               if (j==0) {
                   //indicate = Calc.indexOf(symbol);
                   int indicate2 = Calc.indexOf("%");
             	  if (indicate2 != -1 &&(indicate2 < indicate || indicate == -1)) {
						symbol = "%";
						indicate = indicate2;
		 			}
               }
               
               while(indicate != -1){
            	   
                    indicate = calcPart(indicate, symbol, true);
                    if (changeFlag == 1) symbol = "%";
                    else if(changeFlag == 2) symbol = "/";
                    if (Exceptionflag) {
						return 0;
					}
                }             
            }

            int calculation = Integer.parseInt(Calc);            
            return calculation;
        }

        public int calcPart(int indicate, String symbol, boolean flag)
        {                   
            changeFlag = 0;
            String s1 = Calc.substring(0,indicate);                    
            String [] items1 = s1.split("\\+|\\*|\\/|\\%");   
            String s2 = Calc.substring(indicate+1);           
            String [] items2 = s2.split("\\+|\\*|\\/|\\%"); 
 
            int intc1 = Integer.valueOf(items1[items1.length-1].trim());
            int intc2 = Integer.valueOf(items2[0].trim());
            String sc1 = Integer.toString(intc1); 
            String sc2 = Integer.toString(intc2);           
            String tempStr = calcNoBrace(intc1, intc2, symbol);
            if (tempStr == " ") {
				Exceptionflag = true;
				return 0;
			}

            if(flag == true){
                x = x.substring(0,indicate- sc1.length()) + tempStr + x.substring(indicate + sc2.length() +1 );               
                Calc = x;                          
              }
            else {
                Calc = Calc.substring(0,indicate- sc1.length()) + tempStr + Calc.substring(indicate + sc2.length() +1 );               
			}
            indicate = Calc.indexOf(symbol);
            if (symbol == "%" || symbol == "/") {
            	int indicate2;
            	if (symbol == "%") {
                    indicate2 = Calc.indexOf("/");
				}else {
                    indicate2 = Calc.indexOf("%");
				}
          	  if (indicate2 != -1 &&(indicate2 < indicate || indicate == -1)) {
						indicate = indicate2;
						if (symbol == "/")  changeFlag = 1;
						if (symbol == "%")  changeFlag = 2;
		 			}
            }             
            return indicate;
        }

        public int findLeft( char[] carr)
        {
           int left = -1;
           for( int i = 0; i< carr.length; i++)
            {            
        	   int b = Lbraces.indexOf(carr[i]);
        	   if(b != -1)  left = i;
            }  
            return left;
        }

        public String calcNoBrace(int calc1, int calc2, String symbol)
        {
               int res = 0;             
                if(symbol == "/")
                {
                     if(calc2 == 0){
                    	 System.out.println("Error: division by 0 condition");
                    	 return " ";
                     }
                     if(calc1 ==0 && calc2 == 0)
                     {
                    	 System.out.println("Error: undefined condition");
                    	 return " ";
                     }  
                    res = calc1/calc2;
                }
                if(symbol == "*")  res = calc1*calc2;   
                if(symbol == "+")  res =  calc1 + calc2;
                if(symbol == "%")  res =  calc1 % calc2;
                String resSt = Integer.toString(res);

                return resSt;
        }

        public void test() {
        	
        	try {
    			@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
                String test;
                System.out.println("Thanks for using the Calculator1.0");
                while (true) {
                	System.out.print(">>");
                    test = sc.nextLine();
                    int anc = calc(test);
                    if (!Exceptionflag) {
                        System.out.println(anc);       
    				}
                    else {
						System.out.println("Please input again");
						Exceptionflag =false;
					}
                }
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("Uncaught exception - " + e.getMessage());
		        e.printStackTrace(System.err);
			}
            
        }

        public  static void main(String [] args)
       {
            Calculator ob = new Calculator();  
            ob.test();
        }

}
