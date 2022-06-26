import java.util.Scanner;
import java.util.Stack;

public class ev {
    public static String eval(final String str){
        char[] token = str.toCharArray();   //문자열을 쪼개자
        String[] oper1 = {"+", "-", "*", "/", "%"};   //사칙연산
        String[] oper2 = {"==", "!=", "<", "<=", ">", ">="};  //비교연산
        String[] oper3 = {"!", "&&", "||"};   //논리연산

        Stack<Integer> values = new Stack<Integer>();   //숫자를 넣을 스택
        Stack<Character> operations = new Stack<Character>();   //사칙연산자를 넣을 스택
        Stack<Boolean> result = new Stack<Boolean>();     //논리, 비교연산 결과를 넣을 스택
        Stack<String> bool = new Stack<String>();    //비교연산자, 논리연산자, 논리 결과를 넣을 스택


        //입력한 문자열 길이만큰 반복
        for(int i=0; i < str.length(); i++){
            if(token[i] == ' ') continue;   //빈칸(스페이스바)은 건너뛰자

            //읽은 문자가 숫자인 경우
            if(Character.isDigit(token[i])){
                StringBuffer valueBuffer = new StringBuffer();  //숫자를 담아놓을 버퍼 생성

                //i가 토큰의 길이보다 작고 숫자인 경우
                while(i < token.length && Character.isDigit(token[i]))
                    valueBuffer.append(token[i++]); //다음 토큰의 문자를 숫자버퍼에 저장

                values.push(Integer.parseInt(valueBuffer.toString()));  //숫자버퍼의 문자를 10진수로 변졍하여 숫자 스택에 넣자

                i--;    //숫자가 끝나는 지점(연산자 앞)으로 i설정
            }

            //읽은 문자가 시작하는 괄호인 경우
            else if(token[i] == '(' && !Character.isDigit(token[i]))
                operations.push(token[i]);  //연산자를 넣는 스택에 넣자

            //읽은 문자가 닫는 괄호인 경우
            else if(token[i] == ')' && !Character.isDigit(token[i])){
                while (operations.peek() != '(')    //연산스택의 마지막에 들어간 값이 시작 괄호가 아니면,
                    values.push(applyOp(operations.pop(), values.pop(), values.pop())); // 괄호 안의 내용을 계산하여 결과값을 숫자 스택에 넣는다.
                operations.pop();   //연산자 스택의 마지막 문자'(' 반환
            }

            //토큰의 i번째 문자가 연산자인 경우
            else if(token[i] != '(' && !Character.isDigit(token[i])){
                StringBuffer operBuffer = new StringBuffer();

                while(i < token.length && !Character.isDigit(token[i]))
                   operBuffer.append(token[i++]); //다음 토큰의 문자를 연산버퍼에 저장

                bool.push(String.valueOf(operBuffer));  //연산버퍼를 스트링으로 바꿔 스택에 저장

                i--;
            }
        }

        //연산자 스택이 비어있지 않은 경우(마지막 계산)
        while (!operations.empty() && oper)
            values.push(applyOp(operations.pop(), values.pop(), values.pop())); //값을 계산하여 숫자 스택에 넣는다

        String rtn = String.valueOf(values.pop());

        //계산 결과 반환
        return rtn;
    }


    //우선순위 확인
    public static boolean hasPrecedence(char op1, char op2){
        //두번째로 받은 문자(연산자 스택의 마지막 문자)
        if (op2 == '(' || op2 == ')')
            return false;
        //첫 번째로 받은 문자(토큰의 i번째 문자)
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        return true;
    }

    //사칙연산
    public static int applyOp(char op, int b, int a){
        switch (op){
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) //나누는 수가 0인 경우 예외처리
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case '%':
                return a % b;
        }
        return 0;
    }


    //비교연산
    public static boolean cmpOp(String op, int b, int a){
        switch (op){
            case "==":
                return a == b;
            case "!=":
                return a != b;
            case "<":
                return a < b;
            case "<=":
                return a <= b;
            case ">":
                return a > b;
            case ">=":
                return a >= b;
        }
        return true;
    }

    //논리연산
    public static boolean logicOp(String op, boolean b, boolean a){
        switch (op){
            case "!":
                return !b;
            case "&&":
                return a && b;
            case "||":
                return a || b;
        }
        return true;
    }


    public static void main(String[] args){
        System.out.print("입력 : ");
        Scanner scanner = new Scanner(System.in);

        String str = scanner.next();
        System.out.println("결과 : " + eval(str));
    }
}
