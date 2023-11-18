//
// import java.io.Serializable;
//
//
// public class CalcExpr implements Serializable {
// int operand1, operand2;
// char operator;
//
//
// public CalcExpr(int operand1, char operator, int operand2) {
// this.operand1 = operand1;
// this.operator = operator;
// this.operand2 = operand2;
// }
//
//
// public int evaluateExpression(CalcExpr expression) {
// int result = 0;
// switch (expression.operator) {
// case '+':
// result = expression.operand1 + expression.operand2;
// break;
// case '-':
// result = expression.operand1 - expression.operand2;
// break;
// case '*':
// result = expression.operand1 * expression.operand2;
// break;
// case '/';
// if (expression.operand2 != 0) {
// result = expression.operand1 / expression.operand2;
// } else {
// // Handle division by zero
// System.out.println("Division by zero error!");
// }
// break;
// default:
// System.out.println("Invalid operator!");
// break;
// }
//
// return result;
// }
// }